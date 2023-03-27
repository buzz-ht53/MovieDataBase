package com.buzz_ht.moviedatabase.UI.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.buzz_ht.moviedatabase.R
import com.buzz_ht.moviedatabase.UI.Model.MovieModel
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
    private lateinit var resultMain: Response<MovieModel?>
    private val TAG = "SearchActLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewSetup()


    }

    private fun viewSetup() {
        btnSearch = findViewById(R.id.btnSearch)
        edtMovieName = findViewById(R.id.edtMovieName)
        imgPoster = findViewById(R.id.imgPoster)
        btnSearch.setOnClickListener {
            Log.d("ResultMovie1", edtMovieName.text.toString())
            if (edtMovieName.text.toString() != "") {
                callSearchAPI(edtMovieName.text.toString())
            } else {
                GeneralUtils.showToastLong(this, "Please enter a valid name")
            }

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
                if (result != null)
                    resultMain = result
                showResults(resultMain.body()!!)
            }
        }

        /* Thread(Runnable {

         }).start()*/
        /*CoroutineScope(Dispatchers.IO).launch {


        }*/
    }

    private fun showResults(resultMain: MovieModel) {

        if (resultMain != null) {
            Log.d("ResultMovie4", resultMain.toString())

            Log.d("TAG", resultMain.Response.toString())

            if (resultMain.Response.equals("True")) {

                Log.d(TAG, "inside-intent")

                val intent = Intent(applicationContext, ResultActivity::class.java)
                val b = Bundle()
                b.putSerializable("MovieModel", resultMain)
                intent.putExtras(b)
                //  intent.putExtra("MovieModel",resultMain)
                startActivity(intent)


            } else {
                GeneralUtils.showToastLong(this@SearchActivity, "Movie Not Found")
            }
        }
    }


}