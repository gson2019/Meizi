package com.example.bubble.meizi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.bubble.meizi.model.Girl
import com.example.bubble.meizi.model.Hit
import com.example.bubble.meizi.model.NetResponse
import com.example.bubble.meizi.network.MeiziNetwork
import com.example.bubble.meizi.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.UnknownHostException

class MeiziViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val content: MutableLiveData<NetResponse<List<Hit>>> by lazy { MutableLiveData<NetResponse<List<Hit>>>() }
    var hitMap = hashMapOf<Int, Boolean>()
    val favHits = dataRepository.favHits
    suspend fun getRemoteMeizis(query: String, type: String) {
        try {
            val meiziRemoteData =
                dataRepository.getNetworkImages("15844840-d5e06026857e3e31a83ef6638", query, type)
            content.postValue(NetResponse(NetResponse.Status.SUCCESS, meiziRemoteData.hits, null))
            meiziRemoteData.hits.forEach {
                hitMap[it.id] = false
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                content.value = NetResponse(NetResponse.Status.ERROR, null, getMessage(e))
            }
            Log.e("MeiziFailure", e.message)
            e.printStackTrace()
        }
    }

    private fun getMessage(e: Exception): String {
        var message = e.localizedMessage ?: "Unknown Error"
        if (e is UnknownHostException) {
            message = "Network error, please check your connectivity"
        }
        return message
    }

    fun saveFavImage(img: Hit) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.saveImage(img)
        }
    }

    fun deleteFavImage(hit: Hit) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.deleteImage(hit)
        }
    }

    fun setHitSavedState(id:Int) {
        hitMap[id] = !hitMap[id]!!
    }

    init {
        viewModelScope.launch {
            getRemoteMeizis("German Shepherd", "photo")
            val localHits =  dataRepository.getLocalFavHits()
            localHits.forEach {
                hit -> hitMap[hit.id] = true
            }
        }
    }

    // custom view model factory, use to build pass variables to view model
    class MeiziViewModelFactory(private val dataRepository: DataRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MeiziViewModel(dataRepository) as T
        }
    }
}