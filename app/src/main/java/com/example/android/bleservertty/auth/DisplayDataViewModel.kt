package com.example.android.bleservertty.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.bleservertty.data.Faculty
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DisplayDataViewModel(): ViewModel() {
    var facultyMutableLiveData = MutableLiveData<List<Faculty>>()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private var faculties = mutableListOf<Faculty>()

     fun retrieveFaculties() = CoroutineScope(Dispatchers.IO).launch {
        firestore.collection("Faculty").get()
            .addOnSuccessListener {
                it?.documents?.forEach { document ->
                    val faculty = Faculty(
                        document.data?.get("Name").toString(),
                        document.data?.get("Email").toString())
                    faculty.let { it1 -> faculties.add(it1) }
                }
                Log.d("WandaVision",faculties.toString())
            }
        facultyMutableLiveData.postValue(faculties)
    }
}