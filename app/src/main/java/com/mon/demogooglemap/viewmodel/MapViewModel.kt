package com.mon.demogooglemap.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mon.demogooglemap.model.DirectionObject
import com.mon.demogooglemap.model.SearchCompleteObject
import com.mon.demogooglemap.repository.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapViewModel:ViewModel() {
    var direction=MutableLiveData<DirectionObject>()
    var searchLocation=MutableLiveData<SearchCompleteObject>()
    private val apiRepository:ApiRepository=Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRepository::class.java)
    fun getDirection(origin:String,destination:String){
        val call=apiRepository.getDirection(origin,destination)
        call.enqueue(object :Callback<DirectionObject>{
            override fun onResponse(call: Call<DirectionObject>, response: Response<DirectionObject>) {
                direction.value=response.body()
            }

            override fun onFailure(call: Call<DirectionObject>, t: Throwable) {

            }

        })
    }
    fun searchLocation(keySearch:String){
        val call=apiRepository.searchLocation(keySearch)
        call.enqueue(object :Callback<SearchCompleteObject>{
            override fun onResponse(call: Call<SearchCompleteObject>, response: Response<SearchCompleteObject>) {
                searchLocation.value=response.body()
            }

            override fun onFailure(call: Call<SearchCompleteObject>, t: Throwable) {

            }

        })
    }
}