package com.example.blip

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.blip.Constants.CHECK_IS_LOGGED_IN
import com.example.blip.Constants.SHARED_PREF_IS_LOGGED_IN
import com.example.blip.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var fbAuth: FirebaseAuth
    private lateinit var sharedPreferences : SharedPreferences

    override fun onStart() {
        super.onStart()
        fbAuth = FirebaseAuth.getInstance()
//        if (fbAuth.currentUser != null){
//            val intent = Intent(this@LoginActivity, AllChatsActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//            finish()
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        fbAuth = FirebaseAuth.getInstance()

        mBinding.btnLoginUser.setOnClickListener(this)
        mBinding.btnGotoSignUp.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnLoginUser -> {
                    val userEmail = mBinding.etEmail.text.toString().trim()
                    val userPassword = mBinding.etPassword.text.toString().trim()
                    when {
                        TextUtils.isEmpty(userEmail) -> Toast.makeText(this,
                            "Enter valid username",
                            Toast.LENGTH_SHORT).show()
                        TextUtils.isEmpty(userPassword) -> Toast.makeText(this,
                            "Enter valid username",
                            Toast.LENGTH_SHORT).show()
                        else -> {
                            loginUser(userEmail, userPassword)
                        }
                    }
                }

                R.id.btnGotoSignUp -> {
                    val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                    Toast.makeText(this, "Go to sign up", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
            }
        }

    }
}

private fun loginUser(email: String, password: String) {
    fbAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                sharedPreferences = getSharedPreferences(SHARED_PREF_IS_LOGGED_IN, Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.apply {
                    putString(CHECK_IS_LOGGED_IN, "true")
                    apply()
                }
                val intent = Intent(this, AllChatsActivity::class.java)
                finish()
                startActivity(intent)
            } else {
                Toast.makeText(this@LoginActivity, "User does not exist", Toast.LENGTH_SHORT).show()
            }
        }
}
}