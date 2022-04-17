package com.example.mobilecoursework2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobilecoursework2.entities.Actor
import com.example.mobilecoursework2.entities.Movie
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(actorCrossRef : MovieActorCrossReference)

    /*@Transaction
    @Query("SELECT * FROM Movie WHERE movieTitle = :movieTitle")
    suspend fun getMoviesWithActors(movieTitle : String) : List<MoviesWithActors>*/
}