package com.with.app.data.repository

import com.with.app.data.remote.ResponseSignInData
import com.with.app.data.remote.RequestSignInData
import com.with.app.data.remote.RequestSignUpData
import com.with.app.data.remote.ResponseSignUpData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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
    @POST("/Auth/signup")
    fun postSignUp(
        @Part("userId") userId : RequestBody,
        @Part("password") password : RequestBody,
        @Part("name") name : RequestBody,
        @Part("birth") birth : RequestBody,
        @Part("gender") gender : RequestBody,
        @Part img : MultipartBody.Part?
    ) : Call<ResponseSignUpData>

}