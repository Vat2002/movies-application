package com.example.mobilecoursework2.entities.relations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.example.mobilecoursework2.entities.Actor
import com.example.mobilecoursework2.entities.Movie

@Entity
data class ActorsWithMovies (
    @Embedded
    val actors: Actor,
    @Relation(
        parentColumn = "actorName",
        entityColumn = "movieTitle",
        associateBy = Junction(MovieActorCrossReference::class)
    )
    val movies: List<Movie>
    )