package com.joinhub.servicerepresentative.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.joinhub.alphavpn.utility.Preference
import com.joinhub.complaintprotaluser.models.ChatList
import com.joinhub.servicerepresentative.activities.DashboardActivity
import com.joinhub.servicerepresentative.adapter.ChatListAdapter
import com.joinhub.servicerepresentative.databinding.FragmentChatListBinding


class ChatListFragment : Fragment() {
    lateinit var  binding:FragmentChatListBinding
    lateinit var chatList:MutableList<ChatList>
    lateinit var preference: Preference
    lateinit var list:MutableList<ChatList>
    lateinit var adapter:ChatListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentChatListBinding.inflate(layoutInflater, container, false)
        chatList= mutableListOf()
        preference= Preference(requireContext())
        list= mutableListOf()
        getChatList()
        binding.searchUser.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
            if(s!!.isBlank()){
                setUpRec(chatList)
            }else{
                filterData(s.toString())
            }
            }

        })
        return binding.root

    }
    private fun filterData(s: String) {
        list.clear()
        for (model in chatList){
            if(model.name.equals(s,true)){
                list.add(model)
            }
        }
        if(list.isEmpty()){
            binding.chatRec.visibility= View.GONE

        }else{
            binding.chatRec.visibility= View.VISIBLE
            setUpRec(list)
        }
    }
    private fun getChatList() {
        binding.progressBar.visibility=View.VISIBLE
        val ref= FirebaseDatabase.getInstance().reference.child("ChatList").child(preference.getStringpreference("serviceUserName",null)!!)
        ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
             for(snap in snapshot.children){
                 val mode= snap.getValue(ChatList::class.java)
                 chatList.add(mode!!)
             }
                if(chatList.isNotEmpty()){
                    setUpRec(chatList)
                }else{
                    binding.progressBar.visibility=View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressBar.visibility=View.GONE
                Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()

            }
        })
    }

    private fun setUpRec(chatList: MutableList<ChatList>) {
        adapter= ChatListAdapter(requireActivity(), chatList )
        binding.chatRec.layoutManager= LinearLayoutManager(requireContext(),RecyclerView.VERTICAL, false)
        binding.chatRec.adapter= adapter
        binding.progressBar.visibility=View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        DashboardActivity.textView.text= "Chats"
    }
}