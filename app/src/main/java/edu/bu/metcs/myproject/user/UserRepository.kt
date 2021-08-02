package edu.bu.metcs.myproject.user

import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import edu.bu.metcs.myproject.FrainerApplication
import edu.bu.metcs.myproject.SharePreferenceData
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao, application: FrainerApplication) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allUsers: Flow<List<User>> = userDao.getUsers(SharePreferenceData.getSharedPrefString(application, "logged_user", ""))

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
}