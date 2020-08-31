package com.example.imageSearchApp.network

import com.example.imageSearchApp.model.Images
import com.example.imageSearchApp.constants.AppConstants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Api service class for Access Web APIs
 */
interface ApiService {

    @Headers("Authorization: Client-ID 137cda6b5008a7c")
    @GET("3/gallery/search/{pageNo}")
    fun fetchImagesList(@Path("pageNo") pageNo: Int, @Query("q") imageString: String): Call<Images>

    /**
     * Get the instance of ApiService class
     */
    companion object Factory {
        private var gson = GsonBuilder().setLenient().create()

        private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val loggerClient = OkHttpClient.Builder().addInterceptor(logging).build()

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(loggerClient)
                    .baseUrl(AppConstants.BASE_URL)
                    .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}