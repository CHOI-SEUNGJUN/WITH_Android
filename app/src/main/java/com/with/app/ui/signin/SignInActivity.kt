package com.with.app.ui.signin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.with.app.R
import com.with.app.manage.RequestManager
import com.with.app.data.remote.RequestSignInData
import com.with.app.extension.*
import com.with.app.ui.base.MainActivity
import com.with.app.ui.evaluation.EvaluateActivity
import com.with.app.ui.signup.SignUpActivity

import kotlinx.android.synthetic.main.activity_sign_in.*
import org.koin.android.ext.android.inject

class SignInActivity : AppCompatActivity() {

    private val requestManager: RequestManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btn_login.setBackgroundColor(0)



        tv_signin_intro.text = "안녕하세요!<br><b>로그인</b>을 해주세요".toSpanned()

        edt_signin_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!edt_signin_email.text.toString().isEmpty() && !edt_signin_password.text.toString().isEmpty()) {
                    btn_login.setBackgroundResource(R.drawable.corner_primary_6dp)
                    btn_login.setTextColor(Color.parseColor("#ffffff"))
                }
                else if(edt_signin_email.text.toString().isEmpty()) {
                    btn_login.setBackgroundColor(0)
                    btn_login.setBackgroundColor(Color.parseColor("#000000"))
                    btn_login.setBackgroundResource(R.drawable.corner_very_light_pink_6dp)
                    btn_login.setTextColor(Color.parseColor("#a8a8a8"))

                }
            }
        })


        edt_signin_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!edt_signin_email.text.toString().isEmpty() && !edt_signin_password.text.toString().isEmpty()) {
                    btn_login.setBackgroundResource(R.drawable.corner_primary_6dp)
                    btn_login.setTextColor(Color.parseColor("#ffffff"))
                } else if (edt_signin_password.text.toString().isEmpty()){

                    btn_login.setBackgroundColor(Color.parseColor("#000000"))

                    btn_login.setBackgroundResource(R.drawable.corner_very_light_pink_6dp)
                    btn_login.setTextColor(Color.parseColor("#a8a8a8"))

                }
            }
        })


        btn_login.setOnClickListener {
            if(edt_signin_email.text.toString().isEmpty() || edt_signin_password.text.toString().isEmpty()) {
                return@setOnClickListener
            }
            showLoading(loading)
            requestManager.requestSignIn(RequestSignInData(edt_signin_email.text.toString(), edt_signin_password.text.toString()))
                .safeEnqueue(
                    onSuccess = {
                        if (it.success && it.message == "로그인 성공") {
                            requestManager.authManager.token = it.data.token
                            Log.e("token", it.data.token.toString())
                            requestManager.authManager.idx = it.data.userIdx
                            requestManager.authManager.name = it.data.name
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            hideLoading(loading)
                            toast("로그인에 실패하였습니다.")
                        }
                    },
                    onFailure = {
                        hideLoading(loading)
                        toast("로그인에 실패하였습니다.")
                    },
                    onError = {
                        loading.pauseAnimation()
                        hideLoading(loading)
                        toast("네트워크 통신 오류")
                    }
                )
        }



        btn_signin_signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}