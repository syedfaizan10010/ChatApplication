package com.test.mychatapp.Data.Repository

import com.google.firebase.firestore.FirebaseFirestore
import com.test.mychatapp.Data.Model.MessageModel
import com.test.mychatapp.Data.Model.ResultModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MessageRepository(private val firestore: FirebaseFirestore) {
    suspend fun sendMessage(roomId: String, message: MessageModel): ResultModel<Unit> =
    try {
        firestore.collection("rooms").document(roomId)
            .collection("messages").add(message).await()
        ResultModel.Success(Unit)
    } catch (e: Exception) {
        ResultModel.Error(e)
    }

    suspend fun getChatMessages(roomId: String): Flow<List<MessageModel>> =

        callbackFlow {
            val data = firestore.collection("rooms").document(roomId)
                    .collection("messages")
                    .orderBy("timestamp")
                    .addSnapshotListener { querySnapshot, _ ->
                        querySnapshot?.let {
                            trySend(it.documents.map { doc ->
                                doc.toObject(MessageModel::class.java)!!.copy()
                            }).isSuccess
                        }
                    }
            awaitClose{data.remove()}

        }
}

