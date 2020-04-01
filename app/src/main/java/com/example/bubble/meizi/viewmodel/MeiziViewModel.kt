package com.example.bubble.meizi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bubble.meizi.model.Girl
import com.example.bubble.meizi.model.Hit
import com.example.bubble.meizi.network.MeiziNetwork
import kotlinx.coroutines.launch
import java.lang.Exception

class MeiziViewModel : ViewModel() {
    var content:MutableLiveData<List<Hit>> = MutableLiveData()
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
    init {
        viewModelScope.launch {
            getMeizi()
        }
    }
}