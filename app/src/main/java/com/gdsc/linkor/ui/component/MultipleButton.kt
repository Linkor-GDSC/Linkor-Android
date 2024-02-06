package com.gdsc.linkor.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdsc.linkor.R

@Composable
fun MultipleButton(text: String, isChosen: Boolean, onClick: (Any) -> Unit){
    Button(onClick = { onClick(!isChosen) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isChosen) Color(0xFFD9D9D9) else Color(0xFFE0E0E0),
            //contentColor = if (isChosen) Color.White else Color(0xFF000000)
            contentColor = Color(0xFF000000)
        ),
        border = BorderStroke(width = 1.dp, color = Color(0xFFD9D9D9)),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(horizontal = 11.dp)
    )
    {
        Text(text = text)
    }
}

@Composable
@Preview
fun TimeButtonsPreview(){
    TimeButtons()
}

//Time 버튼들
@Composable
fun TimeButtons() {

    var daysOfWeek by remember {
        mutableStateOf(listOf(false, false, false, false, false, false, false))
    }

    Column(
        modifier= Modifier
            .fillMaxWidth(),
        //horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        val buttonSpace = 10.dp
        Row(horizontalArrangement  = Arrangement.spacedBy(buttonSpace)) {
            for (i in 0 until 4) {
                MultipleButton(
                    text = stringResource(
                        id = when (i) {
                            0 -> R.string.Sun
                            1 -> R.string.Mon
                            2 -> R.string.Tue
                            3 -> R.string.Wed
                            else -> throw IllegalArgumentException("Invalid index: $i")
                        }
                    ),
                    isChosen = daysOfWeek[i],
                    onClick = { daysOfWeek = daysOfWeek.toMutableList().apply { set(i, !get(i)) } }
                )
            }
        }

        Row(horizontalArrangement  = Arrangement.spacedBy(buttonSpace),) {
            for (i in 4 until 7) {
                MultipleButton(
                    text = stringResource(
                        id = when (i) {
                            4 -> R.string.Thu
                            5 -> R.string.Fri
                            6 -> R.string.Sat
                            else -> throw IllegalArgumentException("Invalid index: $i")
                        }
                    ),
                    isChosen = daysOfWeek[i],
                    onClick = { daysOfWeek = daysOfWeek.toMutableList().apply { set(i, !get(i)) } }
                )
            }

            MultipleButton(
                text = stringResource(id = R.string.All),
                isChosen = daysOfWeek.all { it },
                onClick = {
                    daysOfWeek = List(7) { !daysOfWeek.all { it } }
                }
            )
        }
    }
}

//FTF/NFTF 버튼들
@Composable
fun TutoringMethodButtons(){
    val buttonSpace = 10.dp
    var isFTFChosen by remember { mutableStateOf(false) }
    var isNFTFChosen by remember { mutableStateOf(false) }
    Row(horizontalArrangement  = Arrangement.spacedBy(buttonSpace),){
        MultipleButton(text = stringResource(id = R.string.ftf),
            isChosen = isFTFChosen,
            onClick = {
                isFTFChosen=!isFTFChosen
                if(isNFTFChosen){isNFTFChosen=false}

            })
        MultipleButton(text = stringResource(id = R.string.nftf),
            isChosen = isNFTFChosen,
            onClick = {
                isNFTFChosen=!isNFTFChosen
                if(isFTFChosen){isFTFChosen=false}
            })
    }
}