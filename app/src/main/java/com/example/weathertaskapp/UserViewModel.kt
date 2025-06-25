package com.example.weathertaskapp


import android.app.Application
import androidx.lifecycle.*
import com.example.weathertaskapp.Database.AppDatabase
import com.example.weathertaskapp.Database.User
import com.example.weathertaskapp.Database.UserRepository

import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> = _userList

    init {
        val userDao = AppDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
        fetchUsers()
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
            fetchUsers()
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.deleteUser(user)
            fetchUsers()
        }
    }

    fun fetchUsers() {
        viewModelScope.launch {
            val users = repository.getAllUsers()
            _userList.postValue(users)
        }
    }
}