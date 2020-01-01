package com.with.app.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
                    edt_signin_email.text.toString(),
                    edt_signin_password.text.toString()
                )
            )
                .safeEnqueue(
                    onSuccess = {
                    requestManager.authManager.token = it.data.token
                        Log.e("token", it.data.token.toString())
                    requestManager.authManager.idx = it.data.userIdx
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                },
                    onFailure = {
                        //TODO : 아이디 오류인지 비밀번호 오류인지 구분 => 경고메세지 띄워주기
                        toast("로그인에 실패하였습니다.")
                    },
                    onError = {
                        toast("네트워크 통신 오류")
                    }
                )
        }

        btn_signin_signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
