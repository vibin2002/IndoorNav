package com.example.android.bleservertty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.chip.ChipGroup

class DisplayDataActivity : AppCompatActivity() {

    lateinit var deptchipgrp: ChipGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_data)

        deptchipgrp = findViewById(R.id.chip_groupdept)

    }
}