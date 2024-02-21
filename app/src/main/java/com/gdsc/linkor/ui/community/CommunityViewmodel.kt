package com.gdsc.linkor.ui.community

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gdsc.linkor.setting.QuestionViewModel
import com.gdsc.linkor.ui.community.data.CommnunityBuilder
import com.gdsc.linkor.ui.community.data.comment.GetCommentResponse
import com.gdsc.linkor.ui.community.data.post.GetPostId
import com.gdsc.linkor.ui.community.data.comment.PostCommentResponse
import com.gdsc.linkor.ui.community.data.comment.PostCommentWriting
import com.gdsc.linkor.ui.community.data.post.PostWriteResponse
import com.gdsc.linkor.ui.community.data.post.PostWriting
import com.gdsc.linkor.ui.signin.SignInViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//val signInViewModel = SignInViewModel()
val questionViewModel = QuestionViewModel()

class CommunityViewmodel(): ViewModel(){
    //val Email = signInViewModel.getEmail()

    //writing
    var  titleWriting by mutableStateOf("")

    var ContentWriting by mutableStateOf("")

    //comment
    var commentWriting by mutableStateOf("")

    var _posts = mutableStateOf(PostWriteResponse(data=emptyList(),message="",success=""))
     val posts:State<PostWriteResponse> = _posts
    init {
        getPosts2()
    }

   var _postId = mutableStateOf(GetPostId(data = emptyList(), message = "", success = ""))
    val postId:State<GetPostId> = _postId
    init {
        getPostSet(1)
    }


// comment 전송
    fun sendComment(id: Int, commentWriting: String){
        var data = PostCommentWriting(commentWriting,"jiwons@gmail.com" )
        CommnunityBuilder.communityService.addComment(id,data)
            .enqueue(object : Callback<PostCommentResponse>{
                override fun onResponse(call: Call<PostCommentResponse>, response: Response<PostCommentResponse>){
                    if(response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                        return
                    }else{
                        Log.e(TAG, response.toString())
                    }
                }
                override fun onFailure(call: Call<PostCommentResponse?>, t: Throwable){
                    Log.e(TAG, "연결 실패")
                    Log.e(TAG, t.toString())
                }

            })
    }

 //댓글 조회
/*
    fun getComment(id: Int){
        CommnunityBuilder.communityService.getComment(id)
            .enqueue(object: Callback<GetCommentResponse>{
                override fun onResponse(call: Call<GetCommentResponse>, response: Response<GetCommentResponse>){
                    if
                }
                override fun onFailure(call: Call<PostWriteResponse?>, t: Throwable){
                    Log.e(TAG, "연결 실패")
                    Log.e(TAG, t.toString())
                }
            })
    }
*/

//게시글 올리기
    fun sendPost(titleWriting: String, ContentWriting: String) {
        //writer -> Email 로 바꾸기
        val data = PostWriting(titleWriting, ContentWriting,  "jiwons@gmail.com")
        CommnunityBuilder.communityService.addPost(data)
            .enqueue(object : Callback<PostWriteResponse> {
                override fun onResponse(call: Call<PostWriteResponse?>, response: Response<PostWriteResponse?>){
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                        return
                    }else{
                        Log.e(TAG, "포스트 게시글 성공")
                    }
                }
                override fun onFailure(call: Call<PostWriteResponse?>, t: Throwable){
                    Log.e(TAG, "연결 실패")
                    Log.e(TAG, t.toString())
                }
            }

            )
    }


// 게시글 가져오기
    fun getPosts2() {
     //   val posts =  mutableListOf<Post>()

        CommnunityBuilder.communityService.getPostList()
            .enqueue(object: Callback<PostWriteResponse>{
                override fun onResponse(call: Call<PostWriteResponse>, response: Response<PostWriteResponse>){
                    if (response.isSuccessful.not()){
                        Log.e("MyTAG", response.toString())
                        return
                    }else{
                        _posts.value = response.body()!!
                        Log.d("MyTAG"," $posts.value")
                    }
                }
                override fun onFailure(call: Call<PostWriteResponse>, t: Throwable){
                    Log.e("MyTAG", "연결 실패")
                    Log.e("MyTAG", t.toString())
                }

            }

            )

    }

    //하나의 게시글 정보 가져오기
    fun getPostSet(id: Int){
        CommnunityBuilder.communityService.getPostId(id)
            .enqueue(object : Callback<GetPostId>{
                override fun onResponse(call: Call<GetPostId>, response: Response<GetPostId>){
                    if(response.isSuccessful.not()){
                        Log.e("MyTAG", response.toString())
                        return
                    }else{
                        _postId.value = response.body()!!
                        Log.d("MyTAG"," $postId.value")
                    }
                }
                override fun onFailure(call: Call<GetPostId>, t: Throwable){
                    Log.e("MyTAG", "연결 실패")
                    Log.e("MyTAG", t.toString())
                }
            })
    }



}



class CommunityViewModelFactory(
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommunityViewmodel::class.java)) {
            return CommunityViewmodel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}