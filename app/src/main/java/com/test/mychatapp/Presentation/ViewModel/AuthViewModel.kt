package com.test.mychatapp.Presentation.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.test.mychatapp.Data.Repository.UserRepository
import com.test.mychatapp.Utils.Injection
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    private val userRepository: UserRepository
    init {
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
    }

    private val _authResult = MutableLiveData<Result<Boolean>>()

    val authResult: LiveData<Result<Boolean>> get() =_authResult
    fun signUp(email: String, password: String, firstName: String, secondName: String){
        viewModelScope.launch {
            _authResult.value = userRepository.signUp(email,password,firstName, secondName)
        }
    }
    fun signIn(email: String,password: String){
        viewModelScope.launch {
            _authResult.value = userRepository.signIn(email,password)
        }
    }
}