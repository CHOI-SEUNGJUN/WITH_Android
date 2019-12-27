package com.with.app.auth

import android.content.Context
import com.with.app.data.remote.RequestSignInData
import com.with.app.repository.AuthRepositoryInterface
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class RequestManager(val context: Context, val authManager: AuthManager) {

    private companion object {
        const val BASE_URL = "http://18.222.189.150:3000"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(AuthRepositoryInterface::class.java)

    fun requestSignIn(data: RequestSignInData) = retrofit.getSignIn(data, authManager.token)


}

val requestModule = module {
    single { RequestManager(get(),get()) }
}