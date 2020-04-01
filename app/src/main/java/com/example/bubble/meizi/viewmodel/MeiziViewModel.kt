package com.example.bubble.meizi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bubble.meizi.model.Girl
import com.example.bubble.meizi.network.MeiziNetwork
import kotlinx.coroutines.launch
import java.lang.Exception

class MeiziViewModel : ViewModel() {
    var content:MutableLiveData<List<Girl>> = MutableLiveData()
    suspend fun getMeizi(){
        try{
            val meizis = MeiziNetwork.instance.getMeiziClient().getMeiziInfos("Girl", "Girl", 1, 10);
            Log.d("Meizi", meizis.toString())
            content!!.postValue(meizis.data);
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