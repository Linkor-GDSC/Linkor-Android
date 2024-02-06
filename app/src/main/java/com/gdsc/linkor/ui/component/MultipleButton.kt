package com.gdsc.linkor.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MultipleButton(text: String, isChosen: Boolean, onClick: (Any) -> Unit){
    Button(onClick = { onClick(!isChosen) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isChosen) Color(0xFFD9D9D9) else Color.Transparent,
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