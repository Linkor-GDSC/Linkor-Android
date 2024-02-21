package com.gdsc.linkor.repository

import android.util.Log
import com.gdsc.linkor.model.TuteeResponse
import com.gdsc.linkor.network.ApiService
import javax.inject.Inject

class MyPageRepository@Inject constructor(private val api: ApiService) {

    suspend fun getTutee(email:String): TuteeResponse? {
        try {
            val response = api.getTutee(email)
            if (response.isSuccessful){
                return response.body()
            }else {
                Log.e("MYTAGRepository", "Unsuccessful response: ${response.code()}")
            }
        } catch (e:Exception){
            Log.e("MYTAGRepository","mypage repository error, detail", e)
        }
        return null

    }
}