package com.with.app.repository

import com.with.app.data.remote.ResponseLoginData
import com.with.app.data.remote.RequestLoginData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthRepositoryInterface {

    /**
     * 로그인
     */

    @POST("/auth/signin")
    fun requestLogin(
        @Body data : RequestLoginData, @Header("token") token : String
    ) : Call<ResponseLoginData>

}