package com.gdsc.linkor.ui.message

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gdsc.linkor.R
import com.gdsc.linkor.model.Message
import com.gdsc.linkor.ui.signin.SignInViewModel
import com.gdsc.linkor.util.DateConverter


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MessageScreen(navController: NavController,
                  otherUserName:String,
                  otherUserEmail:String,
                  otherUserPhotoUrl:String,
                  viewModel:MessageViewModel= hiltViewModel(),
                  signInViewModel: SignInViewModel= hiltViewModel()){

    //Log.d("mytag message","url: ${otherUserPhotoUrl}")
    //Log.d("mytag message","email: ${otherUserEmail}")

    /*var messages by remember { mutableStateOf(listOf(
        Message(content="Hello nice to meet you", senderEmail = "s@naver.com", receiverEmail = "nykim1016@naver.com", regDate = "2024-02-04 9:11"),
        Message(content="My name is Nunsong. I want to learn korean.", senderEmail = "s@naver.com", receiverEmail = "nykim1016@naver.com", regDate = "2024-02-04 9:11"),
        Message(content="Nice to meet you", senderEmail = "nykim1016@naver.com", receiverEmail = "s@naver.com", regDate = "2024-02-04 9:13"),
    ))
    }*/

    val myEmail=signInViewModel.getEmail()

    //메세지 목록
    if (myEmail != null) {
        viewModel.getAllMessage(senderEmail = myEmail, receiverEmail = otherUserEmail)
    }
    val messages = viewModel.messages.value

    val listState = rememberLazyListState()
    //val coroutineScope = rememberCoroutineScope()

    //화면
    Scaffold(
        topBar = { MessageTopAppBar(otherUserName,otherUserPhotoUrl) { navController.navigateUp() } }
    ) {innerPadding->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            color = Color(0xFFD7E3EB)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                ) {
                Spacer(Modifier.height(10.dp))
                //메세지들
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp),
                    state = listState
                ) {
                    items(messages.data.orEmpty()) { message ->
                        Message(isMyMessage = (myEmail==message.senderEmail),message=message)
                    }
                }
                //메세지 입력 창
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color(0xFFFDFDFD)
                ) {
                    MessageInput(viewModel = viewModel,otherUserEmail=otherUserEmail)
                }
            }
        }
    }

}

@Composable
@Preview
fun Preview(){
    //ReceivedMessage()
    //SentMessage()
    //SendButton()
    //MessageTextField()
    //MessageScreen(otherUserName = "Nunsong")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageTopAppBar(otherUserName: String,otherUserPhotoUrl: String,onClick:()->Unit){
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                //뒤로 가기 버튼
                IconButton(onClick = onClick) {
                    Image(painter = painterResource(id = R.drawable.ic_arrow_left), contentDescription = "arrow left",
                    )
                }
                //상대방 프로필
                AsyncImage(
                    model = otherUserPhotoUrl,
                    "tutor profile",
                    modifier = Modifier
                        .size(30.dp, 30.dp)
                        .clip(RoundedCornerShape(50))
                )
                Spacer(modifier = Modifier.width(10.dp))
                //상대방 이름
                Text(otherUserName)
            }
        }
    )
}


@Composable
fun MessageInput(viewModel: MessageViewModel= hiltViewModel(), signInViewModel: SignInViewModel= hiltViewModel(), otherUserEmail: String){
    var inputValue by remember{ mutableStateOf("") }

    fun sendMessage() {
        signInViewModel.getEmail()
            ?.let { viewModel.postMessage(email = it, content = inputValue, receiverEmail = otherUserEmail) }
        inputValue = ""
    }

    Surface(
        modifier=Modifier.padding(horizontal = 10.dp, vertical = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            //메세지 입력 창
            TextField(
                value = inputValue,
                onValueChange = {inputValue=it},
                modifier= Modifier
                    .clip(RoundedCornerShape(10))
                    //.fillMaxWidth()
                    .border(BorderStroke(width = 1.dp, color = Color(0xFFD7E3EB)))
                    .weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF2F2F2),
                    unfocusedContainerColor = Color(0xFFF2F2F2),
                    focusedTextColor = Color(0xFF4F4F4F),
                    unfocusedTextColor = Color(0xFF4F4F4F),
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions { sendMessage() },
            )
            Spacer(modifier = Modifier.width(10.dp))
            //보내기 버튼
            Button(onClick = { sendMessage() },
                colors= ButtonDefaults.buttonColors(containerColor = Color(0xFFFDFDFD),disabledContainerColor=Color(0xFFFDFDFD), contentColor = Color(0xFF4C6ED7),disabledContentColor=Color(0xFF4C6ED7)),
                shape=RoundedCornerShape(10),
                enabled = inputValue.isNotBlank(),
                elevation = ButtonDefaults.buttonElevation(3.dp,3.dp,3.dp,3.dp,3.dp),
                contentPadding = PaddingValues(10.dp),

            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = "send button",
                    modifier=Modifier.size(30.dp)
                )
            }
        }
    }

}




@Composable
fun Message(isMyMessage:Boolean,message: Message){
    val dateConverter = DateConverter()
    Column(
        horizontalAlignment = if (isMyMessage) Alignment.End else Alignment.Start,
        modifier = Modifier
            .fillMaxWidth(),
    )
    {
        Surface(
            modifier = Modifier.widthIn(max = 330.dp),
            shape = RoundedCornerShape(10.dp),
            color = if (isMyMessage) Color(0xFF4C6ED7) else Color(0xFFFDFDFD)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    //.wrapContentWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
            ) {

                //메세지 내용
                Text(
                    text = message.content,
                    color = if (isMyMessage) Color(0xFFFDFDFD) else Color(0xFF181818),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(10.dp))
                //시간
                dateConverter.convertDateToString(message.regDate)?.let {
                    Text(
                        //text = message.regDate,
                        text = it,
                        color = if (isMyMessage) Color(0xFFD0CDCD) else Color(0xFF4F4F4F),
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }
    }
}
