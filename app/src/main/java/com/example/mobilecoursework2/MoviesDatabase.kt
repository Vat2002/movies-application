package com.example.mobilecoursework2

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobilecoursework2.entities.Actor
import com.example.mobilecoursework2.entities.Movie
import com.example.mobilecoursework2.entities.relations.MovieActorCrossReference

@Database(
    entities = [
        Movie::class,
        Actor::class,
        MovieActorCrossReference::class
    ],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase(){

    abstract fun movieDao() : MovieDao

    /*companion object{

        @Volatile
        private var INSTANCE : MoviesDatabase? = null

        fun getInstance(context : Context) : MoviesDatabase{
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    "MoviesDB"
                ).build().also {
                    INSTANCE = it
                }
            }
        }

    }*/

}