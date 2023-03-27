package com.buzz_ht.moviedatabase.UI.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.buzz_ht.moviedatabase.R
import com.buzz_ht.moviedatabase.UI.Model.MovieModel
import com.squareup.picasso.Picasso
import java.io.Serializable

class ResultActivity : AppCompatActivity(), Serializable {

    lateinit var imgMoviePoster: ImageView
    lateinit var txtMovieTitle: TextView
    lateinit var txtMovieYear: TextView
    lateinit var txtMovieRated: TextView
    lateinit var txtMovieReleased: TextView
    lateinit var txtMovieDuration: TextView
    lateinit var txtMovieGenre: TextView
    lateinit var txtMoviePlot: TextView
    lateinit var txtMovieDirector: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        imgMoviePoster = findViewById(R.id.imgMoviePoster)
        txtMovieTitle = findViewById(R.id.txtMovieTitle)
        txtMovieYear = findViewById(R.id.txtMovieYear)
        txtMovieRated = findViewById(R.id.txtMovieRated)
        txtMovieReleased = findViewById(R.id.txtMovieReleased)
        txtMovieDuration = findViewById(R.id.txtMovieDuration)
        txtMovieGenre = findViewById(R.id.txtMovieGenre)
        txtMoviePlot = findViewById(R.id.txtMoviePlot)
        txtMovieDirector = findViewById(R.id.txtMovieDirector)

        var movie = intent.getSerializableExtra("MovieModel") as MovieModel
        Picasso.get()
            .load(movie.Poster)
            .into(imgMoviePoster)

        txtMovieTitle.text = movie.Title
        txtMovieYear.text = movie.Year
        txtMovieRated.text = movie.Rated
        txtMovieReleased.text = movie.Released
        txtMovieDuration.text = movie.Runtime
        txtMovieGenre.text = movie.Genre
        txtMoviePlot.text = movie.Plot
        txtMovieDirector.text = movie.Director


    }
}