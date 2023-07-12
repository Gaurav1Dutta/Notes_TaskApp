package com.example.notes_taskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.notes_taskapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    lateinit var bottomView : BottomNavigationView
    lateinit var notesDb: NotesDb
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragment)
        bottomView = findViewById(R.id.bottomView)
        binding.bottomView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.lvNotesMenu->{
                    navController.navigate(R.id.notes_List_Fragment)
                }
                R.id.lvTaskMenu->{
                    navController.navigate(R.id.task_List_Fragment)
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}