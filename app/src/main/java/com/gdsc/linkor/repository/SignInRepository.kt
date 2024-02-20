package com.gdsc.linkor.repository

import android.util.Log
import com.gdsc.linkor.network.ApiService
import javax.inject.Inject

class SignInRepository@Inject constructor(private val api: ApiService) {
    suspend fun checkExistingMember(email:String):Boolean?{
        try {
            val response = api.checkExistingMember(email=email)
            if (response.isSuccessful){
                //Log.d("mytag signin repository","${response.body()}")
                return response.body()
            }else {
                Log.e("MYTAGRepository", "Unsuccessful response: ${response.code()}")
            }
        } catch (e:Exception){
            Log.e("MYTAGRepository","tutor repository error, detail", e)
        }
        return null

    }
}