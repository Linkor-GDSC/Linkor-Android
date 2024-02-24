package com.gdsc.linkor.ui.community

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gdsc.linkor.R
import com.gdsc.linkor.navigation.LinkorBottomNavigation
import com.gdsc.linkor.ui.community.data.post.Post2
import com.gdsc.linkor.util.DateConverter

@Composable
fun CommunityScreen(
    navController: NavController,
    viewModel: CommunityViewmodel = viewModel( factory = CommunityViewModelFactory())
) {
    //val posts = remember { viewModel.getPosts2() }
   // val posts = viewModel.getPosts2()
    //val posts by remember{viewModel.posts }
    //val posts by viewModel.posts
    viewModel.getPosts3()
    val posts by viewModel.postList.collectAsState()

    Scaffold(
        bottomBar = { LinkorBottomNavigation(navController = navController) },
        containerColor = Color.White,
        floatingActionButton = { // 여기에 floating action button 정의
            IconButton(
                onClick = { navController.navigate("writingScreenRoute") },
                modifier = Modifier.size(60.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.add_box),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Surface(color = Color.White,modifier = Modifier.padding(it)) {
            Surface(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                color = Color.White
            ) {
                LazyColumn {
                    Log.d("MyTAGUI", "$posts")
                    /*items(posts.data.orEmpty()) { post ->*/
                    items(posts) { post ->
                        PostItem(post,viewModel){
                            try {
                                /*navController.navigate(
                                    "comment/${post.toRouteString()}"
                                )*/
                                navController.navigate(
                                    "comment/${post.id}"
                                )
                                Log.d("MYTAG 커뮤니티 게시글","게시글: $post")

                            }catch (e:Exception){
                                Log.e("MYTAG 커뮤니티 네비","navigation 오류",e)
                            }
                        }
                    }
                }

            }
        }
        //추가 버튼
        /*IconButton(
            modifier = Modifier
                .size(60.dp)
                .offset(300.dp, 600.dp),
            onClick = { navController.navigate("writingScreenRoute") },

            ){
            Image(painter = painterResource(id = R.drawable.add_box),
                contentDescription =null,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            )
        }*/
    }

}

//아이템 모양
@Composable
fun PostItem(post: Post2, viewModel: CommunityViewmodel, onClick: (Post2)->Unit) {
    // 게시글 내용을 표시
    val dateConverter = DateConverter()
    Surface(
        onClick = { onClick(post) },
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        color = Color.Transparent,
        shape =  RoundedCornerShape(10.dp) ,
        border = BorderStroke(width = 1.dp, color = Color(0xFFE0E0E0))
    ){
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),

            ){
            Row(
                modifier=Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
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
                        modifier =Modifier.align(Alignment.CenterHorizontally )
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

            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            )
            {
                //제목
                post.title?.let {
                    Text(text= it,
                        style= MaterialTheme.typography.titleMedium)
                }
                //내용
                post.content?.let {
                    Text(text= it,
                        style=MaterialTheme.typography.bodySmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis)
                }

            }
        }
    }

}




/*
@Preview(showBackground = true)
@Composable
fun communityPreview() {


    communityScreen(

    )
}*/
