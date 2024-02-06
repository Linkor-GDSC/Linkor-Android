package com.gdsc.linkor.ui.setting


import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//retrofit builer 역할

object RetrofitClient {

    var gson = GsonBuilder().setLenient().create()

    val retrofit = Retrofit.Builder()
        .baseUrl(Constantes.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val WebService: WebService by lazy { retrofit.create(WebService::class.java) }

}