package com.example.mobilecoursework2

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.mobilecoursework2.entities.Actor
import com.example.mobilecoursework2.entities.Movie
import com.example.mobilecoursework2.entities.relations.MovieActorCrossReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class RetrieveAndSaveMovies : AppCompatActivity() {

    private lateinit var moviesDao: MovieDao

    lateinit var getData : String

    lateinit var movieTitle: String
    lateinit var movieYear: String
    lateinit var movieRated: String
    lateinit var movieReleased: String
    lateinit var movieRuntime: String
    lateinit var movieGenre: String
    lateinit var movieDirector: String
    lateinit var movieWriter: String
    lateinit var movieActor: String
    lateinit var moviePlot: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retrieve_save_movies_activity)

        val db = Room.databaseBuilder(this, MoviesDatabase::class.java, "Movies_Database").build()
        moviesDao = db.movieDao()

        val retrieveMovieBT = findViewById<Button>(R.id.retrieveMovieBt)
        val saveMovieToDBBT = findViewById<Button>(R.id.saveMovieBt)
        val getDataFromField = findViewById<TextView>(R.id.enterMoviePT)


        saveMovieToDBBT.setOnClickListener {
            saveDataToDB()
        }

        retrieveMovieBT.setOnClickListener {
            getData = getDataFromField.text.toString()
            Log.d("insert", "retrieve button clicked")
            callAPI()
        }
    }

    private fun callAPI() {

        var data: String = ""
        val viewRetrievedData = findViewById<TextView>(R.id.viewRetrievedMovie)
        //android:scrollbars = "vertical" add to the xml file
        viewRetrievedData.movementMethod = ScrollingMovementMethod()

        runBlocking {
            launch {
                withContext(Dispatchers.IO) {
                    // this will contain the whole of JSON
                    val movieName = getData
                    val stb = StringBuilder("")
                    val url2string = "https://www.omdbapi.com/?t=$movieName&apikey=f9c5d50c"
                    val url = URL(url2string)
                    val con = url.openConnection() as HttpURLConnection
                    val bf: BufferedReader
                    try {
                        bf = BufferedReader(InputStreamReader(con.inputStream))
                    } catch (e: IOException) {
                        e.printStackTrace()
                        return@withContext
                    }
                    var line = bf.readLine()
                    while (line != null) {
                        stb.append(line)
                        line = bf.readLine()
                    }
                    // pick up all the data
                    Log.d("insert", "outside parsejson")
                    data = parseJSON(stb)
                }
                viewRetrievedData.text = data
                Log.d("insert",data)
            }
        }
    }

    private fun parseJSON(stb: java.lang.StringBuilder): String {

        Log.d("insert", "inside parser json")

        // extract the actual data
        val json = JSONObject(stb.toString())

        movieTitle = json["Title"].toString()
        movieYear = json["Year"].toString()
        movieRated = json["Rated"].toString()
        movieReleased = json["Released"].toString()
        movieRuntime = json["Runtime"].toString()
        movieGenre = json["Genre"].toString()
        movieDirector = json["Director"].toString()
        movieWriter = json["Writer"].toString()
        movieActor = json["Actors"].toString()
        moviePlot = json["Plot"].toString()

        return ("Title:" + movieTitle + "\n" + "Year:" + movieYear + "\n" + "Rated:" + movieRated + "\n" + "Released:" + movieReleased + "\n" + "Runtime:" + movieRuntime +
                "\n" + "Genre:" + movieGenre + "\n" + "Director:" + movieDirector + "\n" + "Writer:" + movieWriter + "\n" + "Actor:" + movieActor + "\n" +
                "Plot:" + moviePlot)
    }
  
    private fun saveDataToDB(){
        //enter code here
        runBlocking {
            launch {
                val movies = listOf(
                    Movie(movieTitle,movieYear,movieRated,movieReleased,movieRuntime,movieGenre,movieDirector,movieWriter,movieActor,moviePlot)
                )

                val actors = listOf(
                    Actor(movieActor)
                )

                val movieActorRelationships = listOf(
                    MovieActorCrossReference(movieTitle,movieActor)
                )

                for (movie in movies) {
                    moviesDao.insertMovie(movie)
                }

                for (actor in actors) {
                    moviesDao.insertActor(actor)
                }

                for (movieActorCrossReference in movieActorRelationships) {
                    moviesDao.insertCrossRef(movieActorCrossReference)
                }

                val movie: List<Movie> = moviesDao.getMovie()

                for (film in movie) {
                    Log.d("insert", "Movies:$film")
                }
            }
        }
    }
}