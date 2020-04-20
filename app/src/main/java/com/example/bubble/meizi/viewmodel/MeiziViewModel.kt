package com.example.bubble.meizi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.bubble.meizi.model.Girl
import com.example.bubble.meizi.model.Hit
import com.example.bubble.meizi.network.MeiziNetwork
import com.example.bubble.meizi.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MeiziViewModel(private val dataRepository: DataRepository) : ViewModel() {
    var content: MutableLiveData<List<Hit>> = MutableLiveData()
    var hitMap = hashMapOf<Int, Boolean>()
    val favHits = dataRepository.favHits
    suspend fun getRemoteMeizis(query: String, type: String) {
        try {
            val meiziRemoteData =
                dataRepository.getNetworkImages("15844840-d5e06026857e3e31a83ef6638", query, type)
            content.postValue(meiziRemoteData.hits)
            meiziRemoteData.hits.forEach {
                hitMap[it.id] = false
            }
        } catch (e: Exception) {
            Log.e("MeiziFailure", e.message)
            e.printStackTrace()
        }
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
//        favHits.value!!.forEach {
//            hitMap[it.id] = true
//        }
//        favHits.observe(viewModelScope, )
//        favHits.observe()
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