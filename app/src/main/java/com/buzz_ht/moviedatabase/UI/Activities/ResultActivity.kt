package com.buzz_ht.moviedatabase.UI.Activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.buzz_ht.moviedatabase.R
import com.buzz_ht.moviedatabase.UI.Model.MovieModel
import com.buzz_ht.moviedatabase.UI.Utils.GeneralUtils
import com.squareup.picasso.Picasso
import java.io.Serializable


class ResultActivity : AppCompatActivity(), Serializable {

    lateinit var movie: MovieModel
    lateinit var imgMoviePoster: ImageView
    lateinit var txtMovieTitle: TextView
    lateinit var txtMovieYear: TextView
    lateinit var txtMovieRated: TextView
    lateinit var txtMovieReleased: TextView
    lateinit var txtMovieDuration: TextView
    lateinit var txtMovieGenre: TextView
    lateinit var txtMoviePlot: TextView
    lateinit var txtMovieDirector: TextView
    lateinit var txtMovieWriter: TextView
    lateinit var txtActors: TextView
    lateinit var txtLanguage: TextView
    lateinit var txtCountry: TextView
    lateinit var txtAwards: TextView
    lateinit var txtIMDBRating: TextView


    lateinit var btnWatchNow: Button
    lateinit var btnWatchlist: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setupLayouts()
        hideActionBar()
        getDataFromIntent()
        populateViews()
        drawBehindStatusBar()
        setUpWatchlistButton()

    }

    private fun setUpWatchlistButton() {

        val sharedPref = getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE)
        val titleRetrieve = sharedPref.getString(movie.Title, "NA")
        if (titleRetrieve == "NA") {
            btnWatchlist.text = getString(R.string.watchlist)
        } else {
            btnWatchlist.text = getString(R.string.Added)
        }
    }

    private fun drawBehindStatusBar() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        getWindow().setStatusBarColor(Color.TRANSPARENT);

    }

    private fun hideActionBar() {
        getSupportActionBar()?.hide();
    }

    private fun setupLayouts() {
        imgMoviePoster = findViewById(R.id.imgMoviePoster)
        txtMovieTitle = findViewById(R.id.txtMovieTitle)
        txtMovieYear = findViewById(R.id.txtMovieYear)
        txtMovieRated = findViewById(R.id.txtMovieRated)
        txtMovieReleased = findViewById(R.id.txtMovieReleased)
        txtMovieDuration = findViewById(R.id.txtMovieDuration)
        txtMovieGenre = findViewById(R.id.txtMovieGenre)
        txtMoviePlot = findViewById(R.id.txtMoviePlot)
        txtMovieDirector = findViewById(R.id.txtMovieDirector)
        txtMovieWriter = findViewById(R.id.txtMovieWriter)
        txtActors = findViewById(R.id.txtActors)
        txtLanguage = findViewById(R.id.txtLanguage)
        txtCountry = findViewById(R.id.txtCountry)
        txtAwards = findViewById(R.id.txtAwards)
        txtIMDBRating = findViewById(R.id.txtIMDBRating)


        btnWatchNow = findViewById(R.id.btnWatchNow)
        btnWatchlist = findViewById(R.id.btnWatchlist)

        btnWatchlist.setOnClickListener {

            val sharedPref =
                getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE)
            val editSharedPref = sharedPref.edit()

            if (sharedPref.getString(movie.Title, "NA") == "NA") {
                editSharedPref.putString(movie.Title, movie.Title)
                editSharedPref.apply()
                editSharedPref.commit()
                btnWatchlist.text = getString(R.string.Added)
            } else {
                editSharedPref.putString(movie.Title, "NA")
                editSharedPref.apply()
                editSharedPref.commit()
                btnWatchlist.text = getString(R.string.watchlist)
            }

        }

        btnWatchNow.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/search?q=where+can+I+watch " + movie.Title)
                )
            )
        }

    }

    private fun getDataFromIntent() {
        movie = intent.getSerializableExtra("MovieModel") as MovieModel
    }

    private fun populateViews() {

        Picasso.get()
            .load(movie.Poster)
            .into(imgMoviePoster)

        txtMovieTitle.text = movie.Title
        txtMovieYear.text = "Year: " + movie.Year
        txtMovieRated.text = "Rated: " + movie.Rated
        txtMovieReleased.text = "Released: " + movie.Released
        txtMovieDuration.text = "Runtime: " + movie.Runtime
        txtMovieGenre.text = "Genre: " + movie.Genre
        txtMoviePlot.text = "Plot Synopsis: " + movie.Plot
        txtMovieDirector.text = "Director: " + movie.Director
        txtMovieWriter.text = "Writer: " + movie.Writer
        txtActors.text = "Actors: " + movie.Actors
        txtLanguage.text = "Language: " + movie.Language
        txtCountry.text = "Country: " + movie.Country
        txtAwards.text = "Awards: " + movie.Awards
        txtIMDBRating.text = "IMDB Rating: " + movie.imdbRating
    }

}