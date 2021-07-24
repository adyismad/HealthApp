package edu.bu.metcs.myproject.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
        @PrimaryKey @ColumnInfo(name = "username") val userName: String,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "password") val password: String,
        @ColumnInfo(name = "male") val male: Boolean,
        @ColumnInfo(name = "age") val age: String,
        @ColumnInfo(name = "location") val location: String,
        @ColumnInfo(name = "daytime") val daytime: String,
        @ColumnInfo(name = "partner_pref") val partnerPref: String)