package com.example.android.bleservertty.ble

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.android.bleservertty.R
import com.example.android.bleservertty.auth.DisplayDataViewModel
import com.example.android.bleservertty.data.Faculty
import com.google.android.material.chip.ChipGroup

class DisplayDataActivity : AppCompatActivity() {

    lateinit var deptchipgrp: ChipGroup
    private var faculties = mutableListOf<Faculty>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppdefaultTheme)
        setContentView(R.layout.activity_display_data)

        deptchipgrp = findViewById(R.id.chip_groupdept)

        val model = ViewModelProvider(this).get(DisplayDataViewModel::class.java)
        model.retrieveFaculties()
        model.facultyMutableLiveData.observe( this , Observer {
            faculties.addAll(it)
            findViewById<TextView>(R.id.textView6).text = it.toString()
            if (faculties.size == 0)
            {
                //Progressbar

            }
            else
            {
                Log.d("IronMan",faculties.toString())
            }
        })

    }
}