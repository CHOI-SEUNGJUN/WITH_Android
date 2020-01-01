package com.with.app.manage

import android.content.Context
import com.with.app.data.remote.RequestBoardData
import com.with.app.data.remote.RequestChatOpenData
import com.with.app.data.remote.RequestSignInData
import com.with.app.data.remote.RequestWithInviteData
import com.with.app.data.repository.AuthRepositoryInterface
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class RequestManager(val context: Context, val authManager: AuthManager, val regionManager: RegionManager) {

    private companion object {
        const val BASE_URL = "http://18.222.189.150:3000"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(AuthRepositoryInterface::class.java)

    fun requestSignIn(data: RequestSignInData) = retrofit.postSignIn(data)

    fun requestSignUp(userId: RequestBody, password: RequestBody, name: RequestBody,
                      birth: RequestBody, gender: RequestBody, img: MultipartBody.Part?)
            = retrofit.postSignUp(userId, password, name, birth, gender, img)

    fun requestWithMate() = retrofit.getWithMate(authManager.token)

    fun requestRecommendPlace(regionCode : String) = retrofit.getRecommendPlace(regionCode)

    fun requestLatelyBoard(boardIdx : String) = retrofit.getLatelyBoard(boardIdx)

    fun requestBgImg() = retrofit.getBgImg()

    fun requestCountryList(regionCode: String) = retrofit.getCountryList(regionCode)

    fun requestSearchBoard(regionCode: String, startDate: String, endDate: String,
                           keyword: String, filter: Int)
            = retrofit.getSearchBoard(regionCode, startDate, endDate, keyword, filter, authManager.token)

    fun requestDetailBoard(boardIdx: Int) = retrofit.getDetailBoard(boardIdx)

    fun requestBoardWrite(data: RequestBoardData)
            = retrofit.postBoardWrite(data, authManager.token)

    fun requestBoardEdit(boardIdx: Int, data: RequestBoardData)
            = retrofit.putBoardEdit(boardIdx, data, authManager.token)

    fun requestChatOpen(data: RequestChatOpenData)
            = retrofit.postChatOpen(data, authManager.token)

    fun requestChatList() = retrofit.getChatList(authManager.token)

    fun requestWithInvite(data: RequestWithInviteData)
            = retrofit.putWithInvite(data, authManager.token)

    fun requestMyPage() = retrofit.getMyPage(authManager.token)

    fun requestPutMyPage(intro: RequestBody, userImg: MultipartBody.Part?, userBgImg: MultipartBody.Part?)
            = retrofit.putMyPage(intro, userImg, userBgImg, authManager.token)


}

val requestModule = module {
    single { RequestManager(get(), get(), get()) }
}