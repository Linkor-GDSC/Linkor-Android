package com.gdsc.linkor.ui.message

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc.linkor.model.MessageResponse
import com.gdsc.linkor.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(private val messageRepository: MessageRepository):ViewModel() {
    private val _messages : MutableState<MessageResponse> = mutableStateOf(MessageResponse(null,"",""))
    val messages: State<MessageResponse> = _messages

    fun getAllMessage(senderEmail:String,receiverEmail:String){
        //retrofit을 이용하여 서버에서 메세지 불러오기
        viewModelScope.launch {
            try {
                _messages.value=messageRepository.getAllMessage(senderEmail=senderEmail, receiverEmail = receiverEmail)!!
            }catch (e:Exception){
                Log.e("MYTAGMessageViewModel","Message api error",e)
            }
        }
    }

    fun postMessage(email: String, content: String, receiverEmail: String) {
        viewModelScope.launch {
            try {
                messageRepository.postMessage(email=email, content=content, receiverEmail = receiverEmail)
                _messages.value=messageRepository.getAllMessage(senderEmail=email, receiverEmail = receiverEmail)!!
                //messageRepository.postMessage(email = "test01@gmail.com", content="I want to learn Korean", receiverEmail = "test02@gmail.com")
                // 성공적으로 요청이 완료된 경우 처리
            } catch (e: Exception) {
                Log.e("MYTAGMessageViewModel","Message api error",e)
            }
        }
    }
}