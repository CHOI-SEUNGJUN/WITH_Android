package com.with.app.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.with.app.R
import com.with.app.manage.RequestManager
import com.with.app.data.remote.RequestSignInData
import com.with.app.ui.base.MainActivity
import com.with.app.ui.signup.SignUpActivity
import com.with.app.util.safeEnqueue
import com.with.app.util.toast
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.koin.android.ext.android.inject

class SignInActivity : AppCompatActivity() {

    private val requestManager: RequestManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        btn_login.setOnClickListener {
            requestManager.requestSignIn(
                RequestSignInData(
                    edt_id.text.toString(),
                    edt_pw.text.toString()
                )
            )
                .safeEnqueue(
                    onSuccess = {
                    requestManager.authManager.token = it.data.token
                    requestManager.authManager.idx = it.data.userIdx
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                },
                    onFailure = {
                        toast("로그인에 실패하였습니다.")
                    },
                    onError = {
                        toast("네트워크 통신 오류")
                    }
                )
        }

        btn_signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
