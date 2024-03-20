package com.test.mychatapp.Data.Repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.test.mychatapp.Data.Model.UserModel
import kotlinx.coroutines.tasks.await

class UserRepository(private val auth: FirebaseAuth,
                     private val firestore: FirebaseFirestore) {

    suspend fun signUp(
        email: String,
        password: String,
        firstName: String,
        secondName: String
    ): Result<Boolean> =
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val userData = UserModel(firstName, secondName ,email)
            saveUserToFireStore(userData)
            //add user to firestore
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun saveUserToFireStore(user: UserModel){
        firestore.collection("users").document(user.email).set(user).await()
    }

    suspend fun signIn(email: String, password: String):Result<Boolean> =
    try{
        auth.signInWithEmailAndPassword(email,password).await()
        Result.success(true)
    }catch (e:Exception){
        Result.failure(e)
    }

    suspend fun getCurrentUser(): Result<UserModel> = try {
        val uid = auth.currentUser?.email
        if (uid != null) {
            val userDocument = firestore.collection("users").document(uid).get().await()
            val user = userDocument.toObject(UserModel::class.java)
            if (user != null) {
                Log.d("user2","$uid")
                Result.success(user)
            } else {
                Result.failure(Exception("User data not found"))
            }
        } else {
            Result.failure(Exception("User not authenticated"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

}