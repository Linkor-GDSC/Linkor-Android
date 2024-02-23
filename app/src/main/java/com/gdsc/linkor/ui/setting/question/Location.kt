package com.gdsc.linkor.setting.question

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gdsc.linkor.R
import com.gdsc.linkor.setting.QuestionViewModel
import com.gdsc.linkor.setting.QuestionWrapper


data class WeekItem(
    val title: String,
    val checkedStatus: MutableState<Boolean>
)

@Composable
fun Location(
    @StringRes titleResourceId: Int,
    viewModel: QuestionViewModel ,
    modifier: Modifier = Modifier,
){

    val week: List<WeekItem> = remember {
        listOf(
            WeekItem("SUN", mutableStateOf(false)),
            WeekItem("MON", mutableStateOf(false)),
            WeekItem("TUE", mutableStateOf(false)),
            WeekItem("WED", mutableStateOf(false)),
            WeekItem("THU", mutableStateOf(false)),
            WeekItem("FRI", mutableStateOf(false)),
            WeekItem("SAT", mutableStateOf(false)),
            //WeekItem("ALL", mutableStateOf(false))
        )
    }
    val All: Boolean = week.all { it.checkedStatus.value }

    val allBoxChecked: (Boolean) -> Unit = { isAllBoxChecked ->
        week.forEach {
            it.checkedStatus.value = isAllBoxChecked
        }
    }


    QuestionWrapper(
        viewModel = QuestionViewModel(),
        titleResourceId = titleResourceId,
        modifier = modifier
            .selectableGroup()
            .fillMaxSize(),
    ) {
        Spacer(Modifier.height(32.dp))

        Text(
            text = "Location",
            fontStyle = FontStyle.Normal,
            fontSize = 19.sp,
            color = Color.Black,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )


        Row (
            modifier = Modifier
                .padding(16.dp, vertical = 20.dp)
                .fillMaxWidth()
        ){
            CityDropDown2(viewModel = viewModel)
            Spacer(modifier = Modifier.width(44.dp))
            DistrictDropDown2(viewModel = viewModel)
        }

        Spacer(Modifier.height(30.dp)) //로케이션과 여백으로 간격 조정


        Text(
            text = "Time" ,
            fontStyle = FontStyle.Normal,
            fontSize = 19.sp,
            color = Color.Black,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp, ),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally) ,
            modifier = Modifier
                .background(Color.Transparent)
                .padding(16.dp) //vertical로 time과 간격조
                .selectableGroup()
                .fillMaxWidth(),
            //  .heightIn(max = 220.dp)        // 최대 height 제한
            //   .wrapContentHeight(),		// wrapContent 설정
            userScrollEnabled = false       //스크롤 막기
        ){

            items(week.size) { index ->
                val weekItem = week[index]

                Weekend2(
                    viewModel = viewModel,
                    title = weekItem.title,
                    checkedStatus = weekItem.checkedStatus,
                    onClick = {
                        Log.d("mytag 시간 클릭", "클릭: ${week[index]}")
                        viewModel.toggleWeekItem(weekItem)
                    }
                )

                /*if (index == week.size - 1) {
                    AllAgree2(
                        viewModel = viewModel,
                        title = week[index].title,
                        shouldChecked = All,
                        allBoxChecked = allBoxChecked,
                    )
                }*/
            }


        }

    }

}

@Composable
fun AllAgree2(
    viewModel: QuestionViewModel,
    title: String,
    shouldChecked: Boolean,
    allBoxChecked: (Boolean) -> Unit,

    ){
    Box(
        modifier = Modifier
            /* week 요소 위아래 간격 조정 */
            .padding(vertical = 1.5.dp)
            .fillMaxSize()
            .clip(MaterialTheme.shapes.small)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center,
        content = {
            Surface(
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(
                    width = 1.dp,
                    color =if (shouldChecked)  Color(android.graphics.Color.parseColor("#4C6ED7"))
                    else Color.LightGray
                ),
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .padding(1.dp)
                    .clip(MaterialTheme.shapes.small)

                ,
                content = {
                    Row(
                        modifier = Modifier
                            .background(color = Color.Transparent)
                            .size(width = 134.02.dp, height = 40.17.dp)
                            .fillMaxWidth()
                            .clickable {
                                allBoxChecked(!shouldChecked)
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Spacer(modifier = Modifier.width(13.dp))
                            Text(
                                text = title,
                                color = if (shouldChecked)  Color(android.graphics.Color.parseColor("#4C6ED7"))
                                else Color.Black // 선택되었을 때 텍스트 색상 변경
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Checkbox(
                                checked = shouldChecked,
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(android.graphics.Color.parseColor("#4C6ED7")),
                                    uncheckedColor = Color.LightGray,
                                    checkmarkColor = Color.White
                                ),
                                onCheckedChange = {
                                    allBoxChecked(it)
                                })
                        }
                    )
                }
            )
        }
    )
}


@Composable
fun Weekend2(
    viewModel: QuestionViewModel,
    title: String,
    checkedStatus: MutableState<Boolean>,
    onClick: () -> Unit // 클릭 이벤트 핸들러 추가
) {

    Box(
        modifier = Modifier
            /* week 요소 위아래 간격 조정  -> allagree도 똑같이 변화 시켜야함 */
            .padding(vertical = 1.5.dp)
            .fillMaxSize()
            .clip(MaterialTheme.shapes.small)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center,
        content = {
            Surface(
                color = Color.Transparent,
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(
                    width = 1.dp,
                    color = if (checkedStatus.value)  Color(android.graphics.Color.parseColor("#4C6ED7"))
                    else Color.LightGray
                ),
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .padding(1.dp)
                    .clip(MaterialTheme.shapes.small)
                    .clickable {
                        onClick.invoke()
                    },

                content = {
                    Spacer(modifier = Modifier.width(13.dp))
                    Row(
                        modifier = Modifier
                            .background(color = Color.Transparent)
                            .size(width = 134.02.dp, height = 40.17.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Spacer(modifier = Modifier.width(13.dp))
                            Text(
                                text = title,
                                color = if (checkedStatus.value)  Color(android.graphics.Color.parseColor("#4C6ED7"))
                                else Color.Black // 선택되었을 때 텍스트 색상 변경
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Checkbox(
                                checked = checkedStatus.value,

                                /* modifier = Modifier
                                     .size(24.dp), // 네모 모양의 checkbox 안 보이도록 크기 조절*/
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(android.graphics.Color.parseColor("#4C6ED7")),
                                    uncheckedColor = Color.LightGray,
                                    checkmarkColor = Color.White
                                ),
                                onCheckedChange = {
                                    onClick.invoke()
                                    checkedStatus.value = it


                                },
                                //modifier = Modifier.clickable { onClick.invoke() } // Checkbox 클릭 이벤트 추가
                            )
                        }
                    )
                }
            )
        }
    )
}



@Composable
fun CityDropDown2(viewModel: QuestionViewModel){
    //드롭다운 펼쳐짐 정의
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var selectedIndex by remember{ mutableStateOf(0) }
    var selectedSido by remember { mutableStateOf(viewModel.selectedCity) }
    val cities = cityDistrictsMap.keys.toList()

    Box(
        modifier = Modifier
           .size(width = 142.29.dp, height = 50.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
            ,
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

                Text(text = selectedSido ?: "City", textAlign = TextAlign.Center, color = Color.Black)

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    painter = painterResource(id = R.drawable.bottom_nav),
                    contentDescription = "bottom nav",
                    tint = Color.DarkGray
                )
            }

                // 드롭다운 메뉴
                DropdownMenu(
                    modifier = Modifier.size(width = 142.29.dp, height = 350.dp)
                        .background(Color.White)
              //          .border(width = 1.dp, color = Color(R.color.primary_color))
                    ,
                    expanded = isDropDownMenuExpanded,
                    onDismissRequest = { isDropDownMenuExpanded = false }
                ) {
                    cities.forEachIndexed { index, cityName ->
                        DropdownMenuItem(
                            onClick = {
                                selectedIndex = index
                                isDropDownMenuExpanded = false
                                viewModel.setSelectedCity(cityName)
                                selectedSido = cityName
                            },
                            text = {Text(cityName)}

                        )
                    }
                }
        }
    }

}




@Composable
fun DistrictDropDown2(viewModel: QuestionViewModel){
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var selectedIndex by remember{ mutableStateOf(0) }

    var selectedGungu by remember { mutableStateOf(viewModel.selectedDistrict) }
    val selectedCity = viewModel.selectedCity

    val districts = cityDistrictsMap[selectedCity] ?: emptyList()

    Box(
        modifier = Modifier
            .size(width = 142.29.dp, height = 50.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
            ,
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
            ) {
                Text(text = selectedGungu ?: "District", textAlign = TextAlign.Center, color = Color.Black)

                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.bottom_nav),
                    contentDescription = "bottom nav",
                    tint = Color.DarkGray
                )
            }

            DropdownMenu(
                modifier = Modifier.size(width = 142.29.dp, height = 350.dp)
                    .background(Color.White)
                //          .border(width = 1.dp, color = Color(R.color.primary_color))
                ,
                expanded = isDropDownMenuExpanded,
                onDismissRequest = { isDropDownMenuExpanded = false }
            ) {
                districts.forEachIndexed { index, cityName ->
                    DropdownMenuItem(
                        onClick = {
                            selectedIndex = index
                            isDropDownMenuExpanded = false
                            viewModel.setSelectedDisitrict(cityName)
                            selectedGungu = cityName
                        },
                        text = {Text(cityName)}

                    )
                }
            }
        }
    }

}

/*
@Preview(showBackground = true)
@Composable
fun DropdownMenuPreview2() {

    Location2(
        titleResourceId = R.string.titleLocation,
        viewModel = viewModel
        )
}*/
