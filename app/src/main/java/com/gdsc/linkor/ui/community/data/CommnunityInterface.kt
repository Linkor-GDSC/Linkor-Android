package com.gdsc.linkor.ui.community.data

import com.gdsc.linkor.ui.community.data.comment.GetCommentResponse
import com.gdsc.linkor.ui.community.data.comment.PostCommentResponse
import com.gdsc.linkor.ui.community.data.comment.PostCommentWriting
import com.gdsc.linkor.ui.community.data.post.GetPostId
import com.gdsc.linkor.ui.community.data.post.PostDetailResponse
import com.gdsc.linkor.ui.community.data.post.PostWriteResponse
import com.gdsc.linkor.ui.community.data.post.PostWriting
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommnunityInterface {

    //게시글 작성
    @POST("posts/write")
    fun addPost(
        @Body postData: PostWriting
    ) : Call<PostWriteResponse>

    //게시글 조회
    @GET("posts")
    fun getPostList(): Call<PostWriteResponse>

    //게시글 하나 조회
    /*@GET("posts/{id}")
    fun getPostId(
        @Path("id") id: Int
    ): Call<GetPostId>*/
    @GET("posts/{id}")
    fun getPostById(
        @Path("id") id: Int
    ): Call<PostDetailResponse>

    //한 게시글의 댓글 작성
    @POST("comments/{boardId}")
    fun addComment(
        @Path("boardId") id: Int,
        @Body PostCommentWriting : PostCommentWriting
    ): Call<PostCommentResponse>

    //한 게시글의 전체 댓글 조회
    @GET("comments/{boardId}")
    fun getComment(
        @Path("boardId") id : Int
    ): Call<GetCommentResponse>

}