package com.gdsc.linkor.ui.mypage.Edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdsc.linkor.R
import com.gdsc.linkor.setting.QuestionViewModel
import com.gdsc.linkor.setting.question.cityDistrictsMap


@Composable
fun sidoDropdown(
    viewModel: QuestionViewModel,
)
{
    //드롭다운 펼쳐짐 정의
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }

    var selectedSido by remember { mutableStateOf(viewModel.selectedCity) }

    val cities = cityDistrictsMap.keys.toList()


Row {

    //Spacer(modifier = Modifier.width(35.dp))
    Box(
        modifier = Modifier
            .size(width = 125.dp, height = 40.dp)

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
                Row {
                    Spacer(Modifier.width(3.dp))

                    Text( text = selectedSido ?: "City" ,
                        textAlign = TextAlign.Center, color = Color.Black, fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )


                    Icon(
                        painter = painterResource(id = R.drawable.bottom_nav),
                        contentDescription = "bottom nav",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .size(24.dp)

                    )
                }


            }

            // 드롭다운 메뉴
            DropdownMenu(
                modifier = Modifier
                    .size(width = 125.dp, height = 300.dp)
                    .background(Color.White)
                //          .border(width = 1.dp, color = Color(R.color.primary_color))
                ,
                expanded = isDropDownMenuExpanded,
                onDismissRequest = { isDropDownMenuExpanded = false }
            ) {
                cities.forEachIndexed { index, cityName ->
                    DropdownMenuItem(
                        onClick = {
                            isDropDownMenuExpanded = false
                            viewModel.setSelectedCity(cityName)
                            selectedSido = cityName
                        },
                        text = { Text(cityName) }

                    )
                }
            }

    }
}


}

@Composable
fun gunguDropdown(
    viewModel: QuestionViewModel,
)
{
    //드롭다운 펼쳐짐 정의
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }


    var selectedGungu by remember { mutableStateOf(viewModel.selectedDistrict) }
    val selectedCity = viewModel.selectedCity

    val districts = cityDistrictsMap[selectedCity] ?: emptyList()

    Row {

        //Spacer(modifier = Modifier.width(35.dp))
        Box(
            modifier = Modifier
                .size(width = 125.dp, height = 40.dp)

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
                Row {
                    Text( text = selectedGungu ?: "District" ,
                        textAlign = TextAlign.Center, color = Color.Black, fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )


                    Icon(
                        painter = painterResource(id = R.drawable.bottom_nav),
                        contentDescription = "bottom nav",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .size(24.dp)

                    )
                }


            }

            // 드롭다운 메뉴
            DropdownMenu(
                modifier = Modifier
                    .size(width = 125.dp, height = 300.dp)
                    .background(Color.White)
                //          .border(width = 1.dp, color = Color(R.color.primary_color))
                ,
                expanded = isDropDownMenuExpanded,
                onDismissRequest = { isDropDownMenuExpanded = false }
            ) {
                districts.forEachIndexed { index, cityName ->
                    DropdownMenuItem(
                        onClick = {
                            isDropDownMenuExpanded = false
                            viewModel.setSelectedDisitrict(cityName)
                            selectedGungu = cityName
                        },
                        text = { Text(cityName) }

                    )
                }
            }

        }
    }


}