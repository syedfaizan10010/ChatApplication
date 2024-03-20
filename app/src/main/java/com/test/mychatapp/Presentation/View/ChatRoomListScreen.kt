package com.test.mychatapp.Presentation.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test.mychatapp.Data.Model.RoomModel
import com.test.mychatapp.Presentation.ViewModel.RoomViewModel


@Composable
fun ChatRoomListScreen(
    roomViewModel: RoomViewModel = viewModel (),
    onJoinClicked: (RoomModel) -> Unit
){
    val rooms by roomViewModel.rooms.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Chat Rooms", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        //Display list of chat rooms available
        LazyColumn {
            //TODO - configuration
            items(rooms){room ->
                RoomItem(room = room, onJoinClicked={onJoinClicked(room)})
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            showDialog = true
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Create Room")
        }

    }

    if (showDialog){
        AlertDialog( onDismissRequest = { showDialog = true },
            title = { Text("Create a new room") },
            text={
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }, confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            if (name.isNotBlank()) {
                                showDialog = false

                            }
                        }
                    ) {
                        Text("Add")
                    }
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            })
    }
}

@Composable
fun RoomItem(room: RoomModel, onJoinClicked: (RoomModel) -> Unit){
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = room.name, fontSize = 16.sp, fontWeight = FontWeight.Normal)
        OutlinedButton(onClick = { onJoinClicked(room) }) {
            Text(text = "Join")
        }

    }
}

//@Preview
//@Composable
//fun ChatRoomListScreenPreview(){
//    ChatRoomListScreen()
//}
@Preview
@Composable
fun RoomItemPreview(){
    val room= RoomModel("1","Syed ")
    RoomItem(room){}
}