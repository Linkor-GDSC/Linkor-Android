package com.gdsc.linkor.ui.community

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gdsc.linkor.setting.QuestionViewModel
import com.gdsc.linkor.ui.community.data.CommnunityBuilder
import com.gdsc.linkor.ui.community.data.PostWriteResponse
import com.gdsc.linkor.ui.community.data.PostWriting
import com.gdsc.linkor.ui.signin.SignInViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val signInViewModel = SignInViewModel()
val questionViewModel = QuestionViewModel()

class CommunityViewmodel(): ViewModel(){
    val Email = signInViewModel.getEmail()

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

    fun sendComment(commentWriting: String){


    }

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
                override fun onFailure(call: Call<PostWriteResponse?>,t: Throwable){
                    Log.e(TAG, "연결 실패")
                    Log.e(TAG, t.toString())
                }
            }

            )
    }

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
                      /*  response.body()?.let{postWriteResponse ->
                            postWriteResponse.data?.let { data ->
                                    val post = Post(
                                        id = data.id ?: 0,  // id에 대한 null 체크 추가
                                        name = data.writer.orEmpty(),
                                        photoUrl = data.writerPhotoUrl,
                                        title = data.title.orEmpty(),
                                        content = data.content.orEmpty(),
                                        time = data.createdAt.orEmpty()
                                    )
                                    posts.add(post)
                            }
                        }*/

                    }
                }
                override fun onFailure(call: Call<PostWriteResponse>, t: Throwable){
                    Log.e("MyTAG", "연결 실패")
                    Log.e("MyTAG", t.toString())
                }

            }

            )

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