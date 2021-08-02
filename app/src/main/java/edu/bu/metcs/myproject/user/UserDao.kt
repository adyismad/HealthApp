package edu.bu.metcs.myproject.user

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM user_table WHERE username != :userName")
    fun getUsers(userName: String?): Flow<List<User>>

    @Query("SELECT * FROM user_table WHERE username = :userName AND password = :password")
    fun getUser(userName: String, password: String): User?

    @Query("SELECT * FROM user_table WHERE username = :userName")
    fun getUser(userName: String): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()
}