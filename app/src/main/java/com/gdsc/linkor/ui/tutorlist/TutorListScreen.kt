package com.gdsc.linkor.ui.tutorlist

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.gdsc.linkor.R
import com.gdsc.linkor.model.Tutor
import com.gdsc.linkor.navigation.LinkorBottomNavigation
import com.gdsc.linkor.navigation.Route

@Composable
fun TutorListScreen(navController: NavController,viewModel: TutorListViewModel = hiltViewModel()) {
    /*val tutors= listOf(
        Tutor("https://lh3.googleusercontent.com/a/ACg8ocLvVDbkmsKPGOwT0iOIMf2vuk_9CeWhI_SqyZObY73bVgk=s96-c","Nunsong Kim","Woman","Seoul","Yong San","MON, TUE","FTF","Hello nice to meet you. I'll teach you how to pronounce Korean by listening k-pop"),
        Tutor("https://lh3.googleusercontent.com/a/ACg8ocLvVDbkmsKPGOwT0iOIMf2vuk_9CeWhI_SqyZObY73bVgk=s96-c","Sookmyung Lee","Woman","Seoul","Yong San","MON, TUE","NFTF","Hello nice to meet you."),
        Tutor("https://lh3.googleusercontent.com/a/ACg8ocLvVDbkmsKPGOwT0iOIMf2vuk_9CeWhI_SqyZObY73bVgk=s96-c","Nunsong Kim","Woman","Seoul","Yong San","MON, TUE","FTF","Hello nice to meet you."),
        Tutor("https://lh3.googleusercontent.com/a/ACg8ocLvVDbkmsKPGOwT0iOIMf2vuk_9CeWhI_SqyZObY73bVgk=s96-c","Sookmyung Lee","Woman","Seoul","Yong San","MON, TUE","NFTF","Hello nice to meet you."),
        Tutor("https://lh3.googleusercontent.com/a/ACg8ocLvVDbkmsKPGOwT0iOIMf2vuk_9CeWhI_SqyZObY73bVgk=s96-c","Nunsong Kim","Woman","Seoul","Yong San","MON, TUE","FTF","Hello nice to meet you."),
        Tutor("https://lh3.googleusercontent.com/a/ACg8ocLvVDbkmsKPGOwT0iOIMf2vuk_9CeWhI_SqyZObY73bVgk=s96-c","Sookmyung Lee","Woman","Seoul","Yong San","MON, TUE","NFTF","Hello nice to meet you.")
    )*/

    val tutors by viewModel.tutorList
    Log.d("MYTAGTUTORLISTSCREEN","${tutors}")
    var showBottomSheet by remember { mutableStateOf(false) }

    //bottom sheet 보여주기
    if (showBottomSheet) {
        FilterBottomSheet(
            onDismiss = {showBottomSheet = false},
            onClick = {
                viewModel.getTutor()
            }
        )
    }

    Scaffold(
        bottomBar = { LinkorBottomNavigation(navController = navController) },
        containerColor = Color(0xffFFFFFF)
    )
    {
        Surface(modifier = Modifier.padding(it),
            color=Color(0xffFFFFFF)
        ){
            Column(
                modifier = Modifier.padding(start=20.dp,end=20.dp, top = 10.dp)
            ) {
                //필터 버튼
                FilterButton { showBottomSheet = true }
                //튜터 리스트
                LazyColumn{
                    items(tutors.tutor){ tutor->
                        TutorListItem(tutor = tutor ) {
                            try {
                                /*navController.navigate(
                                    "tutor_detail/${tutor.toRouteString()}"
                                )*/
                                navController.navigate(
                                    "${Route.TUTOR_DETAIL}/${tutor.email}"
                                )
                            }catch (e:Exception){
                                Log.e("MYTAG","navigation 오류",e)
                            }

                        }
                    }
                }
            }
        }

    }

}
//튜터 리스트 아이템
@Composable
fun TutorListItem(
    modifier: Modifier =Modifier,
    tutor: Tutor,
    onClick: (Tutor)->Unit){
    Surface(onClick = { onClick(tutor) },
        modifier = modifier
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth(),
        color= Color(0xFFFFFFFF),
        border = BorderStroke(width = 1.dp, color = Color(0xFFE0E0E0))
    ) {
        Row(
            modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,

            ){
            //프로필
            AsyncImage(
                model = tutor.photoUrl,
                //model = tutor.photoUrl.ifEmpty { "https://lh3.googleusercontent.com/a/ACg8ocIEuFKe7nru_wkMIgvUTufE09Z5w6PKHfu8VjOTBWMutbro6XPdmQQAiG4qe_7U0ceWrEuOdWSxe9H6OSupUnWVtME=s576-c-no" },
                contentDescription = "Tutor Profile Image",
                modifier = Modifier
                    .size(100.dp, 100.dp)
                    .clip(RoundedCornerShape(50))
            )
            Column(modifier = modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            )
            {
                //이름
                Text(text=tutor.name,
                    style= MaterialTheme.typography.titleMedium)
                //성별
                Text(text=tutor.gender, style=MaterialTheme.typography.bodySmall)
                //위치
                Text(text=tutor.locationGu+", "+tutor.locationSido,style=MaterialTheme.typography.bodySmall)
                //시간
                Text(text=tutor.time.joinToString(),style=MaterialTheme.typography.bodySmall)
                //수업 방식
                Text(text=tutor.tutoringMethod,style=MaterialTheme.typography.bodySmall)
                //자기소개
                Text(
                    text=tutor.introduction,
                    style=MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }

}

@Composable
fun FilterButton(onClick:()->Unit){

    Button(onClick =  onClick ,
        colors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFFD9D9D9),
        contentColor = Color(0xFF4C6ED7),),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(text = stringResource(id = R.string.filter))
            Spacer(modifier = Modifier.width(10.dp))
            Image(painter = painterResource(id = R.drawable.ic_filter_down), contentDescription = "filter down")
        }
    }
}

/*@Composable
@Preview
fun FilterButtonPreview() {
    FilterButton()
}*/



@Composable
@Preview
fun TutorListPreview(){
    //val tutor = Tutor("https://lh3.googleusercontent.com/a/ACg8ocLvVDbkmsKPGOwT0iOIMf2vuk_9CeWhI_SqyZObY73bVgk=s96-c","Nunsong Kim","Woman","Seoul","Yong San","FTF","Hello nice to meet you. I'll teach you how to pronounce Korean by listening k-pop")
    val navController = rememberNavController()
    TutorListScreen(navController = navController)
}
