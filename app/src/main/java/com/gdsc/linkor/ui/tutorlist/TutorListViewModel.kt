
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
    var finalSelectedGender by mutableStateOf(selectedGender)

    var selectedLocationSido by mutableStateOf<String?>(null)
    var finalSelectedLocationSido by mutableStateOf<String?>(selectedLocationSido)
    var selectedLocationGu by mutableStateOf<String?>(null)
    var finalSelectedLocationGu by mutableStateOf<String?>(selectedLocationGu)

    var selectedTutoringMethod by mutableStateOf<String?>(null)
    var finalSelectedTutoringMethod by mutableStateOf(selectedTutoringMethod)

    var selectedDaysOfWeek by mutableStateOf(listOf(false, false, false, false, false, false, false))
    var finalSelectedDaysOfWeek by mutableStateOf(selectedDaysOfWeek)
    var time1 by mutableStateOf<String?>(null)
    var time2 by mutableStateOf<String?>(null)
    var time3 by mutableStateOf<String?>(null)
    var time4 by mutableStateOf<String?>(null)
    var time5 by mutableStateOf<String?>(null)
    var time6 by mutableStateOf<String?>(null)
    var time7 by mutableStateOf<String?>(null)


    init {
        getTutor()
    }

    //모든 선택 저장
    fun filterAll(){

    }

    //위치 선택
    fun selectSido(sido:String?){
        selectedLocationSido = sido
    }
    fun selectGu(gu:String?){
        selectedLocationGu = gu
    }

    //위치 저장
    fun filterLocation(){
        finalSelectedLocationSido=selectedLocationSido
        finalSelectedLocationGu=selectedLocationGu
    }


    //성별 선택
    fun selectGender(gender:String?){
        selectedGender=gender
    }

    //성별 저장
    fun filterGenderMethod(){
        finalSelectedGender=selectedGender
    }

    //튜터링 방식 선택
    fun selectTutoringMethod(tutoringMethod:String?){
        selectedTutoringMethod=tutoringMethod
    }

    //튜터링 방식 저장
    fun filterTutoringMethod(){
        finalSelectedTutoringMethod=selectedTutoringMethod
    }

    //요일 선택
    fun selectDaysOfWeek(days: List<Boolean>) {
        selectedDaysOfWeek = days
    }

    //요일 저장
    fun filterDaysOfWeek(){
        finalSelectedDaysOfWeek = selectedDaysOfWeek
        for (i in finalSelectedDaysOfWeek.indices){
            when (i) {
                0 -> time1 = if (finalSelectedDaysOfWeek[i]) "SUN" else null
                1 -> time2 = if (finalSelectedDaysOfWeek[i]) "MON" else null
                2 -> time3 = if (finalSelectedDaysOfWeek[i]) "TUE" else null
                3 -> time4 = if (finalSelectedDaysOfWeek[i]) "WED" else null
                4 -> time5 = if (finalSelectedDaysOfWeek[i]) "THU" else null
                5 -> time6 = if (finalSelectedDaysOfWeek[i]) "FRI" else null
                6 -> time7 = if (finalSelectedDaysOfWeek[i]) "SAT" else null
            }
        }
    }


    fun getTutor() {
        viewModelScope.launch {
            try{
                _tutorList.value = tutorRepository.getAllTutor(
                    gender = finalSelectedGender,
                    locationSido=finalSelectedLocationSido,
                    locationGu = finalSelectedLocationGu,
                    tutoringMethod = finalSelectedTutoringMethod,
                    time1 = time1,
                    time2 = time2,
                    time3 = time3,
                    time4 = time4,
                    time5 = time5,
                    time6 = time6,
                    time7 = time7
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
