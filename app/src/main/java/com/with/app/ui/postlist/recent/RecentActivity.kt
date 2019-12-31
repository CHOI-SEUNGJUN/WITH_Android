package com.with.app.ui.postlist.recent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.with.app.R
import kotlinx.android.synthetic.main.activity_recent.*

class RecentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent)

        btn_cancel.setOnClickListener {
            finish()
        }
    }
}
