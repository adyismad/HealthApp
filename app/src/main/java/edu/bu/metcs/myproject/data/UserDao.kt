package edu.bu.metcs.myproject.data

import androidx.room.*

@Dao
interface UserDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM user_table WHERE username != :userName")
    fun getUsers(userName: String?): List<User>?

    @Query("SELECT * FROM user_table WHERE username = :userName AND password = :password")
    fun getUser(userName: String, password: String): User?

    @Query("SELECT * FROM user_table WHERE username = :userName")
    fun getUser(userName: String): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()
}