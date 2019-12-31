package com.with.app.data.remote

data class ResponseRecommendPlaceData(
    val success : Boolean,
    val message : String,
    val data : List<ResponseRecommendPlaceArrayData>
)

data class ResponseRecommendPlaceArrayData(
    val regionNameEng : String,
    val regionImg : String
)