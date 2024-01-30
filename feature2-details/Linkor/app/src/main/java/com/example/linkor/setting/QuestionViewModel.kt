package com.example.linkor.setting

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.linkor.setting.question.Communication
import com.example.linkor.setting.question.Gender

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

    data class WeekItem(
        val title: String,
        val checkedStatus: MutableState<Boolean>
    )

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

    fun onModeResponse(Mode: Mode) {
        _ModeResponse.value = Mode
    }

    fun onGenderResponse(Gender: Gender) {
        _GenderResponse.value = Gender
    }


    fun onCommunicationResponse(Communication: Communication) {
        _CommunicationResponse.value = Communication
    }

    /* introduction 필요 */


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