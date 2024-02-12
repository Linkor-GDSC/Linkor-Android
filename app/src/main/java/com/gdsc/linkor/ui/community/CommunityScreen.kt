package com.gdsc.linkor.ui.community

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gdsc.linkor.R
import com.gdsc.linkor.navigation.LinkorBottomNavigation

@Composable
fun CommunityScreen(navController: NavController) {
    val posts = remember { getPosts() }

    Scaffold(
        bottomBar = { LinkorBottomNavigation(navController = navController) }
    ) {
        Surface(Modifier.padding(it)) {
            Surface(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                color = Color.Transparent
            ) {


                LazyColumn {
                    items(posts) { post ->
                        PostItem(post){
                            try {
                                navController.navigate(
                                    "comment/${post.toRouteString()}"
                                )
                            }catch (e:Exception){
                                Log.e("MYTAG","navigation 오류",e)
                            }
                        }
                    }
                }
                //추가 버튼
                IconButton(
                    modifier = Modifier
                        .size(60.dp)
                        .offset(300.dp, 600.dp),
                    onClick = {  /*클릭 후 액션 */ }
                ){
                    Image(painter = painterResource(id = R.drawable.add_box),
                        contentDescription =null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    )
                }
            }
        }
    }

}

//아이템 모양
@Composable
fun PostItem(post: Post, onClick: (Post)->Unit) {
    // 게시글 내용을 표시
    Surface(
        onClick = { onClick(post) },
        modifier = Modifier
            .padding(vertical = 10.dp)
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
                    model = post.photoUrl,
                    " Profile Image",
                    modifier = Modifier
                        .size(35.dp, 35.dp)
                        .clip(RoundedCornerShape(50))
                )
                Spacer(modifier = Modifier.width(10.dp))

                Text(text = post.name,
                    modifier = Modifier.align(Alignment.CenterVertically) // 수정된 부분

                    )
            }

            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            )
            {
                //제목
                Text(text=post.title,
                    style= MaterialTheme.typography.titleMedium)
                //내용
                Text(text=post.content,
                    style=MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis)

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
