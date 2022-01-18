package com.example.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()
    }

    private val register = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
                binding.rvTasks.adapter = adapter
                adapter.submitList(TaskDataSource.getList())
        }
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            Intent(this, AddTaskActivity::class.java).let {
                register.launch(it)
            }
        }
        adapter.listenerEdit = {
            Log.e("TAG","listenerEdit $it")
        }
        adapter.listenerDelete = {
            Log.e("TAG","listenerDelete $it")
        }
    }
}