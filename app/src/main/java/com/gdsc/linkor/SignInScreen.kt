package com.gdsc.linkor

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

@Composable
fun SignInScreen (signInViewModel: SignInViewModel) {
    val coroutineScope = rememberCoroutineScope()

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    Log.e("MyTagSignInScreen","account is null")
                } else {
                    Log.d("MyTagSignInScreen","account success")
                    coroutineScope.launch {
                        signInViewModel.onGoogleSignInAccount(account)
                    }
                }
            } catch (e: ApiException) {
                Log.e("MyTagSignInScreen","account error")
            }
        }
    Surface(
        modifier=Modifier
            .fillMaxHeight(),
        color = Color(0xFF4C6ED7),
    ) {
        Column (modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ){
            LinkorLogo()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 35.dp, end = 35.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            SignInGoogleButton {
                authResultLauncher.launch(123)
            }
        }
    }

}
@Composable
fun LinkorLogo(){
    Image(painter = painterResource(id = R.drawable.ic_linkor_logo),
        contentDescription = "Linkor Logo",
        modifier = Modifier.size(130.dp,50.dp),
    )
}


@Composable
fun SignInGoogleButton(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        color = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.small,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(
                top = 15.dp,
                bottom = 15.dp
            )
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_google), contentDescription = "Google sign button", tint = Color.Unspecified, modifier = Modifier.size(35.dp))
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = "Sign in with Google", style = MaterialTheme.typography.overline, color = Color(0xFF000000).copy(alpha = 0.54f), fontSize = 15.sp, fontWeight = FontWeight.Normal)
        }
    }
}
