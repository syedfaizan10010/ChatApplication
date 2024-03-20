package com.test.mychatapp.Presentation.View

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mychatApp.R
import com.test.mychatapp.Data.Model.MessageModel
import com.test.mychatapp.Utils.formatTimeStamp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatMessageItem(message:MessageModel){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
           horizontalAlignment =if(message.isSentByCurrentUser) Alignment.End else Alignment.Start){
        Box(modifier = Modifier
            .background(
                if (message.isSentByCurrentUser) colorResource(id = R.color.purple_700) else Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp) ){
            Text(message.text,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(message.senderFirstName,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
            )
            Text(
                text = formatTimeStamp(message.timestamp), // Replace with actual timestamp logic
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            )

        }
    }
}