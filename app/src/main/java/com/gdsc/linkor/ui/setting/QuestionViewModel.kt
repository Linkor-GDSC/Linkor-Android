package com.gdsc.linkor.setting

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gdsc.linkor.setting.question.Communication
import com.gdsc.linkor.setting.question.Gender
import com.gdsc.linkor.setting.question.WeekItem
import com.gdsc.linkor.ui.community.signInViewModel
import com.gdsc.linkor.ui.setting.dataSetting.SettingBuilder
import com.gdsc.linkor.ui.setting.dataSetting.UserInfo
import com.gdsc.linkor.ui.setting.dataSetting.UserInfoTime
import com.gdsc.linkor.ui.setting.dataSetting.UserInfoTimeResponse
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

val photoUri = signInViewModel.getImage()
val Name = signInViewModel.getName()
val Email = signInViewModel.getEmail()
/*val photoUri = ""
val Name = ""
val Email = "email"*/

class QuestionViewModel: ViewModel(){
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

/*    fun modeString(Mode: Mode){
        if (Mode.stringResourceId == st){
            return
        }
    }*/

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
    val selectedWeeks: List<String>
        get() = _selectedWeeks.map { it.title }

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

   /* fun getString(): String? {
        return getApplication<Application>().resources.getString(ModeResponse.)
    }*/

    var ModeString : String = ModeResponse?.stringResourceId.toString()
    var GenderString : String = GenderResponse?.stringResourceId.toString()
    var CommnunicationString : String = CommunicationResponse?.stringResourceId.toString()
    var photouriString : String = photoUri.toString()

    fun onDonePressed(onSurveyComplete: () -> Unit) {
        // Here is where you could validate that the requirements of the survey are complete

        //값들 포스트 하기
        val data = UserInfo(email = "test20@gmail.com", name = Name, role = /*ModeString*/"tutor", gender = GenderString,
        locationsido = selectedCity, locationgu =selectedDistrict , tutoringMethod = CommnunicationString,
        introduction = intro, photourl = photouriString)
        SettingBuilder.SettingService.addUserInfo(data)
            .enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response : Response<Boolean>){
                    if (response.isSuccessful.not()){
                        Log.d("mytag 세부조건", response.toString())
                        return
                    }else{
                        onTimePressed()
                        Log.e("mytag 세부조건", "초기 정보 등록 성공")
                    }
                }
                override fun onFailure(call: Call<Boolean>, t: Throwable){
                    Log.e("mytag 세부조건", "연결 실패")
                    Log.e("mytag 세부조건", t.toString())
                }

            })
        onSurveyComplete()

    }

    fun onTimePressed(){
        var data = UserInfoTime(email = "test20@gmail.com", times = selectedWeeks)
        SettingBuilder.SettingService.addUserTime(data)
            .enqueue(object: Callback<UserInfoTimeResponse>{
                override fun onResponse(call: Call<UserInfoTimeResponse>, response: Response<UserInfoTimeResponse>){
                    if(response.isSuccessful.not()){
                        Log.e(ContentValues.TAG, response.toString())
                        return
                    }else{
                        Log.e(ContentValues.TAG, "초기 시간 정보 성공")
                    }
                }

                override fun onFailure(call: Call<UserInfoTimeResponse>, t: Throwable){
                    Log.e(ContentValues.TAG, "연결 실패")
                    Log.e(ContentValues.TAG, t.toString())
                }
            })
    }

    val mainScope = MainScope()

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