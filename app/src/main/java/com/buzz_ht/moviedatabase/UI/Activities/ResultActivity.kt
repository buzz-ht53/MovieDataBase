package com.buzz_ht.moviedatabase.UI.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.buzz_ht.moviedatabase.R
import com.buzz_ht.moviedatabase.UI.MovieModel
import com.squareup.picasso.Picasso
import java.io.Serializable

class ResultActivity : AppCompatActivity(), Serializable {

    lateinit var imgMoviePoster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        imgMoviePoster = findViewById(R.id.imgMoviePoster)

        var movie = intent.getSerializableExtra("MovieModel") as MovieModel
        Picasso.get()
            .load(movie.Poster)
            .into(imgMoviePoster)

    }
}