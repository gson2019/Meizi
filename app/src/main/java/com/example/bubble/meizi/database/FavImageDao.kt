package com.example.bubble.meizi.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bubble.meizi.model.Hit
import kotlinx.coroutines.Deferred

@Dao
interface FavImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newHit:Hit)

    @Insert
    suspend fun insertAllHits(hits: List<Hit>)

    @Delete
    suspend fun deleteHit(hit: Hit)

    @Query("select * from favorite")
    fun getFavHits(): LiveData<List<Hit>>

    @Query("select * from favorite")
    fun getAllFavHits(): List<Hit>
}