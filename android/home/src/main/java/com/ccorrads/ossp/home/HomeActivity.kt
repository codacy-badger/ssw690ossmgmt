package com.ccorrads.ossp.home

import android.os.Bundle
import com.ccorrads.ossp.core.BaseActivity
import com.ccorrads.ossp.home.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    var component: HomeComponent? = null

    override fun layoutResId(): Int {
        return R.layout.activity_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = DaggerHomeComponent.builder()
            .context(this)
            .build()

        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_home_frag_container, HomeFragment())
            .commit()

        configureBottomNav()
    }

    private fun configureBottomNav() {
        activity_home_bottom_nav.setOnNavigationItemReselectedListener { /* no-op */ }
        activity_home_bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_home_frag_container, HomeFragment())
                        .commit()
                    true
                }
                R.id.navigation_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_home_frag_container, SettingsFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}