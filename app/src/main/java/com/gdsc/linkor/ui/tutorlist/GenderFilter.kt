package com.gdsc.linkor.ui.tutorlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gdsc.linkor.R
import com.gdsc.linkor.setting.QuestionViewModel
import com.gdsc.linkor.setting.question.Gender

@Composable
fun GenderFilter(
    viewModel: QuestionViewModel,
    possibleAnswers: List<Gender>,
    tutorListViewModel: TutorListViewModel= hiltViewModel()
)
{
    Row {
        //Spacer(modifier = Modifier.width(35.dp))
        //드롭다운 펼쳐짐 정의
        var isDropDownMenuExpanded by remember { mutableStateOf(false) }


        //var selectGender by remember { mutableStateOf(viewModel.GenderResponse) }
        var selectGender by remember{mutableStateOf(tutorListViewModel.finalSelectedGender)}

        Box(
            modifier = Modifier
                .size(width = 110.dp, height = 40.dp)

            ,
            contentAlignment = Alignment.Center
        ) {


            // 드롭다운 버튼
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = MaterialTheme.shapes.medium,
                onClick = { isDropDownMenuExpanded = true },
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Gray
                ),
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small)
                    .clickable(onClick = { isDropDownMenuExpanded = true })
                //  .wrapContentSize(Alignment.TopStart) //wrapcontent이녀석..
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()

                    ,
                ) {
                    (if (selectGender != null) {
                        //stringResource(id = selectGender!!.stringResourceId)
                        selectGender
                    } else {
                        "Gender"
                    })?.let {
                        Text( text =
                        it,
                            textAlign = TextAlign.Center, color = Color.Black, fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f)

                        )
                    }


                    Icon(
                        painter = painterResource(id = R.drawable.bottom_nav),
                        contentDescription = "bottom nav",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .size(24.dp)
                            .weight(0.5f)
                    )
                }


            }

            // 드롭다운 메뉴
            DropdownMenu(
                modifier = Modifier
                    .size(width = 110.dp, height = 150.dp)
                    .background(Color.White)
                //          .border(width = 1.dp, color = Color(R.color.primary_color))
                ,
                expanded = isDropDownMenuExpanded,
                onDismissRequest = { isDropDownMenuExpanded = false }
            ) {
                
                DropdownMenuItem(text =  {Text(text = "Man")},
                    onClick = {
                        isDropDownMenuExpanded = false
                        selectGender = "Man"
                        tutorListViewModel.selectGender(gender="Man")
                    }
                )

                DropdownMenuItem(text =  {Text(text = "Woman")},
                    onClick = {
                        isDropDownMenuExpanded = false
                        selectGender = "Woman"
                        tutorListViewModel.selectGender(gender="Woman")
                    }
                )

                DropdownMenuItem(text =  {Text(text = "Other")},
                    onClick = {
                        isDropDownMenuExpanded = false
                        selectGender = "Other"
                        tutorListViewModel.selectGender(gender="Other")
                    }
                )


                /*possibleAnswers.forEachIndexed { index, gender ->
                    DropdownMenuItem(
                        onClick = {
                            isDropDownMenuExpanded = false
                            //viewModel.onGenderResponse(gender)
                            //selectGender = gender
                        },
                        text = { Text(text = stringResource(id = gender.stringResourceId)) }

                    )
                }*/
            }

        }
    }


}