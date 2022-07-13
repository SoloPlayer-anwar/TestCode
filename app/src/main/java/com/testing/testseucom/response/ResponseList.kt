package com.testing.testseucom.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ResponseList(

    @Expose
    @SerializedName("code")
    val code: Int,

    @Expose
    @SerializedName("data")
    val `data`: List<Data>,

    @Expose
    @SerializedName("message")
    val message: String,

    @Expose
    @SerializedName("status")
    val status: String
)