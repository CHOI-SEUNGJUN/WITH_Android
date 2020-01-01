package com.with.app.data.repository


import com.with.app.data.remote.*
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

    /**
     * HOME - 동행 친구 조회
     */
    @GET("/home/mates")
    fun getWithMate(
        @Header("token") token : String
    ) : Call<ResponseWithMateData>

    /**
     * HOME - 추천 여행지 조회
     */
    @GET("/home/recommendations/{regionCode}")
    fun getRecommendPlace(
        @Path("regionCode") regionCode : String
    ) : Call<ResponseRecommendPlaceData>

    /**
     * HOME - 최근 본 게시물
     */
    @GET("/home/boards/{boardIdx}")
    fun getLatelyBoard(
        @Path("boardIdx") boardIdx : String
    ) : Call<ResponseLatelyBoardData>

    /**
     * HOME - 홈 배경 이미지 조회
     */
    @GET("/home/bgImg")
    fun getBgImg() : Call<ResponseHomeBgData>

    /**
     * 국가 리스트 출력
     */
    @GET("/home/regions/{regionCode}")
    fun getCountryList(
        @Path("regionCode") regionCode : String
    ) : Call<ResponseCountryListData>

    /**
     * 게시글 전체 조회
     */
    @GET("/board/region/{regionCode}/startDates/{startDate}/endDates/{endDate}/keywords/{keyword}/filters/{filter}")
    fun getSearchBoard(
        @Path("regionCode") regionCode : String,
        @Path("startDate") startDate : String,
        @Path("endDate") endDate : String,
        @Path("keyword") keyword : String,
        @Path("filter") filter : Int,
        @Header("token") token: String
    ) : Call<ResponseSearchBoardData>

    /**
     * 게시글 상세 조회
     */
    @GET("/board/{boardIdx}")
    fun getDetailBoard(
        @Path("boardIdx") boardIdx: Int
    ) : Call<ResponseDetailBoardData>

    /**
     * 게시글 작성
     */
    @POST("/board")
    fun postBoardWrite(
        @Body data : RequestBoardData, @Header("token") token: String
    ) : Call<ResponseBoardData>

    /**
     * 게시글 수정
     */
    @PUT("/board/edit/{boardIdx}")
    fun putBoardEdit(
        @Path("boardIdx") boardIdx: Int, @Body data : RequestBoardData, @Header("token") token: String
    ) : Call<ResponseBoardEditData>

    /**
     * 채팅방 개설
     */
    @POST("/chat")
    fun postChatOpen(
        @Body data : RequestChatOpenData, @Header("token") token : String
    ) : Call<ResponseChatOpenData>

    /**
     * 채팅목록 조회
     */
    @GET("/chat")
    fun getChatList(
        @Header("token") token: String
    ) : Call<ResponseChatListData>

    /**
     * 채팅 - 동행신청
     */
    @PUT("/chat")
    fun putWithInvite(
        @Body data : RequestWithInviteData, @Header("token") token : String
    ) : Call<ResponseWithInviteData>

    /**
     * 마이페이지 조회
     */
    @GET("/mypage")
    fun getMyPage(
        @Header("token") token: String
    ) : Call<ResponseMyPageData>

}