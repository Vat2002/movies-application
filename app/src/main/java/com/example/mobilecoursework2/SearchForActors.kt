package com.example.mobilecoursework2

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilecoursework2.entities.Actor
import com.example.mobilecoursework2.entities.Movie
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.w3c.dom.Text

class SearchForActors : AppCompatActivity(){

    private lateinit var moviesDao: MovieDao

    lateinit var actorInputData : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_actors_activity)

        val searchActorBT = findViewById<Button>(R.id.searchActorInActBt)
        val getSearchActor = findViewById<TextView>(R.id.searchActorPT)

        searchActorBT.setOnClickListener {
            actorInputData = getSearchActor.text.toString()
            searchActor()
        }
    }

    private fun searchActor(){

        val viewSearchedActor = findViewById<TextView>(R.id.viewSearchedActors)
        viewSearchedActor.movementMethod = ScrollingMovementMethod()

        runBlocking {
            launch {
                /*if (actorInputData == moviesDao.getAllActor().toString()){
                    viewSearchedActor.text = actorInputData
                }*/
                viewSearchedActor.text = moviesDao.getActor().toString()
            }
        }
    }
}