package com.example.mobilecoursework2

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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


class SearchForMovies : AppCompatActivity() {

   lateinit var getData : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_movies_activity)

        val retrieveMovieBT = findViewById<Button>(R.id.retrieveMovieBt)
        val getDataFromField = findViewById<TextView>(R.id.enterMoviePT)

        retrieveMovieBT.setOnClickListener {
            getData = getDataFromField.text.toString()
            Log.d("insert", "clicked button")
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
}