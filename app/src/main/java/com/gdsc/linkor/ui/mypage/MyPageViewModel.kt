package com.gdsc.linkor.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc.linkor.model.Tutee
import com.gdsc.linkor.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val myPageRepository: MyPageRepository):ViewModel() {
    val tuteeList = MutableStateFlow<List<Tutee>>(emptyList())


    fun getTutee(email: String) {
        viewModelScope.launch {
            val response = myPageRepository.getTutee(email)
            response?.let {
                tuteeList.value = it.flatMap { sublist -> sublist } // Flatten the nested lists
            }
        }
    }
}