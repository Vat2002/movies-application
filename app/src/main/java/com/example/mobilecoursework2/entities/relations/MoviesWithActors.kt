package com.example.mobilecoursework2.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.mobilecoursework2.entities.Actor
import com.example.mobilecoursework2.entities.Movie

data class MoviesWithActors (
        @Embedded val movies : Movie,
        @Relation(
                parentColumn = "movieTitle",
                entityColumn = "actorName" ,
                associateBy = Junction(MovieActorCrossReference::class)
        )
        val actors : List<Actor>
)