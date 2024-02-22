package com.gdsc.linkor.ui.community.comment

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gdsc.linkor.R
import com.gdsc.linkor.ui.community.CommunityViewmodel
import com.gdsc.linkor.ui.community.data.comment.AllComment
import com.gdsc.linkor.ui.community.data.post.Post2
import com.gdsc.linkor.ui.community.data.post.PostDetail
import com.gdsc.linkor.util.DateConverter

@Composable
fun commentScreen(navController: NavController,
                  //post: Post2,
                  viewmodel: CommunityViewmodel,
                  id: Int
){
    LaunchedEffect(Unit) {
        try {
            viewmodel.getPostSet(id)
        } catch (e: Exception) {
            Log.e("mytag 댓글창","에러",e)
        }
    }
    viewmodel.getComment(id)
    val commentList by viewmodel.commentList.collectAsState()

    /*try{
        viewmodel.getPostSet(id=id)

    }catch (e:Exception){
        Log.e("mytag 댓글창","에러",e)
    }*/

    val post by viewmodel.postDetail.collectAsState()
    //val post = PostDetail(content = "$id",createdAt="2024-02-18T20:19:48.0104",id=1,title="title", writer = "writier",
        //writerPhotoUrl="https://lh3.googleusercontent.com/a/ACg8ocIEuFKe7nru_wkMIgvUTufE09Z5w6PKHfu8VjOTBWMutbro6XPdmQQAiG4qe_7U0ceWrEuOdWSxe9H6OSupUnWVtME=s576-c")

    val dateConverter = DateConverter()
    //Log.d("MyTAGUI", "$post")
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFFFFF)
        ) {
        Column(modifier=Modifier.fillMaxSize()) {
            //뒤로 가기 버튼
            IconButton(onClick = { navController.navigateUp() }) {
                Image(painter = painterResource(id = R.drawable.ic_arrow_left), contentDescription = "arrow left",
                    modifier = Modifier
                        .padding(8.dp),
                    alignment = Alignment.TopStart)
            }

            //질문글
            Surface(
                modifier = Modifier
                    .heightIn(max = 630.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .weight(1f),
                color= Color(0xFFFFFFFF),
                //border = BorderStroke(width = 1.dp, color = Color(0xFFE0E0E0))
            ) {
                Column(
                    modifier = Modifier.padding(/*horizontal = 14.dp,*/ vertical = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp),

                    ) {
                    Row(
                        modifier=Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                    ) {
                        //프로필
                        AsyncImage(
                            model = post.writerPhotoUrl,
                            " Profile Image",
                            modifier = Modifier
                                .size(40.dp, 40.dp)
                                .clip(RoundedCornerShape(50))
                                .align(Alignment.CenterVertically)

                        )
                        Spacer(modifier = Modifier.width(10.dp))

                        Column(
                            modifier = Modifier
                        ) {
                            //이름
                            post.writer?.let {
                                Text(text = it,
                                )
                            }

                            Spacer(modifier = Modifier.height(3.dp))

                            //작성 시간
                            Row(
                                // modifier =Modifier.align(Alignment.CenterHorizontally )
                            ) {
                                Image(painter = painterResource(id = R.drawable.bx_bx_time),
                                    contentDescription = "time",

                                    )
                                Spacer(modifier = Modifier.width(3.dp))

                                //post.createdAt?.let { Text(text = it, fontSize = 11.sp, color= Color.DarkGray) }
                                post.createdAt?.let {
                                    dateConverter.convertDateToString(it)
                                        ?.let { Text(text = it, fontSize = 11.sp, color= Color.DarkGray) }
                                }
                            }

                        }


                    }

                    Column(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                    )
                    {
                        //제목
                        post.title?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        //내용
                        post.content?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }

                    }

                    //댓글 시작
                    //val comments = remember { getComment() }
                    Column(
                        modifier = Modifier
                        // .padding(16.dp)
                    ) {
                        commentList.forEach { comment ->
                            Postcomment(comment,viewmodel)
                        }
                    }


                }

            }


            // 댓글 작성
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color(0xFFFDFDFD)
            ) {
                CommentInput(post, viewmodel)
            }

        }

    }



}

@Composable
fun Postcomment(comment: AllComment, viewmodel: CommunityViewmodel){
// 게시글 내용을 표시
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth(),
        color= Color(0xFFFFFFFF),
        border = BorderStroke(width = 1.dp, color = Color(0xFFE0E0E0))
    ){
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),

            ){
            Row(
                modifier=Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                //프로필
                AsyncImage(
                    model = comment.writerPhotoUrl,
                    " Profile Image",
                    modifier = Modifier
                        .size(35.dp, 35.dp)
                        .clip(RoundedCornerShape(50))
                )
                Spacer(modifier = Modifier.width(10.dp))

                //이름

                Text(text = comment.writer,
                    modifier = Modifier.align(Alignment.CenterVertically) // 수정된 부분

                )
                Spacer(modifier = Modifier.width(5.dp))

                //점 - 시간 이을 점

                Image(
                    painter = painterResource(id = R.drawable.ellipse_1234),
                    contentDescription = "dot",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(5.dp))


            }

            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            )
            {
                //내용
                Text(text=comment.content,
                    style=MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis)

            }
        }
    }
}

@Composable
fun CommentInput(post : PostDetail, viewmodel: CommunityViewmodel) {

    fun sendComment() {
        post.id?.let { viewmodel.sendComment(it,viewmodel.commentWriting) }
        viewmodel.commentWriting = ""
    }

    val localFocusManager = LocalFocusManager.current

    Surface(
        color = Color.Transparent,
        border = BorderStroke(
            width = 1.dp,
            color = Color.Transparent
        ),

        //박스 크기 조절
        modifier = Modifier
            .fillMaxWidth()
            .size(100.dp)
            .padding(13.dp)
    ) {
        Surface(
            color = Color.Transparent,
            border = BorderStroke(
                width = 1.dp,
                color = Color.LightGray
            ),
            shape =  RoundedCornerShape(10.dp) ,
            modifier = Modifier
               // .clip(RoundedCornerShape(10.dp))
        ) {

            BasicTextField(
                value = viewmodel.commentWriting,
                onValueChange = { viewmodel.commentWriting = it },
                enabled = true,
                textStyle = TextStyle(fontSize = 18.sp),

                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions{
                    localFocusManager.clearFocus()
                },
                decorationBox = {
                    innerTextField ->
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(size = 16.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(modifier = Modifier
                            .weight(2f)
                        ){
                            innerTextField()
                        }


                        IconButton(onClick = {  sendComment()  },
                            ) {
                            Icon(painter = painterResource(id = R.drawable.ic_send),
                                contentDescription = "send button",
                                tint = colorResource(id = R.color.primary)
                            )
                        }
                    }
                }
            )
        }

        /*
        BasicTextField(
            value = viewmodel.commentWriting,
            onValueChange ={ viewmodel.commentWriting = it},
            enabled = true,
            textStyle = TextStyle(fontSize = 18.sp),
            colors = OutlinedTextFieldDefaults.colors( //border 색, label 색 변경
                focusedBorderColor = Color(android.graphics.Color.parseColor("#4C6ED7")), // 포커스를 얻었을 때 outline 색상 지정
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(android.graphics.Color.parseColor("#4C6ED7")),
                unfocusedLabelColor = Color.LightGray
            ),
         //   label = { Text(text = "write your comment", textAlign = TextAlign.Center)},

            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions{
                localFocusManager.clearFocus()
            },

            //내부 변경
            modifier = Modifier
                .background(Color.Transparent),

            ){

        }*/
    }
}



/*

@Preview(showBackground = true)
@Composable
fun commentPreview() {


    comment(

    )
}*/
