package com.gdsc.linkor.navigation

import com.gdsc.linkor.R
import com.gdsc.linkor.navigation.Route.COMMUNITY
import com.gdsc.linkor.navigation.Route.COMMUNITYLIST
import com.gdsc.linkor.navigation.Route.LEARNING
import com.gdsc.linkor.navigation.Route.MYPAGE
import com.gdsc.linkor.navigation.Route.TUTORLIST


sealed class BottomNavItem(
    val title: Int, val icon: Int, val screenRoute: String
)
{
    object TutorList : BottomNavItem(R.string.text_tutor_list, R.drawable.ic_tutor_list, TUTORLIST)
    object Learning : BottomNavItem(R.string.text_learning, R.drawable.ic_learning, LEARNING)
    object Community : BottomNavItem(R.string.text_community, R.drawable.ic_community, COMMUNITYLIST)
    object MyPage : BottomNavItem(R.string.text_mypage, R.drawable.ic_mypage, MYPAGE)
}