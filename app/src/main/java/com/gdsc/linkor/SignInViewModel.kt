package com.gdsc.linkor

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent


import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignInViewModel():ViewModel() {


    private val auth: FirebaseAuth = Firebase.auth

    //private lateinit var signInLauncher: ActivityResultLauncher<Intent>


    fun signInWithGoogle(context: Context, launcher: (Intent, (Int, Intent?) -> Unit) -> Unit) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, gso)

        val signInIntent = googleSignInClient.signInIntent
        launcher(signInIntent) { resultCode, data ->
            handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            // Google 로그인 실패
            Log.w("MyTaghandlesignin", "Google sign in failed", e)
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Firebase 로그인 성공
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    val name = user?.displayName
                    val email = user?.email
                    Log.d("MyTagfirebaseauthwithgoogle","이름: $name 이메일: $email")
                } else {
                    // Firebase 로그인 실패
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // 추가적인 작업 수행
                }
            }
    }




    // 로그인 결과 반환 변수
    private val _signInResult = MutableSharedFlow<Boolean>()
    var signInResult = _signInResult.asSharedFlow()

    fun trySignIn(context: Context) {
        viewModelScope.launch {
            val account = async {
                getLastSignedInAccount(context)
            }
            delay(2500)
            // 계정 확인 -> true, 없음 -> false 반환
            setLoginResult(account.await() != null)
            //Log.d("MyTagViewModel","사용자: $account")
        }
    }

    // 이전에 로그인 한 계정이 있는지 확인
    private fun getLastSignedInAccount(context: Context) = GoogleSignIn.getLastSignedInAccount(context)

    private fun setLoginResult(isSignIn: Boolean) {
        viewModelScope.launch {
            _signInResult.emit(isSignIn)
        }
    }




    // 구글 계정 로그인(회원가입) 메소드
    fun onGoogleSignInAccount(account: GoogleSignInAccount?) {
        try{
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                Log.d("MyTagSignInViewModel", "계정 가저오기 성공")

                // 구글 로그인 성공시
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task -> // task : 사용자 및 결과 정보를 가지고 있음
                        if (task.isSuccessful) {
                            Log.d("MyTagSignInViewModel", "파이어베이스 인증 성공")
                            onSignInGoogleAuthTask(task) // 로그인 성공 후 수행할 메소드 호출
                        }else{
                            Log.e("MyTagSignInViewModel", "파이어베이스 인증 실패", task.exception)
                            val exceptionMessage = task.exception?.message
                            Log.e("MyTagSignInViewModel", "Exception Message: $exceptionMessage")
                        }

                    }
            }else{
                Log.e("MyTagSignInViewModel", "계정 가저오기 실패")
            }
        }catch (e: ApiException) {
            Log.e("MyTagSignViewModel","계정 가져오기 에러",e)
        }
    }

    // 파이어베이스 구글 로그인 성공시 수행할 메소드
    private fun onSignInGoogleAuthTask(task: Task<AuthResult>) {
        viewModelScope.launch {
            try{
                if (task.isSuccessful) {
                    Log.d("MyTagSignInViewModel","파이어베이스 로그인 성공")

                    val user = auth.currentUser
                    val name = user?.displayName
                    val email = user?.email
                    //val photoUrl = user?.photoUrl
                    // val uId = user.getIdToken()
                    //Log.d("MyTagSignInViewModela","이름: $name 이메일: $email 프로필: $photoUrl")
                    TODO("서버에 이메일 전송, 신규 회원인지 아니면 기존 회원인지 결과값 받아오기")
                    TODO("기존 회원일 경우 - 튜터 목록 화면으로 넘어가기")
                    TODO("신규 회원일 경우 - sharedpreference로 이메일 저장, 세부조건 화면으로 넘어가기")
                    // repository를 통해 회원가입 요구(중복 계정일 경우 백엔드에서 처리)
                    //userRepository.register(User(auth.uid!!, auth.currentUser?.email, auth.currentUser?.displayName!!, LoginType.GOOGLE.name))

                } else { // Google로 로그인 실패
                    Log.e("MyTagSignInViewModel","파이어베이스 로그인 실패")
                }
            }catch (e: ApiException){
                Log.e("MyTagSignInViewModel", "파이어베이스 인증 에러", e)
            }
        }
    }
}









