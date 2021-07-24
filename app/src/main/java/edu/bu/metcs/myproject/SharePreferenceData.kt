package edu.bu.metcs.myproject

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import edu.bu.metcs.myproject.user.LoggedInUser
import kotlin.reflect.KClass

object SharePreferenceData {

    /**
     * This method set String value in shared preference.
     *
     * @param context - Current context
     * @param key     - String representation the key
     * @param value   -String representing the default value
     */
    fun setSharedPrefString(context: Context?, key: String, value: String) {
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = settings.edit()
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * Returns String value as per preference key passed.
     *
     * @param context      - Current context
     * @param key          - String representation the key
     * @param defaultValue -String representing the default value
     * @return String
     */
    fun getSharedPrefString(context: Context?, key: String, defaultValue: String): String? {
        val token = PreferenceManager.getDefaultSharedPreferences(context)
        return token.getString(key, defaultValue)
    }

    fun setBooleanPreference(context: Context?, key: String, value: Boolean) {
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = settings.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun saveObject(context: Context?, key: String, any: Any) {
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = settings.edit()
        val json = Gson().toJson(any);
        editor.putString(key, json);
        editor.apply()
    }

    fun getObject(context: Context?, key: String): String? {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null)
    }

    fun getBooleanPreference(context: Context, key: String, defaultValue: Boolean): Boolean {
        val token = PreferenceManager.getDefaultSharedPreferences(context)
        return token.getBoolean(key, defaultValue)
    }

    fun clearAllPreference(context: Context?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

}
