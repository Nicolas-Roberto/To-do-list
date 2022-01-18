package com.example.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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

        binding.rvTasks.adapter = adapter
        updateList()

        insertListeners()
    }

    private val register = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
                binding.rvTasks.adapter = adapter
                updateList()
        }
    }

    private fun updateList() {
        val list = TaskDataSource.getList()
            binding.includeEmpty.emptyState.visibility = if(list.isEmpty()) View.VISIBLE
            else View.GONE

        adapter.submitList(list)
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            Intent(this, AddTaskActivity::class.java).let {
                register.launch(it)
            }
        }
        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            intent.let {
                register.launch(it)
            }
        }
        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }
    }
}