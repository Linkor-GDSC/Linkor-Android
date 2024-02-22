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
import com.gdsc.linkor.ui.community.data.comment.AllComment
import com.gdsc.linkor.ui.community.data.comment.GetCommentResponse
import com.gdsc.linkor.ui.community.data.post.GetPostId
import com.gdsc.linkor.ui.community.data.comment.PostCommentResponse
import com.gdsc.linkor.ui.community.data.comment.PostCommentWriting
import com.gdsc.linkor.ui.community.data.post.Post2
import com.gdsc.linkor.ui.community.data.post.PostDetail
import com.gdsc.linkor.ui.community.data.post.PostDetailResponse
import com.gdsc.linkor.ui.community.data.post.PostWriteResponse
import com.gdsc.linkor.ui.community.data.post.PostWriting
import com.gdsc.linkor.ui.signin.SignInViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val signInViewModel = SignInViewModel()
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

    val postList=MutableStateFlow<List<Post2>>(emptyList())

    val postDetail = MutableStateFlow<PostDetail>(PostDetail())

    val commentList = MutableStateFlow<List<AllComment>>(emptyList())

    val isCommentLoading = MutableStateFlow(true)
    init {
        //getPosts2()
    }

   /*var _postId = mutableStateOf(GetPostId(data = emptyList(), message = "", success = ""))
    val postId:State<GetPostId> = _postId*/
    /*init {
        getPostSet(1)
    }
*/

// comment 전송
    fun sendComment(id: Int, commentWriting: String){
        val writer = signInViewModel.getEmail()
        var data = writer?.let { PostCommentWriting(commentWriting, it) }
    if (data != null) {
        CommnunityBuilder.communityService.addComment(id,data)
            .enqueue(object : Callback<PostCommentResponse>{
                override fun onResponse(call: Call<PostCommentResponse>, response: Response<PostCommentResponse>){
                    if(response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                        return
                    }else{
                        Log.e(TAG, response.toString())
                        getComment(id=id)
                    }
                }
                override fun onFailure(call: Call<PostCommentResponse?>, t: Throwable){
                    Log.e(TAG, "연결 실패")
                    Log.e(TAG, t.toString())
                }

            })
    }
    }

 //댓글 조회

    fun getComment(id: Int){
        CommnunityBuilder.communityService.getComment(id)
            .enqueue(object: Callback<GetCommentResponse>{
                override fun onResponse(call: Call<GetCommentResponse>, response: Response<GetCommentResponse>){
                    if(response.isSuccessful.not()){
                        Log.e("mytag 댓글조회", response.toString())
                        return
                    }else{
                        Log.d("mytag 댓글조회", "성공: ${response.body().toString()}")
                        commentList.value= response.body()?.data!!
                    }
                }
                override fun onFailure(call: Call<GetCommentResponse>, t: Throwable){
                    Log.e("mytag 댓글조회", "연결 실패")
                    Log.e("mytag 댓글조회", t.toString())
                }
            })
    }


//게시글 올리기
    fun sendPost(titleWriting: String, ContentWriting: String) {
        //writer -> Email 로 바꾸기
        val writer = signInViewModel.getEmail()
        val data = PostWriting(titleWriting, ContentWriting,  writer=writer)
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
fun getPosts3() {
    //   val posts =  mutableListOf<Post>()

    CommnunityBuilder.communityService.getPostList()
        .enqueue(object: Callback<PostWriteResponse>{
            override fun onResponse(call: Call<PostWriteResponse>, response: Response<PostWriteResponse>){
                if (response.isSuccessful.not()){
                    Log.e("MyTAG", response.toString())
                    return
                }else{
                    //_posts.value = response.body()!!.data
                    postList.value= response.body()?.data!!
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


/*fun getPosts2() {
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

}*/

    /*fun getPosts2() {
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

    }*/

    //하나의 게시글 정보 가져오기
    suspend fun getPostSet(id: Int) {
        isCommentLoading.value = true
        try {
            val response = withContext(Dispatchers.IO) {
                CommnunityBuilder.communityService.getPostById(id).execute()
            }

            if (response.isSuccessful) {
                Log.d("mytag 특정 게시글","${response.body()}")
                response.body()?.data?.let {
                    postDetail.value = it
                    Log.d("MyTAG 특정 게시글"," $postDetail")
                } ?: Log.e("MyTAG", "postDetail is null")
            } else {
                Log.e("MyTAG 특정 게시글", response.toString())
            }
        } catch (e: Exception) {
            Log.e("MyTAG 특정 게시글", "Error in getPostSet", e)
        } finally {
            isCommentLoading.value = false
        }
    }
    /*fun getPostSet(id: Int){
        CommnunityBuilder.communityService.getPostById(id)
            .enqueue(object : Callback<PostDetailResponse>{
                override fun onResponse(call: Call<PostDetailResponse>, response: Response<PostDetailResponse>){
                    if(response.isSuccessful.not()){
                        Log.e("MyTAG 특정 게시글", response.toString())
                        return
                    }else{
                        //_postId.value = response.body()!!
                        postDetail.value= response.body()?.postDetail!!
                        Log.d("MyTAG 특정 게시글"," $postDetail")
                    }
                }
                override fun onFailure(call: Call<PostDetailResponse>, t: Throwable){
                    Log.e("MyTAG", "연결 실패")
                    Log.e("MyTAG", t.toString())
                }
            })
    }*/

    /*fun getPostSet(id: Int){
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
    }*/



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