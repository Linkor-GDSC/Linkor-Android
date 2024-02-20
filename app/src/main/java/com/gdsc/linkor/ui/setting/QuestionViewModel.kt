package com.gdsc.linkor.setting

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gdsc.linkor.setting.question.Communication
import com.gdsc.linkor.setting.question.Gender
import com.gdsc.linkor.setting.question.WeekItem
import kotlinx.coroutines.MainScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class Questions {
    Mode,
    Gender,
    Location,
    Communication,
    Introduction,
}

class QuestionViewModel(): ViewModel(){
    private val questionOrder: List<Questions> = listOf(
        Questions.Mode,
        Questions.Gender,
        Questions.Location,
        Questions.Communication,
        Questions.Introduction,

    )
    private var questionIndex = 0

    /*  student / tutor 선택 창  */

    private val _ModeResponse = mutableStateOf<Mode?>(null)
    val ModeResponse: Mode?
        get()= _ModeResponse.value

    /*  성별 지정 창 */

    private val _GenderResponse = mutableStateOf<Gender?>(null)
    val GenderResponse: Gender?
        get() = _GenderResponse.value


    /*  지역, 요일 지정 창 */
    /*  db연동시 selectedWeeks 활용가능  */


   //도시 지정
    private val _selectedCity = mutableStateOf<String?>(null) // 선택된 값의 초기값을 설정
    val selectedCity: String?
        get() = _selectedCity.value

    //군구 지정
    private val _selectedDistrict = mutableStateOf<String?>(null) // 선택된 값의 초기값을 설정
    val selectedDistrict: String?
        get() = _selectedDistrict.value

    // 선택된 week 아이템 리스트
    private val _selectedWeeks = mutableStateListOf<WeekItem>()
    val selectedWeeks: List<WeekItem>
        get() = _selectedWeeks

    // Week 아이템을 선택 또는 해제하는 함수
    fun toggleWeekItem(weekItem: WeekItem) {
        weekItem.checkedStatus.value = !weekItem.checkedStatus.value
        if (weekItem.checkedStatus.value) {
            _selectedWeeks.add(weekItem)
        } else {
            _selectedWeeks.remove(weekItem)
        }
    }

    /*  대면/비대면 지정 창 */

    private val _CommunicationResponse = mutableStateOf<Communication?>(null)
    val CommunicationResponse: Communication?
        get() = _CommunicationResponse.value


    /*  자기소개 지정 창 */

    var  intro by mutableStateOf("")




    private val _questionScreenData = mutableStateOf(createQuestionScreenData())
    val questionScreenData: questionScreenData?
        get() = _questionScreenData.value

    private val _isNextEnabled = mutableStateOf(false)
    val isNextEnabled: Boolean
        get() = _isNextEnabled.value



    fun onBackPressed(): Boolean{
        if (questionIndex == 0){
            return false
        }
        changeQuestion(questionIndex -1)
        return true
    }

    fun onPreviousPressed() {
        if (questionIndex == 0) {
            throw IllegalStateException("onPreviousPressed when on question 0")
        }
        changeQuestion(questionIndex - 1)
    }

    fun onNextPressed() {
        changeQuestion(questionIndex + 1)
    }

    private fun changeQuestion(newQuestionIndex: Int) {
        questionIndex = newQuestionIndex
        _questionScreenData.value = createQuestionScreenData()
    }

    fun onDonePressed(onSurveyComplete: () -> Unit) {
        // Here is where you could validate that the requirements of the survey are complete
        onSurveyComplete()
    }
    val mainScope = MainScope()

    fun onModeResponse(Mode: Mode) {
            _ModeResponse.value = Mode
/*
        val Role = Role(Role = Mode)
        val call: Call<Any> = RetrofitClient.WebService.addUserRegister(Role)

        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                // 서버 응답을 처리
                if (response.isSuccessful) {
                    // 성공적으로 응답을 받았을 때의 처리
                    Log.d("QuestionViewModel", "성공 메시지: ${response.body()}") // 성공 메시지 로그 출력
                } else {
                    // 응답이 실패한 경우에 대한 처리
                    Log.e("QuestionViewModel", "응답 실패: ") // 실패 메시지 로그 출력
                }
            }
       //     ${t.message}

            override fun onFailure(call: Call<Any>, t: Throwable) {
                // 통신 실패시의 처리
                Log.e("QuestionViewModel", "통신 실패:*") // 실패 메시지 로그 출력
                t.printStackTrace()
            }
        })*/
    }

    fun onGenderResponse(Gender: Gender) {
        _GenderResponse.value = Gender
    }


    fun onCommunicationResponse(Communication: Communication) {
        _CommunicationResponse.value = Communication
    }

    /* introduction 필요 */


    // 선택된 도시를 저장하는 함수
    fun setSelectedCity(city: String) {
        _selectedCity.value = city
    }

    fun setSelectedDisitrict(city: String) {
        _selectedDistrict.value = city
    }


    private fun createQuestionScreenData(): questionScreenData {
        return questionScreenData(
            questionIndex = questionIndex,
            questionCount = questionOrder.size,
            shouldShowPreviousButton = questionIndex > 0,
            shouldShowDoneButton = questionIndex == questionOrder.size - 1,
            Questions = questionOrder[questionIndex],
        )
    }

}

class QuestioniewModelFactory(
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            return QuestionViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class questionScreenData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val Questions: Questions,
)