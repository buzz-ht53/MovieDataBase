package com.buzz_ht.moviedatabase.UI.Activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.buzz_ht.moviedatabase.R
import com.buzz_ht.moviedatabase.UI.Utils.GeneralUtils
import com.buzz_ht.moviedatabase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {

    private var TAG = "LoginActivity"
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //VIew Binding
        setUpViewBinding()
        drawBehindStatusBar()
        hideActionBar()
        //View related
        setUpViews()

        //Firebase
        auth = FirebaseAuth.getInstance()

        setImage()
    }

    private fun setImage() {
        var a = getScreenSize()
        binding.imageView.layoutParams.width = a[0]
        binding.imageView.layoutParams.height = a[0] * (80 / 100)
        binding.imageView.setImageResource(R.drawable.movie1)

    }

    private fun getScreenSize(): Array<Int> {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels

        var a = arrayOf(width, height)
        return a
    }

    private fun setUpViews() {
        binding.editTextNumber.hint = "Enter your Email/Phone Number"
        binding.editTextNumberPassword.hint = "Enter your 6 digit Password"
        binding.button.setOnClickListener {
            buttonClick()
        }
    }

    private fun setUpViewBinding() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
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

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        // updateUI(currentUser)
    }

    private fun buttonClick() {

        //Get input from the user
        var phoneNumber = binding.editTextNumber.text
        var password = binding.editTextNumberPassword.text

        if (phoneNumber.contains("@")) {
            //Email Flow
            if (password.length != 6) {
                binding.editTextNumberPassword.error = "Enter a valid 6 digit password"
            } else {
                performEmailSignInWithFirebase(phoneNumber.toString(), password.toString())
            }


        } else {
            Toast.makeText(
                this@LoginActivity,
                "Please enter a valid email address",
                Toast.LENGTH_LONG
            ).show()


            //TODO
            //Phone Number flow
            /* if(phoneNumber.length!=10){
                 binding.editTextNumber.error = "Please enter a valid Phone Number"
                 return
             }*/
        }


    }

    private fun performEmailSignUpWithFirebase(phoneNumber: String, password: String) {

        Log.d("FirebaseLogin", "Email: " + phoneNumber + " Password: " + password)


        auth.createUserWithEmailAndPassword(phoneNumber, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("FirebaseLogin", "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }


    private fun performEmailSignInWithFirebase(phoneNumber: String, password: String) {

        Log.d("FirebaseLogin", "Email: " + phoneNumber + " Password: " + password)

        auth.signInWithEmailAndPassword(phoneNumber, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("FirebaseLogin", "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }

    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {
            GeneralUtils.showToastLong(this@LoginActivity, "Successfully Signed In!")
            var intent = Intent(applicationContext, SearchActivity::class.java)
            startActivity(intent)
        }


    }
}