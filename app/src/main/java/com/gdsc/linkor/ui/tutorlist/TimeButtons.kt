package com.gdsc.linkor.ui.tutorlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gdsc.linkor.R
import com.gdsc.linkor.ui.component.MultipleButton

//Time 버튼들
@Composable
fun TimeButtons(tutorListViewModel: TutorListViewModel= hiltViewModel()) {

    /*var daysOfWeek by remember {
        mutableStateOf(listOf(true, false, false, false, false, false, false))
    }*/
    var daysOfWeek by remember {
        mutableStateOf(tutorListViewModel.finalSelectedDaysOfWeek)
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
                    //onClick = { daysOfWeek = daysOfWeek.toMutableList().apply { set(i, !get(i)) } }
                    onClick={
                        daysOfWeek = daysOfWeek.toMutableList().apply { set(i, !get(i)) }
                        tutorListViewModel.selectDaysOfWeek(daysOfWeek)
                    }
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
                    onClick = {
                        //daysOfWeek = daysOfWeek.toMutableList().apply { set(i, !get(i)) }
                        daysOfWeek = daysOfWeek.toMutableList().apply { set(i, !get(i)) }
                        tutorListViewModel.selectDaysOfWeek(daysOfWeek)
                    }
                )
            }

            /*MultipleButton(
                text = stringResource(id = R.string.All),
                isChosen = daysOfWeek.all { it },
                onClick = {
                    daysOfWeek = List(7) { !daysOfWeek.all { it } }
                }
            )*/
        }
    }
}