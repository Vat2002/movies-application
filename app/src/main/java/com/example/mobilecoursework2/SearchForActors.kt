package com.example.mobilecoursework2

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.mobilecoursework2.entities.relations.ActorsWithMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SearchForActors : AppCompatActivity(){

    private lateinit var moviesDao: MovieDao
    private lateinit var listOfMovies : List<ActorsWithMovies>
    lateinit var actorInputData : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_actors_activity)

        val searchActorBT = findViewById<Button>(R.id.searchActorInActBt)
        val getSearchActor = findViewById<TextView>(R.id.searchActorPT)
        val db = Room.databaseBuilder(this, MoviesDatabase::class.java, "Movies_Database").build()
        moviesDao = db.movieDao()

        searchActorBT.setOnClickListener {
            actorInputData = getSearchActor.text.toString()
            Log.d("Input",actorInputData)
            searchMoviesWithActor(actorInputData)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun searchMoviesWithActor(query : String){

        val viewSearchedActor = findViewById<TextView>(R.id.viewSearchedActors)
        viewSearchedActor.movementMethod = ScrollingMovementMethod()

        runBlocking {
            launch {
                withContext(Dispatchers.IO){
                    listOfMovies = moviesDao.getActorsWithMovies(query)
                    val sizeList = listOfMovies.size
                    Log.d("List Size", "$sizeList")
                }
                for(film in listOfMovies){
                    Log.d("Insert","$film")
                }
                if(listOfMovies.size > 1){
                    for(loop in listOfMovies){
                        for(movie in loop.movies){
                            val movieTitle = movie.movieTitle
                            val actorName = loop.actors.actorName
                            Log.d("Actor dets","$movieTitle + $actorName")

                            viewSearchedActor.text = "Actor Name:$actorName\nMovie Title:$movieTitle"
                        }
                    }
                } else if(listOfMovies.size == 1){
                    val movieTitle = listOfMovies[0].movies[0].movieTitle
                    viewSearchedActor.text =  "Movie Title:$movieTitle"
                }
            }
        }
    }
}