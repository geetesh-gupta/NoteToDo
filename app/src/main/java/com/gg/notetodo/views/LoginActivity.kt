package com.gg.notetodo.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gg.notetodo.R
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
        StoreSession.init(this)
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
                StoreSession.write(PrefConstant.FULL_NAME, fullName)
                StoreSession.write(PrefConstant.USER_NAME, userName)
                StoreSession.write(PrefConstant.PASSWORD, password)
                StoreSession.write(PrefConstant.IS_LOGGED_IN, true)
            }
        }
        buttonLogin.setOnClickListener(clickAction)
    }
}
