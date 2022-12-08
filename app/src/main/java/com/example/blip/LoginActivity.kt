package com.example.blip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.blip.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

    }
}