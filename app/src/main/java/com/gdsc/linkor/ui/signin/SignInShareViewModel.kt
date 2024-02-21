package com.gdsc.linkor.ui.signin

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


class SignInShareViewModel : ViewModel(){

    val userName = MutableLiveData<String?>()
    val userEmail = MutableLiveData<String?>()
    val userPhotoUrl = MutableLiveData<Uri?>()

    fun saveUserInfo(name: String?, email: String?, photoUrl:Uri?) {
        userName.value = name
        userEmail.value = email
        userPhotoUrl.value = photoUrl
    }

    /*  이름 정보 받아오기 */
    fun getName(): String? {
        return userName.value
    }

    /*  email 정보 받아오기 */

    fun getEmail(): String? {
        return userEmail.value
    }

    fun getImage(): Uri? {
        return userPhotoUrl.value
    }

    fun clearUserData() {
        // 여기에서 사용자 정보 초기화 또는 필요한 처리 수행
    }
}