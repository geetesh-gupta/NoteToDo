package com.gg.notetodo.views

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gg.notetodo.R
import com.gg.notetodo.TodoApp
import com.gg.notetodo.adapter.NotesAdapter
import com.gg.notetodo.clicklisteners.ItemClickListener
import com.gg.notetodo.db.Notes
import com.gg.notetodo.util.AppConstant
import com.gg.notetodo.util.PrefConstant
import com.gg.notetodo.util.StoreSession
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import java.util.ArrayList

class ToDoActivity : AppCompatActivity() {

    private val ADD_NOTES_CODE = 100
    private var fullName: String? = ""
    private lateinit var todoHeader: MaterialTextView
    private lateinit var todoRecyclerViewNotes: RecyclerView
    private lateinit var todoAddNotesButton: FloatingActionButton
    private var listNotes = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        bindView()
        getIntentData()
        setHeader()
        getDataFromDataBase()
        setupRecyclerView()
    }

    private fun bindView() {
        todoHeader = findViewById(R.id.todoHeader)
        todoRecyclerViewNotes = findViewById(R.id.todoRecyclerView)
        todoAddNotesButton = findViewById(R.id.todoAddNotesButton)
        todoAddNotesButton.setOnClickListener {
            startActivityForResult(
                Intent(this, AddNotesActivity::class.java),
                ADD_NOTES_CODE
            )
        }
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

    private fun setHeader() {
        todoHeader.text = "Welcome, $fullName"
    }

    private fun getDataFromDataBase() {
        val notesApp = applicationContext as TodoApp
        val notesDao = notesApp.getNotesDb().notesDao()
        listNotes.addAll(notesDao.getAll())
    }


    private fun setupRecyclerView() {
        val itemClickListener = object : ItemClickListener {
            override fun onUpdate(notes: Notes) {
                val notesApp = applicationContext as TodoApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }

            override fun onClick(notes: Notes) {
            }
        }
        val notesAdapter = NotesAdapter(listNotes, itemClickListener)
        notesAdapter.setHasStableIds(true)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        todoRecyclerViewNotes.layoutManager = linearLayoutManager
        todoRecyclerViewNotes.adapter = notesAdapter
        todoRecyclerViewNotes.setItemViewCacheSize(20)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTES_CODE && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(AppConstant.TITLE);
            val description = data?.getStringExtra(AppConstant.DESCRIPTION)

            val note = Notes(
                title = title!!,
                description = description!!,
                isTaskCompleted = false
            )
            addNotesToDb(note)
            listNotes.add(note)
            todoRecyclerViewNotes.adapter?.notifyItemChanged(listNotes.size - 1)
        }
    }

    private fun addNotesToDb(notes: Notes) {
        val todoApp = applicationContext as TodoApp
        val notesDao = todoApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }
}
