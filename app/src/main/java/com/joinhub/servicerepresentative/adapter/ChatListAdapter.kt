package com.joinhub.servicerepresentative.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joinhub.alphavpn.utility.Preference
import com.joinhub.complaintprotaluser.models.ChatList
import com.joinhub.servicerepresentative.activities.ChatDetailActivity
import com.joinhub.servicerepresentative.databinding.ChatRoomListBinding
import com.joinhub.servicerepresentative.utitlies.Constants

class ChatListAdapter(val activity:Activity, val list:MutableList<ChatList>):RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {
    class ViewHolder(val binding:ChatRoomListBinding):RecyclerView.ViewHolder(binding.root)
    var preference=Preference(activity)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return  ViewHolder(ChatRoomListBinding.inflate(LayoutInflater.from(activity),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding){
            with(list[position])
            {
                txtName.text= name
                if(senderId==preference.getStringpreference("serviceUserName",null)){
                    txtchat.text= "You:  $lastMessage"
                }else{
                    txtchat.text= lastMessage
                }
                txtDate.text= Constants.getDate(timestamp)

                btnChat.setOnClickListener {
                    val intent=Intent(activity,ChatDetailActivity::class.java)
                    intent.putExtra("userName", userName)
                    intent.putExtra("chatroom",chatRoomID)
                    intent.putExtra("name", name)

                    activity.startActivity(intent)

                }
            }

        }
    }

    override fun getItemCount(): Int {
      return list.size
    }

}