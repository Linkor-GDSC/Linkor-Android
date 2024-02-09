package com.gdsc.linkor.ui.message

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.gdsc.linkor.model.Message

class MessageViewModel:ViewModel() {
    val messages: MutableState<List<Message>> = mutableStateOf(listOf())
    val inputValue: MutableState<String> = mutableStateOf("")

    fun getMessage(){
        //retrofit을 이용하여 서버에서 메세지 불러오기
    }
    fun sendMessage(inputValue:String){
        //retrofit을 이용하여 서버에 메세지 저장

        //retrofit을 이용하여 서버에서 메세지 불러오기

    }
}