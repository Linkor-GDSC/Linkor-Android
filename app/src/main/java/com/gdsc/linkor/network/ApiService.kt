package com.gdsc.linkor.network

import com.gdsc.linkor.model.MessagePostRequest
import com.gdsc.linkor.model.MessagePostResponse
import com.gdsc.linkor.model.MessageResponse
import com.gdsc.linkor.model.TuteeResponse
import com.gdsc.linkor.model.TutorDetailResponse
import com.gdsc.linkor.model.TutorListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface ApiService {

    //마이페이지 튜티 불러오기
    @GET("messages/userlist/{email}")
    suspend fun getTutee(@Path("email") email:String) : Response<TuteeResponse>

    //기존 회원인지 확인
    @GET("user/register/{email}/exists")
    suspend fun checkExistingMember(@Path("email") email:String): Response<Boolean>

    //튜터 전체 조회
    /*@GET("user/filter?")
    suspend fun getAllTutor(): Response<TutorListResponse>*/
    @GET("user/filter")
    suspend fun getAllTutor(
        @Query("gender") gender:String? = null,
        @Query("locationsido") locationSido:String? = null,
        @Query("locationgu") locationGu:String? = null,
        @Query("tutoringmethod") tutoringMethod:String? = null,
        @Query("times") time1:String?=null,
        @Query("times") time2:String?=null,
        @Query("times") time3:String?=null,
        @Query("times") time4:String?=null,
        @Query("times") time5:String?=null,
        @Query("times") time6:String?=null,
        @Query("times") time7:String?=null,

    ): Response<TutorListResponse>

    //튜터 정보 조회
    @GET("user/tutors/{email}")
    suspend fun getTutorDetail(@Path("email") email:String):Response<TutorDetailResponse>

    //메세지 조회
    @GET("messages/{senderEmail}/{receiverEmail}")
    suspend fun getAllMessage(@Path("senderEmail") senderEmail:String, @Path("receiverEmail") receiverEmail:String):Response<MessageResponse>
    //메세지 보내기
    @POST("messages/{email}")
    suspend fun postMessage(
        @Path("email") email:String,
        @Body messagePostRequest: MessagePostRequest
    ): Response<MessagePostResponse>
}
