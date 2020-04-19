package com.example.bubble.meizi.repository

import android.app.Application
import com.example.bubble.meizi.database.FavImageDao
import com.example.bubble.meizi.database.FavImgDatabase
import com.example.bubble.meizi.model.Hit
import com.example.bubble.meizi.model.PixabayPhotoResponse
import com.example.bubble.meizi.network.MeiziNetwork
import com.example.bubble.meizi.network.MeiziService

class DataRepository constructor(private val meiziApi:MeiziService,  private val favImgDao: FavImageDao) {
    suspend fun getNetworkImages(key: String, query: String, type: String) : PixabayPhotoResponse {
        return meiziApi.getPixabayImgs(key, query, type)
    }

    // insert one image to database
    suspend fun saveImage(img: Hit): String?{
        return try {
            favImgDao.insert(img)
            null
        } catch (e : Exception) {
            e.toString()
        }
    }

    suspend fun deleteImage(hit: Hit) : String? {
        return try {
            favImgDao.deleteHit(hit)
            null
        } catch (e: Exception) {
            e.toString()
        }
    }

    suspend fun getLocalFavHits(): List<Hit> {
        return favImgDao.getFavHits()
    }
}