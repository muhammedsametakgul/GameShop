package com.example.oyunmagazasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.oyunmagazasi.databinding.ActivityMainBinding
import com.example.oyunmagazasi.ui.fragment.AnaSayfaFragment
import com.example.oyunmagazasi.ui.fragment.SepetFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottombar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.anaSayfaFragment -> {
                    navController.navigate(R.id.anaSayfaFragment)
                    true
                }
                R.id.sepetFragment -> {
                    navController.navigate(R.id.sepetFragment)
                    true
                }
                R.id.profilFragment -> {
                    navController.navigate(R.id.profilFragment2)
                    true
                }
                else -> false
            }
        }}
}