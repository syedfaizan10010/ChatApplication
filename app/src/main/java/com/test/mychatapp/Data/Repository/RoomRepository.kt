package com.test.mychatapp.Data.Repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.test.mychatapp.Data.Model.ResultModel
import com.test.mychatapp.Data.Model.RoomModel
import kotlinx.coroutines.tasks.await

class RoomRepository(private val firestore: FirebaseFirestore) {
    //Function to create room in application
    suspend fun createRoom(name: String):ResultModel<Unit> =
    try{
        val room = RoomModel(name=name)
        firestore.collection("rooms").add(room).await()
        ResultModel.Success(Unit)

    }catch (e:Exception){
        ResultModel.Error(e)
    }

    //Function to get existing Rooms in application
    suspend fun getRooms():ResultModel<List<RoomModel>> =
        try{
            var querySnapshot = firestore.collection("rooms").get().await()
            val rooms = querySnapshot.documents.map { documentSnapShot ->
                documentSnapShot.toObject(RoomModel::class.java)!!.copy(id=documentSnapShot.id)
            }
            ResultModel.Success(rooms)

        }catch (e:Exception){
            ResultModel.Error(e)

        }


}