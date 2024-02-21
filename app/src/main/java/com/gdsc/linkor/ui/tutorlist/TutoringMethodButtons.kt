package com.gdsc.linkor.ui.tutorlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gdsc.linkor.R
import com.gdsc.linkor.ui.component.MultipleButton

//FTF/NFTF 버튼들
@Composable
fun TutoringMethodButtons(tutorListViewModel: TutorListViewModel= hiltViewModel()){
    val buttonSpace = 10.dp
    /*var isFTFChosen by remember { mutableStateOf(false) }
    var isNFTFChosen by remember { mutableStateOf(false) }*/
    /*val isFTFChosen= tutorListViewModel.selectedTutoringMethod == stringResource(id = R.string.ftf)
    val isNFTFChosen = tutorListViewModel.selectedTutoringMethod == stringResource(id = R.string.nftf)*/

    var isFTFChosen by remember { mutableStateOf(tutorListViewModel.finalSelectedTutoringMethod == "FTF") }
    var isNFTFChosen by remember { mutableStateOf(tutorListViewModel.finalSelectedTutoringMethod == "NFTF")}

        /*if (isFTFChosen){
            tutorListViewModel.selectTutoringMethod(stringResource(id = R.string.ftf))
        }
        if (isNFTFChosen){
            tutorListViewModel.selectTutoringMethod(stringResource(id = R.string.nftf))
        }*/

    Row(horizontalArrangement  = Arrangement.spacedBy(buttonSpace),){
        MultipleButton(text = stringResource(id = R.string.ftf),
            isChosen = isFTFChosen,
            onClick = {
                tutorListViewModel.selectTutoringMethod(if (isFTFChosen) null else "FTF")
                isFTFChosen=!isFTFChosen
                if(isNFTFChosen){isNFTFChosen=false}

                //tutorListViewModel.selectTutoringMethod(if (isFTFChosen) "FTF" else null)

            })
        MultipleButton(text = stringResource(id = R.string.nftf),
            isChosen =isNFTFChosen,
            onClick = {
                tutorListViewModel.selectTutoringMethod(if (isNFTFChosen) null else "NFTF")
                isNFTFChosen=!isNFTFChosen
                if(isFTFChosen){isFTFChosen=false}

                //tutorListViewModel.selectTutoringMethod(if (isNFTFChosen) "NFTF" else null)
            })
    }
}