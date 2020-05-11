package com.gg.notetodo.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gg.notetodo.R
import com.gg.notetodo.util.AppConstant
import com.gg.notetodo.util.PrefConstant
import com.gg.notetodo.util.StoreSession
import com.google.android.material.textview.MaterialTextView

class ToDoActivity : AppCompatActivity() {

    var fullName: String? = ""
    private lateinit var todoHeader: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        bindView()
        getIntentData()
        setHeader()
    }

    private fun bindView() {
        todoHeader = findViewById(R.id.todoHeader)
    }

    private fun setHeader() {
        todoHeader.text = "Welcome, $fullName"
    }

    private fun getIntentData() {
        val intent = intent
        if (intent.hasExtra(AppConstant.FULL_NAME)) {
            fullName = intent.getStringExtra(AppConstant.FULL_NAME)
        }
        if (fullName!!.isEmpty()) {
            fullName = StoreSession.readString(PrefConstant.FULL_NAME)
        }
    }
}
