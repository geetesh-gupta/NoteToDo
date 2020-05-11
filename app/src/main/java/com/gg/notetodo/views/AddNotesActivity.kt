package com.gg.notetodo.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gg.notetodo.R
import com.gg.notetodo.util.AppConstant
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class AddNotesActivity : AppCompatActivity() {
    private lateinit var addNotesTitle: TextInputLayout
    private lateinit var addNotesDescription: TextInputLayout
    private lateinit var addNotesCreateButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        bindViews()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
    }

    private fun bindViews() {
        addNotesTitle = findViewById(R.id.addNotesTitle)
        addNotesDescription = findViewById(R.id.addNotesDescription)
        addNotesCreateButton = findViewById(R.id.addNotesCreateButton)
        addNotesCreateButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra(AppConstant.TITLE, addNotesTitle.editText?.text.toString())
            intent.putExtra(AppConstant.DESCRIPTION, addNotesDescription.editText?.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

}

