package com.example.king_bob_nae.features

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.king_bob_nae.R
import com.example.king_bob_nae.base.BaseActivity
import com.example.king_bob_nae.databinding.ActivityMainBinding
import com.example.king_bob_nae.features.home.HomeFragment
import com.example.king_bob_nae.features.kkilog.KkiLogFragment
import com.example.king_bob_nae.features.recipe.RecipeFragment

class HomeActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val homeFragment by lazy { HomeFragment() }
    private val kkiLogFragment by lazy { KkiLogFragment() }
    private val recipeFragment by lazy { RecipeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.bottomNavHome.run {
            itemIconTintList = null
            setOnItemSelectedListener { menu ->
                when (menu.itemId) {
                    R.id.nav_home -> {
                        changeFragment(homeFragment)
                    }
                    R.id.nav_kki_log -> {
                        changeFragment(kkiLogFragment)
                    }
                    R.id.nav_recipe -> {
                        changeFragment(recipeFragment)
                    }
                }
                true
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}