package com.with.app.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.with.app.R
import com.with.app.ui.signin.SignInActivity
import com.with.app.ui.signup.SignUpActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        try {
            Thread.sleep(3000)
            startActivity(
                Intent(this, SignInActivity::class.java))
            finish()
        } catch (e: Exception) {
            return
        }
    }
}
