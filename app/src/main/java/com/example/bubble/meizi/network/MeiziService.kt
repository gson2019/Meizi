package com.example.bubble.meizi.network

import com.example.bubble.meizi.model.GankeResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MeiziService{
    @GET("data/category/{category}/type/{type}/page/{page}/count/{count}")
    suspend fun getMeiziInfos(@Path("category") category: String, @Path("type") type: String, @Path("page") page: Int, @Path("count") count:Int):GankeResponse
}

class MeiziNetwork private constructor(){
    private var meiziService: MeiziService? = null;
    fun getMeiziClient(): MeiziService{
        if(meiziService == null){
            meiziService = Retrofit.Builder()
                .client(getLoggingOkClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://gank.io/api/v2/")
                .build()
                .create(MeiziService::class.java)
        }
        return meiziService!!
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