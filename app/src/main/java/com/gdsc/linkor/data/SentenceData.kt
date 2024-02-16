package com.gdsc.linkor.data

import com.gdsc.linkor.model.Sentence

class SentenceData(){
    fun loadBasicSentences():List<Sentence>{
        return listOf(
            Sentence(korean = "안녕하세요",english="Hello"),
            Sentence(korean = "반갑습니다",english="Nice to meet you"),
            Sentence(korean = "안녕히 가세요",english="Good bye"),
            Sentence(korean = "실례합니다",english="Excuse me"),
            Sentence(korean = "네",english="Yes"),
            Sentence(korean = "아니요",english="No"),
            Sentence(korean = "취미가 무엇인가요?",english="What are your hobbies?"),
            /*Sentence(korean = "",english=""),
            Sentence(korean = "",english=""),
            Sentence(korean = "",english=""),*/

            )
    }

    fun loadIntermediateSentences():List<Sentence>{
        return listOf(
            Sentence(korean = "커피 좀 드시겠어요?", english = "Would you like to have some coffee?"),
            Sentence(korean = "날씨가 어때요?", english = "How is the weather?"),
            Sentence(korean = "무엇을 할 건가요?", english = "What are you going to do?"),
            Sentence(korean = "이것은 무엇인가요?",english="What is this?"),
            Sentence(korean = "저는 사과를 좋아해요",english="I like apple"),
            Sentence(korean = "영화 보러 가요",english="Let's go to the movies."),
            Sentence(korean = "무슨 과일을 좋아하시나요?",english="What fruit do you like?"),
            /*Sentence(korean = "",english=""),
            Sentence(korean = "",english=""),
            Sentence(korean = "",english=""),*/

        )
    }

    fun loadAdvancedSentences():List<Sentence>{
        return listOf(
            Sentence(korean = "일곱 시에 만나요", english = "Let's meet at seven"),
            Sentence(korean = "오늘은 내 생일이에요",english="Today is my birthday"),
            Sentence(korean = "저는 서울에서 살고 있어요",english="I live in Seoul"),
            Sentence(korean = "얼마인가요?",english="How much is it?"),
            Sentence(korean = "무슨 노래를 좋아하시나요?",english="What songs do you like?"),
            Sentence(korean = "저는 고양이를 좋아해요",english="I like cat"),
        )
    }

}



