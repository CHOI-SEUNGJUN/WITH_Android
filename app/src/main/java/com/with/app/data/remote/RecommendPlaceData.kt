package com.with.app.data.remote

data class ResponseRecommendPlaceData(
    val success : Boolean,
    val message : String,
    val data : List<ResponseRecommendPlaceArrayData>
)

data class ResponseRecommendPlaceArrayData(
    val regionCode : String,
    val regionName : String,
    val regionNameEng : String,
    val count : Int,
    val regionImgS : String
)