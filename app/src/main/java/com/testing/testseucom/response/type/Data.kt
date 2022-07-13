package com.testing.testseucom.response.type


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Data(
    @Expose
    @SerializedName("BD")
    val bD: String,

    @Expose
    @SerializedName("FL")
    val fL: String,

    @Expose
    @SerializedName("PR")
    val pR: String,

    @Expose
    @SerializedName("RO")
    val rO: String
)