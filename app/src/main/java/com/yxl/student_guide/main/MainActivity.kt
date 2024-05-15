package com.yxl.student_guide.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yxl.student_guide.R
import com.yxl.student_guide.databinding.ActivityMainBinding
import com.yxl.student_guide.favorites.ui.FavoritesFragment
import com.yxl.student_guide.main.ui.ContentFragment
import com.yxl.student_guide.profile.ui.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, ContentFragment())
                .commitAllowingStateLoss()
        }

        binding.bottomMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemMain -> {
                    replaceFragment(ContentFragment())
                }

                R.id.itemProfile -> {
                    replaceFragment(ProfileFragment())
                }

                R.id.itemFav -> {
                    replaceFragment(FavoritesFragment())
                }
            }
            return@setOnItemSelectedListener true
        }

        binding.bottomMenu.setOnItemReselectedListener {  }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment)
            .commit()
    }

    fun addFragment(fragment: Fragment, bundle: Bundle? = null) {
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }
}