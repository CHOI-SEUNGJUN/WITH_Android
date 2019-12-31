package com.with.app.data.remote

data class ResponseCountryListData(
    val success : Boolean,
    val message : String,
    val data : List<ResponseCountryListArrayData>
)

data class ResponseCountryListArrayData(
    val regionCode : String,
    val regionName : String,
    val regionImgS : String
)