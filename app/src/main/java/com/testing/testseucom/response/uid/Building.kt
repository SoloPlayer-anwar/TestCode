package com.testing.testseucom.response.uid



import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Building(
    @Expose
    @SerializedName("locActive")
    val locActive: String,

    @Expose
    @SerializedName("locCode")
    val locCode: String,

    @Expose
    @SerializedName("locCreatedAt")
    val locCreatedAt: String,

    @Expose
    @SerializedName("locDispensation")
    val locDispensation: Int,


    @Expose
    @SerializedName("locID")
    val locID: String,


    @Expose
    @SerializedName("locLatitude")
    val locLatitude: Int,


    @Expose
    @SerializedName("locLongitude")
    val locLongitude: Int,

    @Expose
    @SerializedName("locName")
    val locName: String,

    @Expose
    @SerializedName("locType")
    val locType: String,

    @Expose
    @SerializedName("locUpdatedAt")
    val locUpdatedAt: String,

    @Expose
    @SerializedName("locUpdatedUsr")
    val locUpdatedUsr: String
)