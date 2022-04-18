package com.example.mobilecoursework2

import androidx.room.*
import com.example.mobilecoursework2.entities.Actor
import com.example.mobilecoursework2.entities.Movie
import com.example.mobilecoursework2.entities.relations.ActorsWithMovies
import com.example.mobilecoursework2.entities.relations.MovieActorCrossReference

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie : Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actor : Actor)

    @Query("Select * from movie")
    suspend fun getMovie(): List<Movie>

    @Query("Select * from actor")
    suspend fun getActor() : List<Actor>

    @Transaction
    @Query("Select * from actor Where actorName Like '%' || :query || '%'")
    suspend fun getActorsWithMovies(query: String) : List<ActorsWithMovies>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(actorCrossRef : MovieActorCrossReference)

}