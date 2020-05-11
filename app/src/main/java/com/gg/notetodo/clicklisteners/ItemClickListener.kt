package com.gg.notetodo.clicklisteners

import com.gg.notetodo.db.Notes

interface ItemClickListener {

    fun onClick(notes: Notes)

    fun onUpdate(notes: Notes)

}
