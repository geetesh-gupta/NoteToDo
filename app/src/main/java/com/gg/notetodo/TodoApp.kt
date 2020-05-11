package com.gg.notetodo

import android.app.Application
import com.gg.notetodo.db.NotesDatabase

class TodoApp : Application() {
    fun getNotesDb(): NotesDatabase {
        return NotesDatabase.getInstance(this)
    }
}
