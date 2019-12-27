package com.with.app.repository

import com.with.app.data.remote.ResponseSignInData
import com.with.app.data.remote.RequestSignInData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthRepositoryInterface {

    /**
     * 로그인
     */

    @POST("/auth/signin")
    fun getSignIn(
        @Body data : RequestSignInData, @Header("token") token : String
    ) : Call<ResponseSignInData>

    /**
     * 회원가입
     */

}