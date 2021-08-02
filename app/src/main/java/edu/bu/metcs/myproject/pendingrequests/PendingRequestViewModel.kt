package edu.bu.metcs.myproject.pendingrequests

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import edu.bu.metcs.myproject.user.User
import edu.bu.metcs.myproject.user.UserRepository

class PendingRequestViewModel(private val repository: UserRepository) : ViewModel() {

    val users: LiveData<List<User>> = repository.allUsers.asLiveData()

}

class PendingRequestViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PendingRequestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PendingRequestViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}