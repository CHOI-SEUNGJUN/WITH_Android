package com.with.app.ui.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.with.app.R
import com.with.app.manage.RequestManager
import com.with.app.data.remote.RequestSignInData
import com.with.app.util.safeEnqueue
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
                .safeEnqueue(onSuccess = {
                })
        }
    }
}
