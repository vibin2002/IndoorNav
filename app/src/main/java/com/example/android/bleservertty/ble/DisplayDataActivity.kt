package com.example.android.bleservertty.ble

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.bleservertty.R
import com.example.android.bleservertty.adapters.MyAdapter
import com.example.android.bleservertty.data.Faculty
import com.example.android.bleservertty.databinding.ActivityDisplayDataBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DisplayDataActivity : AppCompatActivity() {

    lateinit var binding: ActivityDisplayDataBinding
    private var faculties = mutableListOf<Faculty>()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayDataBinding.inflate(layoutInflater)
        setTheme(R.style.AppdefaultTheme)
        setContentView(binding.root)

        binding.progressCircularDda.visibility = ProgressBar.VISIBLE
        faculties.clear()
        retrieveFaculties()

    }

    private fun retrieveFaculties() = CoroutineScope(Dispatchers.IO).launch {
        firestore.collection("Faculty").get()
            .addOnSuccessListener {
                it?.documents?.forEach { document ->
                    val faculty = Faculty(
                        document.data?.get("Email").toString(),
                        document.data?.get("Name").toString())
                    faculty.let { it1 -> faculties.add(it1) }
                }
                Log.d("WandaVision",faculties.toString())
                binding.progressCircularDda.visibility = ProgressBar.INVISIBLE
                myAdapter = MyAdapter(faculties)
                binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                binding.recyclerView.addItemDecoration(DividerItemDecoration(applicationContext,DividerItemDecoration.VERTICAL))
                binding.recyclerView.adapter = myAdapter
                myAdapter.notifyDataSetChanged()
            }
    }
}