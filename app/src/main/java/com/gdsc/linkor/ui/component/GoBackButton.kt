package com.gdsc.linkor.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gdsc.linkor.R

@Composable
fun GoBackButton(navController:NavController){
    //뒤로 가기 버튼
    IconButton(onClick = { navController.navigateUp() }) {
        Image(painter = painterResource(id = R.drawable.ic_arrow_left), contentDescription = "arrow left",
            modifier = Modifier
                .padding(8.dp),
            alignment = Alignment.TopStart)
    }
}