package com.example.bubble.meizi.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName="favorite")
data class Hit(
    val downloads: Int,
    val favorites: Int,
    @PrimaryKey
    val id: Int,
    val imageHeight: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val tags: String,
    val type: String,
    val user: String,
    @Ignore
    val userImageURL: String,
    @Ignore
    val isSaved: Boolean,
    val user_id: Int,
    val views: Int
) {
     constructor(downloads: Int, favorites: Int, id: Int, imageHeight: Int, imageWidth: Int,
                 largeImageURL: String, likes: Int, tags: String, type:String, user: String, user_id: Int, views: Int) :
             this(downloads, favorites, id, imageHeight, imageWidth, largeImageURL, likes, tags, type, user, "", false, user_id, views)
}