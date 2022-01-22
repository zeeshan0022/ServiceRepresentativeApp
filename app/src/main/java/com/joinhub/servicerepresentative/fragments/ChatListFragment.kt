package com.joinhub.servicerepresentative.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joinhub.servicerepresentative.R
import com.joinhub.servicerepresentative.activities.DashboardActivity


class ChatListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_list, container, false)

    }

    override fun onResume() {
        super.onResume()
        DashboardActivity.textView.text= "Chats"
    }
}