package com.with.app.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.with.app.R
import com.with.app.ui.chatlist.ChatListFragment
import com.with.app.ui.home.HomeFragment
import com.with.app.ui.mypage.MyPageFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(p0 : MenuItem) : Boolean{
        when(p0.itemId) {
            R.id.menu_home -> {
                val fragment_home = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment_home).commit()
            }
            R.id.menu_chat -> {
                val fragment_chat = ChatListFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment_chat).commit()
            }
            R.id.menu_myPage -> {
                val fragment_my_page = MyPageFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment_my_page).commit()
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment_home = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment_home).commit()

        val bottomNavigationView = findViewById<View>(R.id.bn_bottom_navi) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }
}
