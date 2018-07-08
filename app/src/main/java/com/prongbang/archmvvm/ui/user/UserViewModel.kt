package com.prongbang.archmvvm.ui.user

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.prongbang.archmvvm.repository.UserRepository
import com.prongbang.archmvvm.utils.Resource
import com.prongbang.archmvvm.vo.User
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private var userData: LiveData<Resource<List<User>>>?=null

    fun getUsers(): LiveData<Resource<List<User>>> {
        if (userData == null) {
            userData = userRepository.getUsers()
        }
        return userData!!
    }

    override fun onCleared() {
        super.onCleared()
        userData = null
    }
}