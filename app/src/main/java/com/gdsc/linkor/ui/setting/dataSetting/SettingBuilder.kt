package com.gdsc.linkor.ui.setting.dataSetting

import com.gdsc.linkor.BuildConfig
import com.gdsc.linkor.ui.community.data.CommnunityInterface
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SettingBuilder {
    val gson = GsonBuilder().setLenient().create()

    val retrofitSetting = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val SettingService : SettingInterface by lazy {
        retrofitSetting.create(SettingInterface::class.java)
    }
}