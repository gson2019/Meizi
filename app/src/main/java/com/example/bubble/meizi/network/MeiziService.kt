package com.example.bubble.meizi.network

import com.example.bubble.meizi.model.GankeResponse
import com.example.bubble.meizi.model.PixabayPhotoResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MeiziService{
    @GET("api/")
    fun getPixabayImgs(@Query("key") key : String, @Query("q") query : String, @Query("image_type") photoType: String ) : Deferred<PixabayPhotoResponse>
}

class MeiziNetwork private constructor(){
    private var pixabayService : MeiziService ? = null;
    fun getPixabayService() : MeiziService{
        if(pixabayService == null){
            pixabayService = Retrofit.Builder()
                .client(getLoggingOkClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl("https://pixabay.com")
                .build()
                .create(MeiziService::class.java)
        }
        return pixabayService!!
    }

    private fun getLoggingOkClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClientBuilder.addInterceptor(loggingInterceptor)
        return httpClientBuilder.build()
    }

    companion object{
        val instance: MeiziNetwork by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            MeiziNetwork()
        }
    }
}