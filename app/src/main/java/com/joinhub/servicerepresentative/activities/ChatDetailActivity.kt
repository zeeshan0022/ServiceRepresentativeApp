package com.joinhub.servicerepresentative.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.database.*
import com.joinhub.alphavpn.utility.Preference
import com.joinhub.complaintprotaluser.models.Chat
import com.joinhub.complaintprotaluser.models.ChatList
import com.joinhub.khataapp.Utilites.ThemeDataStore.PreferencesKeys.name
import com.joinhub.servicerepresentative.R
import com.joinhub.servicerepresentative.adapter.ChatRecyclerAdapter
import com.joinhub.servicerepresentative.databinding.ActivityChatDetailBinding
import com.joinhub.servicerepresentative.utitlies.Constants

class ChatDetailActivity : AppCompatActivity() {
    private lateinit var key: String
    lateinit var name:String
    lateinit var userName:String
    lateinit var chatroom:String
    private lateinit var mChatRecyclerAdapter: ChatRecyclerAdapter
    private  lateinit var binding : ActivityChatDetailBinding
    private  lateinit var chatList:MutableList<Chat>
    private  lateinit var chatLists: ChatList
    lateinit var preference: Preference
    lateinit var rooms:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatList = mutableListOf()
        chatLists= ChatList()
        initt()
        val bundle=intent.extras
        if(bundle!=null){
            name= bundle.getString("name")!!
            chatroom= bundle.getString("chatroom")!!
            userName= bundle.getString("userName")!!

        }
        preference= Preference(this)
        binding.imageSend.setOnClickListener {
            val chat = Chat(
                preference.getStringpreference("serviceEmail",null)!!,
                userName, preference.getStringpreference("serviceUserName")!!,
                userName,
                binding.edittxtType.text.toString().trim(),
                System.currentTimeMillis()
            )
            sendMessage(chat)
        }
        getMessageFromFirebaseUser(userName,
            preference.getStringpreference("serviceUserName")!!)

    }





    private fun sendMessage(chat: Chat) {
        val room_type_1 = chat.senderUid + "_" + chat.receiverUid
        val room_type_2 = chat.receiverUid + "_" + chat.senderUid

        val databaseReference = FirebaseDatabase.getInstance().reference

        databaseReference.child(Constants.ARG_CHAT_ROOMS).ref.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                when {
                    dataSnapshot.hasChild(room_type_1) -> {

                        databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_1)
                            .child((chat.timestamp).toString()).setValue(chat)
                        rooms= room_type_1
                    }
                    dataSnapshot.hasChild(room_type_2) -> {
                        databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_2)
                            .child((chat.timestamp).toString()).setValue(chat)
                        rooms= room_type_2
                    }
                    else -> {
                        databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_1)
                            .child((chat.timestamp).toString()).setValue(chat)
                        rooms= room_type_1
                        getMessageFromFirebaseUser(chat.senderUid, chat.receiverUid)

                    }
                }
                // send push notification to the receiver
//                sendPushNotificationToReceiver(
//                    chat.sender,
//                    chat.message,
//                    chat.senderUid,
//                    SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN),
//                    receiverFirebaseToken
//                )
                binding.edittxtType.setText("")
                Toast.makeText(this@ChatDetailActivity, "Message sent", Toast.LENGTH_SHORT)
                    .show()
                setChatList(chat)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                /// mOnSendMessageListener.onSendMessageFailure("Unable to send message: " + databaseError.message)
            }
        })
    }

    private fun setChatList(chat: Chat) {
        val ref= FirebaseDatabase.getInstance().reference.child("ChatList")
            .child(preference.getStringpreference("serviceUserName",null)!!)

        ref.orderByChild("userName").equalTo(userName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(snap in snapshot.children){
                        chatLists= snap.getValue(ChatList::class.java)!!
                        key= snap.key!!
                    }


                        ref.child(key).child("lastMessage").setValue(chat.message)
                        ref.child(key).child("timestamp").setValue(chat.timestamp)
                        ref.child(key).child("isRead").setValue(true)
                        ref.child(key).child("senderId").setValue(chat.senderUid)




                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun getMessageFromFirebaseUser(senderUid: String, receiverUid: String) {
        val room_type_1: String = senderUid + "_" + receiverUid
        val room_type_2: String = receiverUid + "_" + senderUid

        val databaseReference = FirebaseDatabase.getInstance().reference

        databaseReference.child(Constants.ARG_CHAT_ROOMS).ref.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                when {
                    dataSnapshot.hasChild(room_type_1) -> {

                        FirebaseDatabase.getInstance()
                            .reference
                            .child(Constants.ARG_CHAT_ROOMS)
                            .child(room_type_1).addChildEventListener(object : ChildEventListener {
                                override fun onChildAdded(
                                    dataSnapshot: DataSnapshot,
                                    s: String?
                                ) {
                                    val chat = dataSnapshot.getValue(Chat::class.java)
                                    updateRec(chat)
                                }

                                override fun onChildChanged(
                                    dataSnapshot: DataSnapshot,
                                    s: String?
                                ) {
                                }

                                override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                                override fun onChildMoved(
                                    dataSnapshot: DataSnapshot,
                                    s: String?
                                ) {
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    //      mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.message)
                                }
                            })
                    }
                    dataSnapshot.hasChild(room_type_2) -> {
                        FirebaseDatabase.getInstance()
                            .reference
                            .child(Constants.ARG_CHAT_ROOMS)
                            .child(room_type_2).addChildEventListener(object : ChildEventListener {
                                override fun onChildAdded(
                                    dataSnapshot: DataSnapshot,
                                    s: String?
                                ) {
                                    val chat = dataSnapshot.getValue(Chat::class.java)
                                    //               mOnGetMessagesListener.onGetMessagesSuccess(chat)
                                    updateRec(chat)
                                }

                                override fun onChildChanged(
                                    dataSnapshot: DataSnapshot,
                                    s: String?
                                ) {
                                }

                                override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                                override fun onChildMoved(
                                    dataSnapshot: DataSnapshot,
                                    s: String?
                                ) {
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    //             mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.message)
                                }
                            })
                    }
                    else -> {

                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //    mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.message)
            }
        })
    }

    private fun updateRec(chat: Chat?) {

        mChatRecyclerAdapter.add(chat)
        binding.chatRec.smoothScrollToPosition(mChatRecyclerAdapter.itemCount - 1)
    }

    private fun initt() {
        mChatRecyclerAdapter = ChatRecyclerAdapter(ArrayList<Chat>(), this)
        binding.chatRec.adapter = mChatRecyclerAdapter

        if(MainActivity.themeBool){
            Constants.darkThemeStyle(this)
        }else{
            Constants.lightThemeStyle(this)
        }
    }

}