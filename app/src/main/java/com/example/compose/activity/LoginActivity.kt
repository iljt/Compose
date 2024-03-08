package com.example.compose.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.theme.ComposeTheme
import com.example.compose.theme.Shapes
import com.example.compose.theme.black
import com.example.compose.theme.color_75B4FF
import com.example.compose.theme.color_d8d8d8
import com.example.compose.theme.color_f6f9ff
import com.example.compose.theme.white

class LoginActivity : ComponentActivity() {
    lateinit var activity:Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginPage()
                }
            }
        }
    }


@Composable
fun LoginPage() {
    val context = LocalContext.current
   Column(
          modifier = Modifier
              .fillMaxSize()
              .background(color = white),
         ) {
       LoginText()
       LoginInputBox()
             // LoginButton()
   }
}


@Preview(showBackground = true, backgroundColor = 0x00FFFF)
@Composable
private fun LoginText() {
    Text(
        text = stringResource(id = R.string.login),
        style = TextStyle(fontSize = 20.sp, color = black, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(25.dp,50.dp,0.dp,0.dp))
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0x00FFFF)
@Composable
private fun LoginInputBox() {
    val codeValue = remember { mutableStateOf(TextFieldValue())}
    val pwdValue = remember { mutableStateOf(TextFieldValue())}
    Column() {
        OutlinedTextField(
           value = codeValue.value,
           onValueChange ={
               codeValue.value =it
           },
           placeholder = { Text(text = stringResource(id = R.string.input_code))},
           shape = Shapes.small,
           colors = TextFieldDefaults.outlinedTextFieldColors(
              cursorColor = black,
               //背景颜色
              containerColor = color_f6f9ff,
              focusedBorderColor = color_f6f9ff,
              unfocusedBorderColor = color_f6f9ff
           ),
           textStyle = TextStyle(fontSize = 16.sp, color = black),
           singleLine = true,
           keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
           modifier = Modifier
               // .height(77.dp)
               .fillMaxWidth()
               .padding(start = 30.dp, top = 25.dp, end = 30.dp, bottom = 0.dp)
       )

        OutlinedTextField(
            value = pwdValue.value,
            onValueChange ={
                pwdValue.value =it
            },
            placeholder = { Text(text = stringResource(id = R.string.input_pwd))},
            shape = Shapes.small,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = black,
                //背景颜色
                containerColor = color_f6f9ff,
                focusedBorderColor = color_f6f9ff,
                unfocusedBorderColor = color_f6f9ff
            ),
            textStyle = TextStyle(fontSize = 16.sp, color = black),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, top = 25.dp, end = 30.dp, bottom = 0.dp)
        )

    }
    LoginButton(codeValue,pwdValue)
}


@Composable
private fun LoginButton(
    codeValue: MutableState<TextFieldValue>,
    pwdValue: MutableState<TextFieldValue>
) {
    val context = LocalContext.current
    var mContainerColor by remember { mutableStateOf(color_d8d8d8)}
    var mEnabled by remember { mutableStateOf(false)}
    mContainerColor = if (codeValue.value.annotatedString.isNotEmpty() && pwdValue.value.annotatedString.isNotEmpty()){
        if(pwdValue.value.annotatedString.length < 6){
            mEnabled = false
            color_d8d8d8
        }else{
            mEnabled = true
            color_75B4FF
        }
    }else{
        mEnabled = false
        color_d8d8d8
    }
    Button(
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(width = 0.dp, brush = SolidColor(color_d8d8d8)),
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 60.dp)
            .height(55.dp)
            .fillMaxWidth()
           ,
        colors = ButtonDefaults.buttonColors(containerColor = mContainerColor),
        enabled = mEnabled,
        onClick = {
           // context.startActivity(Intent(context, PwdInitActivity::class.java))
           // context.startActivity(Intent(context, SetNewPwdActivity::class.java))
           // context.startActivity(Intent(context, SettingActivity::class.java))
           // context.startActivity(Intent(context, PersonInfoActivity::class.java))
           // context.startActivity(Intent(context, RemarkActivity::class.java))
           context.startActivity(Intent(context, SearchActivity::class.java))
           // context.startActivity(Intent(context, ExpandActivity::class.java))
            context.startActivity(Intent(context, MyActivity::class.java))
           // Toast.makeText(context, "Login click", Toast.LENGTH_SHORT).show()
        }) {
        Text(
            text = stringResource(id = R.string.login),
            color = white,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreView() {
    ComposeTheme {
        LoginPage()
    }
}

}