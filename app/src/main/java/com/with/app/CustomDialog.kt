package com.with.app

import android.app.Dialog
import android.content.Context

class CustomDialog:Dialog {
    constructor(context: Context?):super(context!!)

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setContentView(R.layout.date_picker)
    }
}
