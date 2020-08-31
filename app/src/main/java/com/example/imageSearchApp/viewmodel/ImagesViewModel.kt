package com.example.imageSearchApp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imageSearchApp.model.Images
import com.example.imageSearchApp.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ImageViewModel for Loading Business Logic
 */
class ImagesViewModel : ViewModel() {

    private val apiService = ApiService.create()
    private var imagesData = MutableLiveData<Images>()


    /**
     * Method to load list using API Call
     */
    fun loadImageList(pageCount: Int, query: String) {
        apiService.fetchImagesList(pageCount,query).enqueue(object : Callback<Images?> {
            override fun onFailure(call: Call<Images?>, t: Throwable) {
                imagesData.value = null
            }
            override fun onResponse(call: Call<Images?>, response: Response<Images?>) {
                if(response.body()!=null && response.body()?.data?.size!=0){
                    imagesData.value = response.body()
                }

            }
        })

    }


    /**
     * This method return instance of {@link Images}
     */
    fun getImagesData(): MutableLiveData<Images> {
        return imagesData
    }

}