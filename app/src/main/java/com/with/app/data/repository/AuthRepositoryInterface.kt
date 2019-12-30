package com.with.app.data.repository

import com.with.app.data.remote.ResponseSignInData
import com.with.app.data.remote.RequestSignInData
import com.with.app.data.remote.RequestSignUpData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST

interface AuthRepositoryInterface {

    /**
     * 로그인
     */

    @POST("/auth/signin")
    fun postSignIn(
        @Body data : RequestSignInData
    ) : Call<ResponseSignInData>

    /**
     * 회원가입
     */
    @Multipart
    @POST("/auth/signup")
    fun postSignUp(
        @Body data : RequestSignUpData
    ) : Call<ResponseSignInData>

}