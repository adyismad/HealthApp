package edu.bu.metcs.myproject.data

import androidx.annotation.WorkerThread

class UserRepository(private val userDao: UserDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(word: User) {
        userDao.insert(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(user: User) {
        userDao.update(user)
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

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getUsers(username: String): List<User>? {
        return userDao.getUsers(username)
    }
}