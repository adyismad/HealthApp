package edu.bu.metcs.myproject.myfrainers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import edu.bu.metcs.myproject.user.User
import edu.bu.metcs.myproject.user.UserRepository

class MyFrainerViewModel(private val repository: UserRepository) : ViewModel() {

    val users: LiveData<List<User>> = repository.allUsers.asLiveData()
}

class MyFrainerViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyFrainerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyFrainerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}