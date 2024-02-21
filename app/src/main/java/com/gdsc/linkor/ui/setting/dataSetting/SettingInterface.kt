package com.gdsc.linkor.ui.setting.dataSetting

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SettingInterface {
    @POST ("user/register")
    fun addUserInfo(
        @Body userInfo: UserInfo
    ): Call<UserInfo>
}


