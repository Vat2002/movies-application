package com.example.mobilecoursework2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import com.example.mobilecoursework2.entities.Actor
import com.example.mobilecoursework2.entities.Movie
import com.example.mobilecoursework2.entities.relations.MovieActorCrossReference
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var moviesDao: MovieDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(this, MoviesDatabase::class.java, "Movies_Database").build()
        moviesDao = db.movieDao()

        val addMoviesToDB = findViewById<Button>(R.id.addToDBBt)
        val searchForMovieBT = findViewById<Button>(R.id.searchMoviesBt)
        val searchActorsBT = findViewById<Button>(R.id.searchActorsBt)

        addMoviesToDB.setOnClickListener {
            Toast.makeText(applicationContext,"Added movies database", Toast.LENGTH_SHORT).show()
            enterData()
        }

        searchForMovieBT.setOnClickListener {
            val searchMovieIntent = Intent(this, RetrieveAndSaveMovies::class.java)
            startActivity(searchMovieIntent)
        }

        searchActorsBT.setOnClickListener {
            val searchActorsIntent = Intent(this, SearchForActors::class.java)
            startActivity(searchActorsIntent)
        }

    }

    private fun enterData() {
        runBlocking {
            launch {
                val movies = listOf(
                    Movie(
                        "The Shawshank Redemption",
                        "1994",
                        "R",
                        "14 Oct 1994",
                        "142 min",
                        "Drama",
                        "Frank Darabont",
                        "Stephen King, Frank Darabont",
                        "Tim Robbins, Morgan Freeman, Bob Gunton",
                        "Two imprisoned men bond over a number of years, finding solace\n" +
                                "and eventual redemption through acts of common decency."
                    ),
                    Movie(
                        "Batman: The Dark Knight Returns, Part 1",
                        "2012",
                        "PG-13",
                        "25 Sep 2012",
                        "76 min",
                        "Animation, Action, Crime, Drama, Thriller",
                        "Jay Oliva",
                        "Bob Kane (character created by: Batman), Frank Miller (comic book), Klaus Janson (comic book), Bob\n" +
                                "Goodman",
                        "Peter Weller, Ariel Winter, David Selby, Wade Williams",
                        "Batman has not been seen for ten years. A new breed\n" +
                                "of criminal ravages Gotham City, forcing 55-year-old Bruce Wayne back\n" +
                                "into the cape and cowl. But, does he still have what it takes to fight\n" +
                                "crime in a new era?"
                    ),
                    Movie(
                        "The Lord of the Rings: The Return of the King",
                        "2003",
                        "PG-13",
                        "17 Dec 2003",
                        "201 min",
                        "Action, Adventure, Drama",
                        "Peter Jackson",
                        "J.R.R. Tolkien, Fran Walsh, Philippa Boyens",
                        "Elijah Wood, Viggo Mortensen, Ian McKellen",
                        "Gandalf and Aragorn lead the World of Men against Sauron's\n" +
                                "army to draw his gaze from Frodo and Sam as they approach Mount Doom\n" +
                                "with the One Ring."
                    ),
                    Movie(
                        "Inception",
                        "2010",
                        "PG-13",
                        "16 Jul 2010",
                        "148 min",
                        "Action, Adventure, Sci-Fi",
                        "Christopher Nolan",
                        "Christopher Nolan",
                        "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page",
                        "A thief who steals corporate secrets through the use of\n" +
                                "dream-sharing technology is given the inverse task of planting an idea\n" +
                                "into the mind of a C.E.O., but his tragic past may doom the project\n" +
                                "and his team to disaster."
                    ),
                    Movie(
                        "The Matrix",
                        "1999",
                        "R",
                        "31 Mar 1999",
                        "136 min",
                        "Action, Sci-Fi",
                        "Lana Wachowski, Lilly Wachowski",
                        "Lilly Wachowski, Lana Wachowski",
                        "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss",
                        "When a beautiful stranger leads computer hacker Neo to a\n" +
                                "forbidding underworld, he discovers the shocking truth--the life he\n" +
                                "knows is the elaborate deception of an evil cyber-intelligence."
                    )
                )

                val actors = listOf(
                    Actor("Tim Robbins"),
                    Actor("Morgan Freeman"),
                    Actor("Bob Gunton"),
                    Actor("Peter Weller"),
                    Actor("Ariel Winter"),
                    Actor("David Selby"),
                    Actor("Wade Williams"),
                    Actor("Elijah Wood"),
                    Actor("Viggo Mortensen"),
                    Actor("Ian McKellen"),
                    Actor("Leonardo DiCaprio"),
                    Actor("Joseph Gordon-Levitt"),
                    Actor("Elliot Page"),
                    Actor("Keanu Reeves"),
                    Actor("Laurence Fishburne"),
                    Actor("Carrie-Anne Moss"),
                )

                val movieActorRelationships = listOf(
                    MovieActorCrossReference("The Shawshank Redemption", "Tim Robbins"),
                    MovieActorCrossReference("The Shawshank Redemption", "Morgan Freeman"),
                    MovieActorCrossReference("The Shawshank Redemption", "Bob Gunton"),
                    MovieActorCrossReference(
                        "Batman: The Dark Knight Returns, Part 1",
                        "Peter Weller"
                    ),
                    MovieActorCrossReference(
                        "Batman: The Dark Knight Returns, Part 1",
                        "Ariel Winter"
                    ),
                    MovieActorCrossReference(
                        "Batman: The Dark Knight Returns, Part 1",
                        "David Selby"
                    ),
                    MovieActorCrossReference(
                        "Batman: The Dark Knight Returns, Part 1",
                        "Wade Williams"
                    ),
                    MovieActorCrossReference(
                        "The Lord of the Rings: The Return of the King",
                        "Elijah Wood"
                    ),
                    MovieActorCrossReference(
                        "The Lord of the Rings: The Return of the King",
                        "Viggo Mortensen"
                    ),
                    MovieActorCrossReference(
                        "The Lord of the Rings: The Return of the King",
                        "Ian McKellen"
                    ),
                    MovieActorCrossReference("Inception", "Leonardo DiCaprio"),
                    MovieActorCrossReference("Inception", "Joseph Gordon-Levitt"),
                    MovieActorCrossReference("Inception", "Elliot Page"),
                    MovieActorCrossReference("The Matrix", "Keanu Reeves"),
                    MovieActorCrossReference("The Matrix", "Laurence Fishburne"),
                    MovieActorCrossReference("The Matrix", "Carrie-Anne Moss"),
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
                    Log.d("insert", "$film")
                }
            }
        }
    }
}