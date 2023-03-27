package com.buzz_ht.moviedatabase.UI.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.buzz_ht.moviedatabase.UI.Utils.GeneralUtils
import com.buzz_ht.moviedatabase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private var TAG = "LoginActivity"
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //VIew Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Firebase
        auth = Firebase.auth

        //View related
        binding.editTextNumber.hint = "Enter your Email/Phone Number"
        binding.editTextNumberPassword.hint = "Enter your 4 digit Password"
        binding.button.setOnClickListener {
            buttonClick()
        }

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

    private fun performEmailSignInWithFirebase(phoneNumber: String, password: String) {

        auth.signInWithEmailAndPassword(phoneNumber, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
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