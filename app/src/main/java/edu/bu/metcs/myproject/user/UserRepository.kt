package edu.bu.metcs.myproject.user

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allUsers: Flow<List<User>> = userDao.getUsers()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: User) {
        userDao.insert(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getUser(userName: String, password: String): User? {
        return userDao.getUser(userName, password)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getUser(userName: String): User? {
        return userDao.getUser(userName)
    }
}