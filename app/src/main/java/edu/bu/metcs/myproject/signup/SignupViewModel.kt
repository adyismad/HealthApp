package edu.bu.metcs.myproject.signup

import android.util.Log
import androidx.lifecycle.*
import edu.bu.metcs.myproject.FrainerApplication
import edu.bu.metcs.myproject.SharePreferenceData
import edu.bu.metcs.myproject.user.LoggedInUser
import edu.bu.metcs.myproject.user.User
import edu.bu.metcs.myproject.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SignupViewModel(private val repository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    fun getUser(userName: String) {
        scope.launch {
            _user.postValue(repository.getUser(userName))
        }
    }
}

class SignupViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignupViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}