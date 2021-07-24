package edu.bu.metcs.myproject.login.view

import androidx.lifecycle.*
import edu.bu.metcs.myproject.user.User
import edu.bu.metcs.myproject.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    fun getUser(userName: String, password: String) {
        scope.launch {
            _user.postValue(repository.getUser(userName, password))
        }
    }
}

class LoginViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}