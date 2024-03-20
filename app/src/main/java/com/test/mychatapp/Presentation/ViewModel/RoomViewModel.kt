package com.test.mychatapp.Presentation.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.mychatapp.Data.Model.ResultModel
import com.test.mychatapp.Data.Model.RoomModel
import com.test.mychatapp.Data.Repository.RoomRepository
import com.test.mychatapp.Utils.Injection
import kotlinx.coroutines.launch

class RoomViewModel:ViewModel() {
    private val _rooms = MutableLiveData<List<RoomModel>>()
    val rooms:LiveData<List<RoomModel>> get() =_rooms
    private val _roomRepository: RoomRepository
    init {
        _roomRepository = RoomRepository(Injection.instance())
        loadRooms()
    }
    fun createRoom(name: String){
        viewModelScope.launch {
            _roomRepository.createRoom(name)
        }
    }
    fun loadRooms(){
        viewModelScope.launch {
            val result = _roomRepository.getRooms()
            when(result){
                is ResultModel.Success -> _rooms.value = result.data
                is ResultModel.Error ->{

                }
            }
        }
    }
}