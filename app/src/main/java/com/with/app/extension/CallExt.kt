package com.with.app.extension

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T>.safeEnqueue(
    onError: (Throwable) -> Unit = onErrorStub,
    onSuccess: (T) -> Unit = {},
    onFailure: (Response<T>) -> Unit = {}
) {
    this.enqueue(
        object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                if ( response.isSuccessful ) {
                    response.body()?.let {
                        onSuccess(it)
                    } ?: onFailure(response)
                } else {
                    onFailure(response)
                }
            }
        }
    )
}

internal val onErrorStub: (Throwable) -> Unit = {
    Log.e("safeEnqueue","network error : $it")
}
