package com.with.app.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.with.app.R
import com.with.app.ui.chatlist.ChatListFragment
import com.with.app.ui.home.HomeFragment
import com.with.app.ui.mypage.MyPageFragment
import com.with.app.ui.postlist.PostListFragment
import com.with.app.util.toast

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

    private var lastTimeBackPressed:Long=-1500
    // 이전 버튼 두 번 눌러서 종료하기

    override fun onBackPressed() {
        // (현재 버튼 누른 시간-이전에 버튼 누른 시간) <=1.5초일 때 동작
        if (System.currentTimeMillis() - lastTimeBackPressed <= 1500) {
            finish()
        }
        lastTimeBackPressed = System.currentTimeMillis()
        toast("이전 버튼을 한 번 더 누르면 종료됩니다")
    }
}
