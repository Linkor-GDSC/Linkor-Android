package com.gdsc.linkor.repository

import android.util.Log
import com.gdsc.linkor.model.MessagePostRequest
import com.gdsc.linkor.model.MessageResponse
import com.gdsc.linkor.network.ApiService
import javax.inject.Inject

class MessageRepository@Inject constructor(private val api: ApiService) {
    suspend fun getAllMessage(senderEmail:String,receiverEmail:String): MessageResponse? {
        try{
            val response = api.getAllMessage(senderEmail=senderEmail,receiverEmail=receiverEmail)
            if (response.isSuccessful){
                return response.body()
            }else{
                Log.e("MYTAGMessageRepository", "Unsuccessful response: ${response.code()}")
            }
        }catch (e:Exception){
            Log.e("MYTAGMessageRepository", "message repository error",e)
        }
        return null
    }

    suspend fun postMessage(email: String,receiverEmail: String, content:String){
        try{
            val messagePostRequest = MessagePostRequest(content=content, receiverEmail = receiverEmail)
            val response = api.postMessage(email=email, messagePostRequest = messagePostRequest)
            if (response.isSuccessful){
                Log.d("mytag","메세지 보내기 성공: ${response.body()}")

            }else{
                Log.e("MYTAGMessageRepository", "Unsuccessful response: ${response.code()}")
            }
        }catch (e:Exception){
            Log.e("MYTAGMessageRepository", "message repository post error",e)
        }
    }
}