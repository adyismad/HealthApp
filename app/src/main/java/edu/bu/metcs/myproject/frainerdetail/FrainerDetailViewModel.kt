package edu.bu.metcs.myproject.frainerdetail

import androidx.lifecycle.*
import edu.bu.metcs.myproject.data.User
import edu.bu.metcs.myproject.data.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MyProfileViewModel(private val repository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    fun getUser(userName: String) {
        scope.launch {
            _user.postValue(repository.getUser(userName))
        }
    }
}

class MyProfileViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}