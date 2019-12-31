package com.with.app.ui.signup

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.bumptech.glide.Glide
import com.with.app.R
import com.with.app.manage.RequestManager
import com.with.app.util.safeEnqueue
import com.with.app.util.toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.dialog_sign_up.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class SignUpActivity : AppCompatActivity() {

    private val requestManager: RequestManager by inject()

    private val OPEN_GALLERY = 100
    var data: Uri? = null
    var imageUri: Uri? = null
    private var profileImg: MultipartBody.Part? = null

    private var validate = false
    private var b_id = false
    private var b_pw = false
    private var b_pwck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signup() // 회원가입 유효성 조건문 & 서버 요청
        validate() // 아이디 중복체크
        textWatcher() // TextWatcher : ID, PW, PW_CK


        img_signup_profile.setOnClickListener {
            changeImage()
        }

        img_gallery.setOnClickListener {
            changeImage()
        }

        btn_close.setOnClickListener {
            showSettingPopup()
        }

    }

    private fun validate() {
        edt_signup_password.setOnClickListener {
            var userID = edt_signup_email.text.toString()

            // 빈칸 체크
            if (userID.isEmpty()) {
                tv_email_warning.setText("아이디를 입력해주세요")
                tv_email_warning.visibility = View.VISIBLE
                return@setOnClickListener
            }
            // 중복 체크
        }
    }

    private fun textWatcher() {

        // ID WATCHER
        edt_signup_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ( edt_signup_email.text.toString().contains(Regex("^[ㄱ-ㅎㅏ-ㅣ가-힣\\s]"))) {
                    tv_email_warning.setText("이메일 형식이 올바르지 않습니다")
                    tv_email_warning.visibility = View.VISIBLE
                    b_id = false
                }
                else {
                    b_id = true
                    tv_email_warning.visibility = View.INVISIBLE
                }

            }
        })

        edt_signup_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //최소 6글자 / 영문, 숫자 혼합
                if (edt_signup_password.text.toString().contains(Regex("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{6,100}\$"))) {
                    tv_password_warning.visibility = View.INVISIBLE
                    b_pw = true
                } else {
                    tv_password_warning.visibility = View.VISIBLE
                    b_pw = false
                }

                // Confirm 재확인 (중간에 바꿀 경우 대비)
                if (!edt_pw_check.text.toString().isEmpty()) {
                    if (edt_pw_check.text.toString().contentEquals(edt_signup_password.text.toString())) {
                        tv_pw_check_warning.visibility = View.INVISIBLE
                        b_pwck = true
                    } else {
                        tv_pw_check_warning.visibility = View.VISIBLE
                        b_pwck = false
                    }
                }
            }
        })

        // Password Confirm WATCHER
        edt_pw_check.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!edt_pw_check.text.toString().isEmpty()) {
                    if (edt_pw_check.text.toString().contentEquals(edt_signup_password.text.toString())) {
                        tv_pw_check_warning.visibility = View.INVISIBLE
                        b_pwck = true
                    } else {
                        tv_pw_check_warning.visibility = View.VISIBLE
                        b_pwck = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        edt_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!edt_name.text.toString().isEmpty()) {
                    tv_name_warning.visibility = View.INVISIBLE
                } else {
                    tv_name_warning.visibility = View.VISIBLE
                }
            }
        })

        edt_birth.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!edt_birth.text.toString().isEmpty()) {
                    tv_birth_warning.visibility = View.INVISIBLE
                } else {
                    tv_birth_warning.visibility = View.VISIBLE
                }
            }
        })

        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                R.id.rbtn_male ->
                    tv_gender_warning.visibility = View.INVISIBLE
                R.id.rbtn_female ->
                    tv_gender_warning.visibility = View.INVISIBLE
                else ->
                    tv_gender_warning.visibility = View.VISIBLE
            }
        }

    }

    // 회원가입 유효성 조건문 & 서버 요청
    private fun signup() {
        btn_signup.setOnClickListener {
            // 아이디 중복체크
            if (!validate) {
                tv_email_warning.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val userID = edt_signup_email.text.toString()
            val password = edt_signup_password.text.toString()
            val name = edt_birth.text.toString()
            if (rbtn_male.isChecked) {
                val gender = 1
            } else {
                val gender = -1
            }

            if (userID.isEmpty() || password.isEmpty() || name.isEmpty() || !b_id || !b_pw || !b_pwck) {
                toast("회원가입 조건에 맞게 모두 채워주세요!")
                return@setOnClickListener
            }

            requestManager.requestSignUp(
                RequestBody.create(MediaType.parse("text.plain"), "1"),
                RequestBody.create(MediaType.parse("text.plain"), "1"),
                RequestBody.create(MediaType.parse("text.plain"), "안드로이드테스트계정임지우지마셈"),
                RequestBody.create(MediaType.parse("text.plain"), "1995-01-01"),
                RequestBody.create(MediaType.parse("text.plain"), "-1"),
                profileImg
            ).safeEnqueue(
                onSuccess = {
                    finish()
                },
                onError = {
                    toast("네트워크 연결 오류")
                    Log.e("error", it.toString())
                },
                onFailure = {
                    toast("회원가입 실패")
                    Log.e("failure", it.message())
                }
            )
        }
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    data?.data.let {
                        this.data = it
                        imageUri = data!!.data
                        Glide.with(this)
                            .load(data.data)
                            .centerCrop()
                            .into(img_signup_profile)
                        val options = BitmapFactory.Options()

                        var input: InputStream? = null
                        try {
                            input = contentResolver.openInputStream(imageUri!!)
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }

                        val bitmap = BitmapFactory.decodeStream(input, null, options)
                        val baos = ByteArrayOutputStream()
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                        val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                        val img = File(getRealPathFromURI(applicationContext, imageUri!!))

                        profileImg = MultipartBody.Part.createFormData("img", img.name, photoBody)
                        Log.v("Signup Activity", "$profileImg")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    override fun onBackPressed() {
        // TODO : alert Dialog 띄우기
    }

    fun changeImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, OPEN_GALLERY)
    }

    private fun showSettingPopup() {
////        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
////        val view = inflater.inflate(R.layout.activity_sign_up, null)
//        val alertDialog = AlertDialog.Builder(this)
//            .setTitle("회원가입을 종료하시겠습니까?")
//            .setMessage("지금까지 입력한 정보들은 저장되지 않습니다.")
//            .setPositiveButton("예") {
//                dialog, which -> finish()
//            }
//            .setNeutralButton("취소") {
//                dialog, which ->  dialog.cancel()
//            }
//            .create()
//        //alertDialog.setView(view)
//        alertDialog.show()
//        MaterialDialog(this).show {
//            title(text = "회원가입을 종료하시겠습니까?")
//            message(text = "지금까지 입력한 정보들은 저장되지\n않습니다.")
//            positiveButton(text = "아니오") {dialog -> dismiss()}
//            negativeButton (text = "예") {dialog -> finish()}
//        }
//       val materialDialog = MaterialDialog(this).show {
//            customView(R.layout.dialog_sign_up)
//        }
//        val customView = materialDialog.getCustomView()
//
//        btn_ok.setOnClickListener {
//            finish()
//        }
//        btn_cancle.setOnClickListener {
//            materialDialog.dismiss()
//        }
        MaterialDialog(this).show {
            customView(R.layout.dialog_sign_up)
            btn_ok.setOnClickListener {
                finish()
            }
            btn_cancle.setOnClickListener {
                dismiss()
            }
        }
    }

}
