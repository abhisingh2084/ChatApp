package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SingUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbref: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btnSingUp)

        btnSignUp.setOnClickListener {
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            signUp(name,email,password)

        }

    }

    private fun signUp(name: String, email: String, password: String){
        // Logic for login user

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    //code for jumping to home
                        addUserToDatabase(name,email,mAuth.currentUser?.uid!!)

                    val intent = Intent(this@SingUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)



                } else {
                    Toast.makeText(this@SingUp,"some Error occured",Toast.LENGTH_SHORT).show()

                }
            }

    }



    private fun addUserToDatabase(name: String, email: String, uid: String){
        mDbref = FirebaseDatabase.getInstance().getReference()
        mDbref.child("user").child(uid).setValue(User(name, email, uid))

    }



}