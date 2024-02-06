package com.gdsc.linkor.ui.mypage


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdsc.linkor.R
import com.gdsc.linkor.setting.QuestionViewModel
import com.gdsc.linkor.setting.question.Gender

import com.gdsc.linkor.ui.component.GenderDropDown2
import com.gdsc.linkor.ui.component.gunguDropdown
import com.gdsc.linkor.ui.mypage.Edit.introEdit
import com.gdsc.linkor.ui.component.sidoDropdown


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDemo(
    viewModel: QuestionViewModel,
    onDismiss: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss()},
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle()},
        containerColor = Color.White/*Color(android.graphics.Color.parseColor("#FFFBF5"))*/ ,
        contentColor = Color.Black,
    ){
        MypageEdit(viewModel = viewModel)
    }


}

@Composable
fun MypageEdit(viewModel: QuestionViewModel){
    
    Column(
        modifier = Modifier
            .fillMaxSize()
        
    ) {
        Spacer(Modifier.height(30.dp))

        Text(text = "Gender",
            fontStyle = FontStyle.Normal,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 35.dp)
            )
        Spacer(Modifier.height(20.dp))

        /*  성별 수정 */

        GenderDropDown2(  viewModel , possibleAnswers = listOf(
            Gender(R.string.Woman),
            Gender(R.string.Man),
            Gender(R.string.Other),
        ))


        Spacer(Modifier.height(30.dp))

        Text(text = "Location",
            fontStyle = FontStyle.Normal,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 35.dp)
        )

        /*  위치 수정 */
        Spacer(Modifier.height(20.dp))

        Row {
            sidoDropdown(
                viewModel
            )
            gunguDropdown( viewModel )
        }



        Spacer(Modifier.height(30.dp))

        Text(text = "Time",
            fontStyle = FontStyle.Normal,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 35.dp)
        )

        Spacer(Modifier.height(30.dp))

        Text(text = "FTF/NFTF",
            fontStyle = FontStyle.Normal,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 35.dp)
        )

        Spacer(Modifier.height(30.dp))

        Text(text = "Self-Introudction",
            fontStyle = FontStyle.Normal,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 35.dp)
        )

        introEdit(viewModel=viewModel)

        Spacer(Modifier.height(30.dp))
        
        Button(onClick = {   },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors( Color(android.graphics.Color.parseColor("#4C6ED7"))),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)


        ) {
            Text(text = "Save",
                fontStyle = FontStyle.Normal,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    //  버튼 위아래 크기 조절
                    .padding(vertical = 10.dp)

            )
        }

        Spacer(Modifier.height(30.dp))


    }

}


