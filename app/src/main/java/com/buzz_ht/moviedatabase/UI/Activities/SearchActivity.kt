package com.buzz_ht.moviedatabase.UI.Activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.buzz_ht.moviedatabase.R
import com.buzz_ht.moviedatabase.UI.Model.MovieModel
import com.buzz_ht.moviedatabase.UI.Retrofit.RetrofitAPI
import com.buzz_ht.moviedatabase.UI.Retrofit.RetrofitClient
import com.buzz_ht.moviedatabase.UI.Utils.Constant
import com.buzz_ht.moviedatabase.UI.Utils.GeneralUtils
import com.google.gson.Gson
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.Serializable


class SearchActivity : AppCompatActivity(), Serializable {

    lateinit var btnSearch: Button
    lateinit var btnMyWatchlist: Button
    lateinit var edtMovieName: EditText
    lateinit var imgPoster: ImageView
    private lateinit var resultMain: Response<MovieModel?>
    private val TAG = "SearchActLogs"
    lateinit var lottieWait: LottieAnimationView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        drawBehindStatusBar()
        hideActionBar()
        viewSetup()


    }

    override fun onResume() {
        super.onResume()
        getWatchList()
    }

    private fun getWatchList() {
        val sharedPref = getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE)
        val watchlist = sharedPref.all
        val watchlistJson = Gson().toJson(watchlist)

        Log.d("Watchlist", watchlist.toString())
        Log.d("Watchlist", Gson().toJson(watchlist))

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

    private fun viewSetup() {
        btnSearch = findViewById(R.id.btnSearch)
        // btnMyWatchlist = findViewById(R.id.btnMyWatchlist)
        edtMovieName = findViewById(R.id.edtMovieName)
        imgPoster = findViewById(R.id.imgPoster)

        btnSearch.setOnClickListener {
            Log.d("ResultMovie1", edtMovieName.text.toString())
            if (edtMovieName.text.toString() != "") {
                // lottieWait.visibility= View.VISIBLE
                // progressBar.visibility=View.VISIBLE

                Handler().postDelayed({
                    callSearchAPI(edtMovieName.text.toString())
                }, 100)

            } else {
                GeneralUtils.showToastLong(this, "Please enter a valid name")
            }

        }

        /*btnMyWatchlist.setOnClickListener {

        }*/

        //    lottieWait=findViewById(R.id.lottieWait)
        progressBar = findViewById(R.id.progressBar)
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
                // lottieWait.visibility=View.GONE
                progressBar.visibility = View.GONE
                startActivity(intent)


            } else {
                GeneralUtils.showToastLong(this@SearchActivity, "Movie Not Found")
            }
        }
    }


}