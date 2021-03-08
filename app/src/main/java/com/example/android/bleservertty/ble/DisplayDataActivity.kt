package com.example.android.bleservertty.ble

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.bleservertty.R
import com.google.android.material.chip.ChipGroup

class DisplayDataActivity : AppCompatActivity() {

    lateinit var deptchipgrp: ChipGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppdefaultTheme)
        setContentView(R.layout.activity_display_data)

        deptchipgrp = findViewById(R.id.chip_groupdept)

    }
}