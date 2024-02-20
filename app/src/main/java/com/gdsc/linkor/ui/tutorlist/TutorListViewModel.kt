
package com.gdsc.linkor.ui.tutorlist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc.linkor.model.Tutor
import com.gdsc.linkor.model.TutorDetailResponse
import com.gdsc.linkor.model.TutorListResponse
import com.gdsc.linkor.repository.TutorRepository
import com.gdsc.linkor.setting.question.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorListViewModel @Inject constructor(private val tutorRepository: TutorRepository):ViewModel() {

    private val _tutorList = mutableStateOf(TutorListResponse(emptyList(),"",""))
    val tutorList: State<TutorListResponse> = _tutorList

    private val _tutorDetail = mutableStateOf<TutorDetailResponse>(TutorDetailResponse(Tutor(),"",""))
    val tutorDetail:State<TutorDetailResponse> = _tutorDetail

    var selectedGender by mutableStateOf<String?>(null)
    var selectedLocationSido by mutableStateOf(null)
    var selectedLocationGu by mutableStateOf(null)
    var selectedTime by mutableStateOf(null)
    var selectedTutoringMethod by mutableStateOf<String?>(null)


    /*private val _tutorList = MutableLiveData<List<Tutor>>()
    val tutorList: LiveData<List<Tutor>> get() = _tutorList*/


    init {
        getAllTutor()
    }

    /*fun selectGender(gender: String) {
        selectedGender=gender
    }*/

    fun selectTutoringMethod(tutoringMethod:String?){
        selectedTutoringMethod=tutoringMethod
    }


    fun getAllTutor() {
        viewModelScope.launch {
            try{
                _tutorList.value = tutorRepository.getAllTutor(
                    gender = selectedGender,
                    locationSido=selectedLocationSido,
                    locationGu = selectedLocationGu,
                    tutoringMethod = selectedTutoringMethod,
                    time = selectedTime
                )!!
            }catch (e:Exception){
                Log.e("MYTAGTutorViewModel","tutor list api error",e)
            }

        }
    }
    fun getTutorDetail(email:String){
        viewModelScope.launch {
            try{
                _tutorDetail.value = tutorRepository.getTutorDetail(email=email)!!
            }catch (e:Exception){
                Log.e("MYTAGTutorViewModel","tutor detail api error",e)
            }
        }
    }
}
