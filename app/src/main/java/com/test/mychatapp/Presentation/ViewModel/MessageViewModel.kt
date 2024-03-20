package com.test.mychatapp.Presentation.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.test.mychatapp.Data.Model.MessageModel
import com.test.mychatapp.Data.Model.UserModel
import com.test.mychatapp.Data.Repository.MessageRepository
import com.test.mychatapp.Data.Repository.UserRepository
import com.test.mychatapp.Utils.Injection
import kotlinx.coroutines.launch
@Suppress("CAST_NEVER_SUCCEEDS")
class MessageViewModel:ViewModel() {
    private  val _messageRepository: MessageRepository
    private val _userRepository: UserRepository
    init {
        _messageRepository= MessageRepository(Injection.instance())
        _userRepository = UserRepository(FirebaseAuth.getInstance(),
            Injection.instance()
        )
        loadCurrentUser()
    }
    private val _message =MutableLiveData<List<MessageModel>>()
    val message: LiveData<List<MessageModel>>get() =_message

    private val _roomId = MutableLiveData<String>()
    private val _currentUser = MutableLiveData<UserModel>()
    val rowId: LiveData<String> get()=_roomId
    val currentUser: LiveData<UserModel> get()= _currentUser

    private fun loadCurrentUser() {
        viewModelScope.launch {
            when (val result = _userRepository.getCurrentUser()) {
                else -> _currentUser.value = result as UserModel?
            }
        }
    }
}