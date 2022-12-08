package com.example.blip

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.blip.Constants.DATABASE_USER
import com.example.blip.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var mBinding : ActivitySignUpBinding
    private lateinit var fbAuth: FirebaseAuth
    private lateinit var mDbRef : DatabaseReference


    override fun onStart() {
        super.onStart()

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        fbAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(v: View?) {
        if(v!=null)
        {
            when(v.id)
            {
                R.id.btnSignUpUser -> {
                    val username = mBinding.etUsername.text.toString().trim()
                    val email = mBinding.etUsername.text.toString().trim()
                    val phoneNumber = mBinding.etUsername.text.toString().trim()
                    val password = mBinding.etUsername.text.toString().trim()
                    val date = mBinding.etDateOfBirth.text.toString().trim()
                    val confirmPassword = mBinding.etUsername.text.toString().trim()
                    when
                    {
                        TextUtils.isEmpty(username) -> Toast.makeText(this, "Enter valid username", Toast.LENGTH_SHORT).show()
                        TextUtils.isEmpty(email) -> Toast.makeText(this, "Enter valid username", Toast.LENGTH_SHORT).show()
                        TextUtils.isEmpty(phoneNumber) -> Toast.makeText(this, "Enter valid username", Toast.LENGTH_SHORT).show()
                        TextUtils.isEmpty(password) -> Toast.makeText(this, "Enter valid username", Toast.LENGTH_SHORT).show()
                        TextUtils.isEmpty(date) -> Toast.makeText(this, "Enter valid username", Toast.LENGTH_SHORT).show()
                        TextUtils.isEmpty(confirmPassword) -> Toast.makeText(this, "Enter valid username", Toast.LENGTH_SHORT).show()
                        !TextUtils.equals(password, confirmPassword) -> Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                        else -> {
                            signUpUser(email, username, phoneNumber, password, date)
                        }
                    }
                }

                R.id.etDateOfBirth -> {
                    chooseDate()
                }
            }
        }
    }

    private fun signUpUser(email: String, username: String, phoneNumber: String, password: String, date: String) {
        fbAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful)
                {
                    addUserToDatabase(username, email, fbAuth.currentUser?.uid!!, phoneNumber, password, date)
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    finish()
                    startActivity(intent)
                }
            }
    }

    private fun addUserToDatabase(username: String, email: String, uid: String, phoneNumber: String, password: String, dateOfBirth: String) {
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child(DATABASE_USER).child(uid).setValue(User(username, email, uid, phoneNumber, password, dateOfBirth))
    }


    private fun chooseDate() {
        val calendar = Calendar.getInstance()
        val curDay = calendar.get(Calendar.DAY_OF_MONTH)
        val curMonth = calendar.get(Calendar.MONTH)
        val curYear = calendar.get(Calendar.YEAR)
        val picker = DatePickerDialog(this,
            { _, year, month, dayOfMonth ->
                if (year > curYear || (year == curYear && month > curMonth) || (year == curYear && month == curMonth && dayOfMonth > curDay)) {
                    mBinding.etDateOfBirth.setText("")
                    Toast.makeText(this,
                        "Enter valid date",
                        Toast.LENGTH_LONG).show()
                } else
                //dk why but month has 0 based indexing that's why +1
                    mBinding.etDateOfBirth.setText("$dayOfMonth / ${month + 1} / $year")
            }, curYear, curMonth, curDay)
        picker.show()
    }
}