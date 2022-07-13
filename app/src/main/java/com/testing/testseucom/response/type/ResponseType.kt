package com.testing.testseucom.response.type


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseType(

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