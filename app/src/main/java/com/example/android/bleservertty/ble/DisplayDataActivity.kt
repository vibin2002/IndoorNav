package com.example.android.bleservertty.ble

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.forEach
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.bleservertty.R
import com.example.android.bleservertty.adapters.MyAdapter
import com.example.android.bleservertty.data.Faculty
import com.example.android.bleservertty.databinding.ActivityDisplayDataBinding
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DisplayDataActivity : AppCompatActivity() {

    lateinit var binding: ActivityDisplayDataBinding
    private var faculties = mutableListOf<Faculty>()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var myAdapter: MyAdapter
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayDataBinding.inflate(layoutInflater)
        setTheme(R.style.AppdefaultTheme)
        setContentView(binding.root)

        binding.progressCircularDda.visibility = ProgressBar.VISIBLE
        faculties.clear()
        retrieveFaculties()

        binding.chipGroupdept.forEach { child ->
            (child as? Chip)?.setOnCheckedChangeListener { _, _ ->
                registerFilterChanged()
            }
        }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu , menu)
        val item: MenuItem = menu!!.findItem(R.id.searchView)
        searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                myAdapter.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun registerFilterChanged() {
        val ids = binding.chipGroupdept.checkedChipIds

        val titles = mutableListOf<CharSequence>()

        ids.forEach { id ->
            titles.add(binding.chipGroupdept.findViewById<Chip>(id).text)
        }

        val text = if (titles.isNotEmpty()) {
            titles.joinToString(", ")
        } else {
            ""
        }
        myAdapter.filter.filter(text)
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                myAdapter.filter.filter(text)
//                return true
//            }
//
//        })
    }

}

