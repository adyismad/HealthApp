package edu.bu.metcs.myproject.myfrainers

import androidx.lifecycle.*
import edu.bu.metcs.myproject.data.User
import edu.bu.metcs.myproject.data.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MyFrainerViewModel(private val repository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)


    fun getUsers(userName: String) {
        scope.launch {
            _users.postValue(repository.getUsers(userName))
        }
    }
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