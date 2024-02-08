@file:OptIn(ExperimentalMaterial3Api::class)

package com.gdsc.linkor.ui.tutorlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdsc.linkor.R
import com.gdsc.linkor.setting.QuestionViewModel
import com.gdsc.linkor.setting.question.Gender
import com.gdsc.linkor.ui.component.GenderDropDown2
import com.gdsc.linkor.ui.component.TimeButtons
import com.gdsc.linkor.ui.component.TutoringMethodButtons
import com.gdsc.linkor.ui.component.sidoDropdown
import com.gdsc.linkor.ui.component.gunguDropdown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(onDismiss: () -> Unit){
    val filterBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    //val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState=filterBottomSheetState,
        /*modifier=Modifier.heightIn(
            min = 800.dp,
        )*/
    )

    {
        BottomSheetContent()
    }

}

@Composable
fun BottomSheetContent(){
    val viewModel = QuestionViewModel()
    Surface{
        Column(
            modifier = Modifier.padding(horizontal = 45.dp,vertical=30.dp),
            //verticalArrangement = Arrangement.spacedBy(40.dp)
        )
        {
            val spaceSmall = 10.dp
            val spaceBig = 50.dp
            //성별
            Text(text="Gender")
            Spacer(modifier = Modifier.height(spaceSmall))
            GenderDropDown2(viewModel = viewModel, possibleAnswers = listOf(
                Gender(R.string.Woman),
                Gender(R.string.Man),
                Gender(R.string.Other),
            ))
            Spacer(modifier = Modifier.height(spaceBig))
            //지역
            Text(text="Location")
            Spacer(modifier = Modifier.height(spaceSmall))
            Row{
                sidoDropdown(viewModel)
                Spacer(modifier = Modifier.width(35.dp))
                gunguDropdown(viewModel)
            }
            Spacer(modifier = Modifier.height(spaceBig))
            //날짜
            Text(text="Time")
            Spacer(modifier = Modifier.height(spaceSmall))
            TimeButtons()
            Spacer(modifier = Modifier.height(spaceBig))
            //대면/비대면
            Text(text="FTF/NFTF")
            Spacer(modifier = Modifier.height(spaceSmall))
            TutoringMethodButtons()
            Spacer(modifier = Modifier.height(spaceBig))

            //누르면 필터링
            SearchButton()
        }

    }
}

//드롭다운
/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(dropDownMenuItemList:List<String>){
    var isExpanded by remember { mutableStateOf(false) }

    var value by remember { mutableStateOf("Woman") }
    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {isExpanded=it}) {

        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                //textColor = Black
            ),
            modifier = Modifier
                .menuAnchor()
                .border(2.dp, color = Color(0xFFE0E0E0), shape = RoundedCornerShape(10.dp))
                .width(120.dp)
                .height(50.dp)
        )
        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded=false }
        ) {
            for(item in dropDownMenuItemList){
                DropdownMenuItem(
                    text = { Text(text=item) },
                    onClick = {
                        value=item
                        isExpanded = false })
            }
        }
    }
}

@Composable
fun GenderDropDown(){
    DropDown(dropDownMenuItemList = listOf("Woman","Man"))
}

@Composable
fun LocationDropDown(){
    Row{
        DropDown(dropDownMenuItemList = listOf("Seoul","GyeongGi"))
        Spacer(modifier = Modifier.width(50.dp))
        DropDown(dropDownMenuItemList = listOf(""))
    }
}*/


@Preview
@Composable
fun FilterBottomSheetPreview(){
    BottomSheetContent()
}


@Composable
fun SearchButton(/*onClick: () -> Unit*/){
    Button(onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4C6ED7),
            contentColor = Color(0xFFFDFDFD),),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp),
        contentPadding = PaddingValues(vertical = 20.dp)
    ) {
        Text(text = stringResource(id = R.string.search))
    }
}