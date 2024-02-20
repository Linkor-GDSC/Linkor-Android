package com.gdsc.linkor.ui.community.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CommnunityInterface {
    @POST("posts/write")
    fun addPost(
        @Body postData: PostWriting
    ) : Call<PostWriteResponse>

    @GET("posts")
    fun getPostList(): Call<PostWriteResponse>
}