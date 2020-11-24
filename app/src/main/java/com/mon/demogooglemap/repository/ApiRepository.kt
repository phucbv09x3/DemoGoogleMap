package com.mon.demogooglemap.repository

import com.mon.demogooglemap.model.DirectionObject
import com.mon.demogooglemap.model.SearchCompleteObject
import retrofit2.http.GET
import retrofit2.http.Query

const val KEY="AIzaSyDFWa-0NcyAk0VaZCRc2v4IcctBcr8g5R4"

interface ApiRepository {
    @GET("/maps/api/directions/json?key=${KEY}")
    fun getDirection(
            @Query(value = "origin") origin:String,
            @Query(value = "destination") destination:String
    ) : retrofit2.Call<DirectionObject>

    @GET("/maps/api/place/autocomplete/json?language=vi&key=${KEY}&radius=500")
    fun searchLocation(
            @Query(value = "input") keySearch:String
    ) : retrofit2.Call<SearchCompleteObject>
}