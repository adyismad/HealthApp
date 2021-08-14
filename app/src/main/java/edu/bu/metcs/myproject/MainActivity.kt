package edu.bu.metcs.myproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.bu.metcs.myproject.data.SharePreferenceData

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.nav_graph)
        val navController = navHostFragment.navController

        val loggedInUser = SharePreferenceData.getSharedPrefString(this, FrainerUtils.LOGGED_USER, "")
        val destination = if (loggedInUser?.isNotEmpty() == true) R.id.myFrainerFragment else R.id.loginFragment
        navGraph.startDestination = destination
        navController.graph = navGraph

        bottomNavigationView = findViewById(R.id.bottom_navigatin_view)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    fun setBottomNavigationVisibility(visibility: Int) {
        // get the reference of the bottomNavigationView and set the visibility.
        bottomNavigationView.visibility = visibility
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "10001"
        const val default_notification_channel_id = "default"

    }
}