package com.gdsc.linkor.ui.signin

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gdsc.linkor.BuildConfig
import com.gdsc.linkor.R
import com.gdsc.linkor.data.UserPreferencesDataStore
import com.gdsc.linkor.navigation.Route
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(signInViewModel: SignInViewModel= hiltViewModel(), navController: NavController, userPreferencesDataStore: UserPreferencesDataStore){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        signInViewModel.googleSignIn(activityResult = it) { user->
            Toast.makeText(context, "Sign in Success", Toast.LENGTH_SHORT).show()
            if (user!=null){
                //기존 회원일 경우 세부조건 선택 건너뛰고 튜터 리스트로
                if (signInViewModel.isExistingMember){
                    navController.navigate(Route.TUTOR)
                }else{ // 신규 회원일 경우 세부조건 선택 화면으로
                    navController.navigate(Route.QUESTION_ROUTE)
                }
            }

            coroutineScope.launch {
                try {
                    if (user != null) {
                        //자동 로그인을 위해 사용자 이메일, 이름 저장
                      //  user.displayName?.let { it1 -> userPreferencesDataStore.saveName(it1) }
                       // user.email?.let { it1 -> userPreferencesDataStore.saveEmail(it1) }
                        Log.d("mytag","savedEmail: ${userPreferencesDataStore.savedEmail}")


                    }else{
                        Log.e("mytag","user is null")
                    }
                }catch (e:Exception){
                    Log.e("MYTAG","저장 실패",e)
                }

            }
        }


    }

    Surface(
        modifier= Modifier
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
                val googleSignInOptions = GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    //.requestIdToken(context.getString(R.string.web_client_id))
                    .requestIdToken(BuildConfig.FIREBASE_CLIENT_ID)
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
                launcher.launch(googleSignInClient.signInIntent)


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
            Text(text = "Sign in with Google", style = MaterialTheme.typography.overline, /*color = Color(0xFF000000).copy(alpha = 0.54f),*/ fontSize = 15.sp, fontWeight = FontWeight.Normal)
        }
    }
}