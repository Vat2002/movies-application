package com.example.mobilecoursework2.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie (
    @PrimaryKey(autoGenerate = false) val movieTitle : String,

    val movieYear : String,
    val movieRated  : String,
    val movieReleasedData : String,
    val movieRunTime : String,
    val movieGenre : String,
    val movieDirector : String,
    val movieWrite : String,
    val movieActor : String,
    val moviePlot : String,
)