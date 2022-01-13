package com.example.todolist.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityAddTaskBinding
import com.example.todolist.datasource.TaskDataSource
import com.example.todolist.extensions.format
import com.example.todolist.extensions.text
import com.example.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialTextInputPicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*

class AddTaskActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()
    }

    private fun insertListeners() {
        binding.inputDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener{
                val timeZone = TimeZone.getDefault()
                val offSet = timeZone.getOffset(Date().time) * -1
                binding.inputDate.text = Date(it + offSet).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.inputHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener{
                val hour = if(timePicker.hour in 0..9) "0${timePicker}" else timePicker.hour
                val minute = if(timePicker.minute in 0..9) "0${timePicker}" else timePicker.minute

                binding.inputHour.text = "$hour:$minute"
            }
            timePicker.show(supportFragmentManager, null)
        }

        binding.inputDescription.editText?.setOnClickListener {
            //binding.inputDescription.text = inputDescription.toString()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnCreateTask.setOnClickListener {
            val task = Task(
                title = binding.inputTitle.text,
                description = binding.inputDescription.text,
                date = binding.inputDate.text,
                hour = binding.inputHour.text
            )
            TaskDataSource.insertTask(task)
            Log.e("TAG","insertListener" + TaskDataSource.getList())
        }
    }
}