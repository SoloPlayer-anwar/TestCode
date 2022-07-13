package com.testing.testseucom.network

import com.testing.testseucom.response.ResponseList
import com.testing.testseucom.response.uid.ResponseByUid
import io.reactivex.Observable
import retrofit2.http.*

interface EndPoint {

    @GET("locations")
    fun getList():Observable<ResponseList>

    @GET("locations/{locID}")
    fun getByUid(@Path(value = "locID") locId:String):Observable<ResponseByUid>

    @FormUrlEncoded
    @POST("locations")
    fun saveList(@Field("locName") locName:String,
                 @Field("locType") locType:String,
                 @Field("projectCode") projectCode:String,
                 @Field("buildingCode") buildingCode:String,
                 @Field("floorCode") floorCode:String):Observable<ResponseByUid>

    @FormUrlEncoded
    @PUT("locations/{locID}")
    fun updateList (@Path(value = "locID") locID:String,
                    @Field("locName") locName:String,
                    @Field("locType") locType:String,
                    @Field("projectCode") projectCode:String,
                    @Field("buildingCode") buildingCode:String,
                    @Field("floorCode") floorCode:String):Observable<ResponseByUid>

}