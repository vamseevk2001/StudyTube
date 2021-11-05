package vamsee.application.studytube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottomnav)
        val bottNavView=findViewById<BottomNavigationView>(R.id.bottnav)
        val navController =findNavController(R.id.fragment)
        bottNavView.setupWithNavController(navController)
    }
}