package vamsee.application.studytube.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vamsee.application.studytube.Utils.Constants.Companion.BASE_URL

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SimpleApi by lazy{
        retrofit.create(SimpleApi::class.java)
    }
}