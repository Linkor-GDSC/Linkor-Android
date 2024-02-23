package com.gdsc.linkor.ui.signin

import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc.linkor.repository.SignInRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(/*private val repository: SignInRepository*/): ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _isFailState = mutableStateOf(false)
    //val isFailState: State<Boolean> = _isFailState
    var isExistingMember = false


    fun googleSignIn(
        activityResult: ActivityResult,
        onSuccess: (FirebaseUser?) -> Unit
    ) {
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
                .getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val name = user?.displayName
                        val email = user?.email
                        val photoUrl = user?.photoUrl
                        Log.d("mytag 로그인 완료","유저 이메일: $email 유저 이름: $name")

                        if (email != null) {
                            viewModelScope.launch {
                                //isExistingMember = checkExistingMember(email = email)
                                //Log.d("MyTag check", "기존 멤버인가? ${isExistingMember}")
                                onSuccess(user)
                            }
                        }

                        Log.d("MyTagfirebaseauthwithgoogle","이름: $name 이메일: $email")
                    }
                }
        } catch (e: Exception) {
            _isFailState.value = true
            Log.w("MYTAG VIEWMODEL2", "signInWithCredential:failure", e)
        }
    }

    /*suspend fun checkExistingMember(email: String): Boolean {
        return try {
            repository.checkExistingMember(email = email) == true
        } catch (e: Exception) {
            Log.e("MYTAGSingInViewModel", "signin api error", e)
            false
        }
    }*/


    /*  이름 정보 받아오기 */
    fun getName(): String? {
        return auth.currentUser?.displayName
    }

    /*  email 정보 받아오기 */

    fun getEmail(): String? {
        return auth.currentUser?.email
    }

    fun getImage(): Uri? {
        return auth.currentUser?.photoUrl
    }

    fun clearUserData() {
        // 여기에서 사용자 정보 초기화 또는 필요한 처리 수행
    }
}