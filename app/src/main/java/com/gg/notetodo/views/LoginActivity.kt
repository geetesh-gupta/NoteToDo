package com.gg.notetodo.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.gg.notetodo.R
import com.gg.notetodo.util.AppConstant
import com.gg.notetodo.util.PrefConstant
import com.gg.notetodo.util.StoreSession
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextName: TextInputLayout
    private lateinit var editTextUsername: TextInputLayout
    private lateinit var editTextPassword: TextInputLayout
    lateinit var buttonLogin: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindView()
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        StoreSession.init(this)
        if (StoreSession.read(PrefConstant.IS_LOGGED_IN) == true) startToDoActivity(
            StoreSession.readString(
                PrefConstant.FULL_NAME
            )!!
        )
    }

    private fun bindView() {
        editTextName = findViewById(R.id.loginName)
        editTextUsername = findViewById(R.id.loginUsername)
        editTextPassword = findViewById(R.id.loginPassword)
        buttonLogin = findViewById(R.id.loginButton)
        val clickAction = View.OnClickListener {
            val fullName = editTextName.editText?.text.toString()
            val userName = editTextUsername.editText?.text.toString()
            val password = editTextPassword.editText?.text.toString()
            if (fullName.isNotEmpty() && userName.isNotEmpty() && password.isNotEmpty()) {
                storeLoginData(fullName, userName, password)
                startToDoActivity(fullName)
            } else {
                Toast
                    .makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        buttonLogin.setOnClickListener(clickAction)
    }

    private fun startToDoActivity(fullName: String) {
        val intent = Intent(this@LoginActivity, ToDoActivity::class.java)
        intent.putExtra(AppConstant.FULL_NAME, fullName)
        startActivity(intent)
    }

    private fun storeLoginData(
        fullName: String,
        userName: String,
        password: String
    ) {
        StoreSession.init(this)
        StoreSession.write(PrefConstant.FULL_NAME, fullName)
        StoreSession.write(PrefConstant.USER_NAME, userName)
        StoreSession.write(PrefConstant.PASSWORD, password)
        StoreSession.write(PrefConstant.IS_LOGGED_IN, true)
    }
}
