package edu.bu.metcs.myproject

import android.app.Application
import edu.bu.metcs.myproject.user.UserRepository
import edu.bu.metcs.myproject.user.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FrainerApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { UserRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { UserRepository(database.wordDao(), this) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getUsername(): String? {
        return SharePreferenceData.getSharedPrefString(applicationContext, "logged_user", "")
    }

    companion object {
        private lateinit var instance: FrainerApplication
        fun app(): FrainerApplication {
            return instance
        }
    }
}