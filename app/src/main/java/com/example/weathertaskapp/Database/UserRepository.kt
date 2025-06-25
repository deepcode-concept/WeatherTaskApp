package com.example.weathertaskapp.Database

import com.example.weathertaskapp.Database.UserDao
import com.example.weathertaskapp.Database.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) = userDao.insertUser(user)

    suspend fun getAllUsers(): List<User> = userDao.getAllUsers()

    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
}