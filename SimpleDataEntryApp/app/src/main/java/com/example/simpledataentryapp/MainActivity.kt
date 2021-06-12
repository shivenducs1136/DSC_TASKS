package com.example.simpledataentryapp

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.renderscript.ScriptGroup
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.simpledataentryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.DoneButton.setOnClickListener{
            mechanism(it)
        }
        binding.nameTxt.setOnClickListener {
            finish()
        }

    }
        private fun mechanism(view: View){

            binding.apply{
                nameTxt.visibility = View.INVISIBLE
                nameTxt.text = binding.editTextText.text
                invalidateAll()
                editTextText.visibility = View.GONE
                nameTxt.visibility = View.VISIBLE

            }

            // Hide the keyboard.
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
}
