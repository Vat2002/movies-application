package com.example.mobilecoursework2.entities.relations

import androidx.room.Entity

@Entity(primaryKeys = [ "movieTitle","actorName"])
data class MovieActorCrossReference(
    val movieTitle: String,
    val actorName: String
)