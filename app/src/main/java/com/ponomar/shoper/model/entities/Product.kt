package com.ponomar.shoper.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ponomar.shoper.model.entities.converters.ImageConverters

@Entity
data class Product(
    @PrimaryKey val id:Int,
    val title:String,
    val desc:String,
    val cost:Float,
    @TypeConverters(ImageConverters::class) val images:List<String>
)