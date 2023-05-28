package com.shpp.mandroid.task1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shpp.mandroid.task1.databinding.ActivityMainBinding

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
        binding.textName.text = parseEmail(intent.getStringExtra(Constants.INTENT_KEY_EMAIL))
    }

    private fun parseEmail(email: String?): String {
        return Regex("""@[\w.]*""").replace(email.toString(), "")
            .split(".")
            .joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() }}
    }
}