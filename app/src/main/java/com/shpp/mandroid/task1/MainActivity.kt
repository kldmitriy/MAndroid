package com.shpp.mandroid.task1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        displayName()
    }

    private fun displayName() {
        val textviewName = findViewById<TextView>(R.id.text_name)
        textviewName.text = parseEmail(intent.getStringExtra(getString(R.string.GLOBAL_EMAIL)))
    }

    private fun parseEmail(email: String?): CharSequence? {
        return Regex("""@[\w.]*""").replace(email.toString(), "").
            replace(".", " ")
    }
}