package com.example.bubble.meizi.repository

import com.example.bubble.meizi.database.FavImageDao
import com.example.bubble.meizi.model.Hit
import com.example.bubble.meizi.model.PixabayPhotoResponse
import com.example.bubble.meizi.network.MeiziService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository constructor(private val meiziApi:MeiziService,  private val favImgDao: FavImageDao) {
    val favHits = favImgDao.getFavHits()

    suspend fun getNetworkImages(key: String, query: String, type: String) : PixabayPhotoResponse {
        var pixabayPhotoResponse:PixabayPhotoResponse? = null
        withContext(Dispatchers.IO) {
            val pixabayRes = meiziApi.getPixabayImgs(key, query, type).await()
            pixabayPhotoResponse = pixabayRes
        }
        return pixabayPhotoResponse!!
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
         var localHits:List<Hit>? = null
         withContext(Dispatchers.IO) {
             val res = favImgDao.getAllFavHits()
             localHits = res
         }
        return localHits!!
    }
}