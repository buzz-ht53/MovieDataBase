package com.buzz_ht.moviedatabase.UI.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.buzz_ht.moviedatabase.R
import com.buzz_ht.moviedatabase.UI.MovieModel
import com.buzz_ht.moviedatabase.UI.Retrofit.RetrofitAPI
import com.buzz_ht.moviedatabase.UI.Retrofit.RetrofitClient
import com.buzz_ht.moviedatabase.UI.Utils.Constant
import com.buzz_ht.moviedatabase.UI.Utils.GeneralUtils
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.Serializable


class SearchActivity : AppCompatActivity(), Serializable {

    lateinit var btnSearch: Button
    lateinit var edtMovieName: EditText
    lateinit var imgPoster: ImageView
    var resultMain: Response<MovieModel?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        btnSearch = findViewById(R.id.btnSearch)
        edtMovieName = findViewById(R.id.edtMovieName)
        imgPoster = findViewById(R.id.imgPoster)
        btnSearch.setOnClickListener {
            Log.d("ResultMovie1", edtMovieName.text.toString())
            callSearchAPI(edtMovieName.text.toString())

        }
    }

    private fun callSearchAPI(movieName: String) {
        Log.d("ResultMovie2", "***INSIDE*****")
        val retrofitAPI =
            RetrofitClient.RetrofitClient.getRetroInstance().create(RetrofitAPI::class.java)

        runBlocking {
            launch {
                Log.d("ResultMovie3", movieName + Constant.API_KEY)
                val result = retrofitAPI.getMovie(movieName, Constant.API_KEY)
                resultMain = result
                showResults(resultMain!!)
            }
        }

        /* Thread(Runnable {

         }).start()*/
        /*CoroutineScope(Dispatchers.IO).launch {


        }*/
    }

    private fun showResults(resultMain: Response<MovieModel?>) {

        if (resultMain.body() != null) {
            Log.d("ResultMovie4", resultMain.body().toString())



            if (resultMain.body()?.Response.equals("True")) {

                var intent = Intent(applicationContext, ResultActivity::class.java)
                var b = Bundle()
                b.putSerializable("MovieModel", resultMain)
                intent.putExtras(b)
                startActivity(intent)

                /* if (resultMain.body()!!.Poster != null) {
                     Picasso.get()
                         .load(resultMain?.body()!!.Poster)
                         .into(imgPoster)
                 }*/
            } else {
                GeneralUtils.showToastLong(this@SearchActivity, "Movie Not Found")
            }
        }
    }


}