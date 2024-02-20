package com.gdsc.linkor.ui.community.data

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object CommnunityBuilder {
    val gson = GsonBuilder().setLenient().create()

    val retrofit = Retrofit.Builder()
        .baseUrl("***REMOVED***")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val communityService : CommnunityInterface by lazy {
        retrofit.create(CommnunityInterface::class.java)
    }
}