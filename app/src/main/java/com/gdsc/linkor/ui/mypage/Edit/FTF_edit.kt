package com.gdsc.linkor.ui.mypage.Edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gdsc.linkor.R
import com.gdsc.linkor.ui.component.MultipleButton

//FTF/NFTF 버튼들
@Composable
fun FTFEdit(){
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