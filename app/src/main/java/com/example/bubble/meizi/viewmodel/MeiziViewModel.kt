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
    val content:MutableLiveData<List<Hit>> = MutableLiveData()
    val favHits: MutableLiveData<List<Hit>> by lazy { MutableLiveData<List<Hit>>()}
    suspend fun getMeizi(){
        try{
            val meizis = MeiziNetwork.instance.getPixabayService().getPixabayImgs("15844840-d5e06026857e3e31a83ef6638", "Shibas", "photo");
            Log.d("Meizi", meizis.toString())
            content!!.postValue(meizis.hits);
        }catch(e: Exception){
            Log.e("MEiziFailure", e.message)
            e.printStackTrace()
        }
    }
    suspend fun getRemoteMeizis(query: String, type: String) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val meiziRemoteData = dataRepository.getNetworkImages("15844840-d5e06026857e3e31a83ef6638", query, type)
                content!!.postValue(meiziRemoteData.hits)
            } catch (e:Exception) {
                Log.e("MeiziFailure", e.message)
                e.printStackTrace()
            }
        }
    }

    fun getLocalFavHits() {
        viewModelScope.launch(Dispatchers.IO){
            val fetchedFavHits = dataRepository.getLocalFavHits()
            Log.d("Meizi", "local Hits count ${fetchedFavHits.size}")
            withContext(Dispatchers.Main) {
                favHits.value = fetchedFavHits
            }
        }
    }

    fun saveFavImage(img: Hit) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.saveImage(img)
        }
    }

    init {
        viewModelScope.launch {
            getRemoteMeizis("Shiba Inus", "photo")
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