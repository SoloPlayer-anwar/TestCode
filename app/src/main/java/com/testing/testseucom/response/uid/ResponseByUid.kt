package com.testing.testseucom.response.uid


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseByUid(

    @Expose
    @SerializedName("code")
    val code: Int,

    @Expose
    @SerializedName("data")
    val `data`: Data,

    @Expose
    @SerializedName("message")
    val message: String,

    @Expose
    @SerializedName("status")
    val status: String
)