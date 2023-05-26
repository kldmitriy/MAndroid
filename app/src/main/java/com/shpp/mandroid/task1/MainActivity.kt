package com.shpp.mandroid.task1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.shpp.mandroid.task1.databinding.ActivityMainBinding

// TODO Binding
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        displayName()
    }

    private fun displayName() {

        binding.textName.text = parseEmail(intent.getStringExtra(SignUp.GLOBAL_EMAIL))
    }

    //TODO regex to constants| uppercase
    private fun parseEmail(email: String?): CharSequence? {
        return Regex("""@[\w.]*""").replace(email.toString(), "").replace(".", " ")
    }
}