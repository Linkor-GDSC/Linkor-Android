package com.gdsc.linkor.ui.setting

import com.google.firebase.auth.UserInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

//인터페이스 역할

interface WebService {

    @POST("users/register")
     fun addUserRegister(
        @Body Role: Role
    ) : Call<Any>
}