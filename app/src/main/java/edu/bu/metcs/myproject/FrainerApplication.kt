package edu.bu.metcs.myproject

import android.app.Application
import edu.bu.metcs.myproject.data.UserRepository
import edu.bu.metcs.myproject.data.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FrainerApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { UserRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { UserRepository(database.wordDao()) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: FrainerApplication
        fun app(): FrainerApplication {
            return instance
        }
    }
}