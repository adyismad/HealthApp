package edu.bu.metcs.myproject.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_table")
open class User(
        @PrimaryKey @ColumnInfo(name = "username") val userName: String,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "password") val password: String,
        @ColumnInfo(name = "male") val male: Boolean,
        @ColumnInfo(name = "age") val age: String,
        @ColumnInfo(name = "location") val location: String,
        @ColumnInfo(name = "daytime") val daytime: String,
        @ColumnInfo(name = "partner_pref") val partnerPref: String) : Serializable