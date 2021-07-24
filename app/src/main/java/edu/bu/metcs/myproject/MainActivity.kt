package edu.bu.metcs.myproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.nav_graph)
        val navController = navHostFragment.navController

        val destination = if (SharePreferenceData.getObject(this, "logged_user") != null) R.id.myFrainerFragment else R.id.loginFragment
        navGraph.startDestination = destination
        navController.graph = navGraph
    }
}