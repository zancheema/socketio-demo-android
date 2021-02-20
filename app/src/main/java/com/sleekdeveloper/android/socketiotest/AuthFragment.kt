package com.sleekdeveloper.android.socketiotest

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : Fragment(R.layout.fragment_auth) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enterChatButton.setOnClickListener {
            enterChat()
        }
    }

    private fun enterChat() {
        val bundle = bundleOf(
            "userName" to editNickName.text.toString(),
            "chatRoom" to editChatRoom.text.toString()
        )
        findNavController().navigate(R.id.action_authFragment_to_chatFragment, bundle)
    }
}