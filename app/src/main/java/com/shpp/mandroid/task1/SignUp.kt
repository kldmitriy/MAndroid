package com.shpp.mandroid.task1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.shpp.mandroid.task1.databinding.ActivitySignUpBinding


//TODO constants
const val PASSWORD_MIN_LENGTH: Int = 8
const val STORAGE_LOGIN: String = "LoginDataStorage"
const val STORAGE_LOGIN_REMEMBER: String = "Remember"
const val STORAGE_LOGIN_EMAIL: String = "Email"
//TODO binding
class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpBinding.inflate(layoutInflater)


        val lSettings: SharedPreferences = getSharedPreferences(STORAGE_LOGIN, Context.MODE_PRIVATE)

        if (lSettings.contains(STORAGE_LOGIN_REMEMBER)) {
            if (lSettings.getBoolean(STORAGE_LOGIN_REMEMBER, false)) {
                changeActivity(lSettings.getString(STORAGE_LOGIN_EMAIL, "").toString())
            }
        }


        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

        val editEmail = findViewById<TextInputLayout>(R.id.edit_email)
        val editEmailText = findViewById<TextInputEditText>(R.id.edit_email_text)


        editEmailText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                editEmail.error = if (isEmailValid(editEmailText.text.toString())) null
                else getString(R.string.EMAIL_ERROR)
            }
        })



        binding.editPasswordText.doOnTextChanged { text, start, before, count ->
            binding.editPassword.error =
                if (!isPasswordLengthEnough(text.toString()))
                    getString(R.string.ERROR_SHORT_PASSWORD)
                else if (isPasswordValid(text.toString())) null
                else getString(R.string.ERROR_NOT_VALID_PASSWORD)
        }

        setListeners()
    }

    private fun setListeners() {
       binding.buttonRegister.setOnClickListener {
           val email = findViewById<TextInputEditText>(R.id.edit_email_text).text.toString()
           val password = findViewById<TextInputEditText>(R.id.edit_password_text).text.toString()

           if (isEmailValid(email) && isPasswordLengthEnough(password) && isPasswordValid(password)) {
               if (findViewById<MaterialCheckBox>(R.id.check_remember_me).isChecked) {
                   val lSettings: SharedPreferences =
                       getSharedPreferences(STORAGE_LOGIN, Context.MODE_PRIVATE)
                   val editor: Editor = lSettings.edit()
                   editor.putBoolean(STORAGE_LOGIN_REMEMBER, true)
                   editor.putString(STORAGE_LOGIN_EMAIL, email)
                   editor.apply()
               }
               changeActivity(email)
           }
       }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return Regex(getString(R.string.REGEX_PASSWORD_MATCH)).find(password) != null
    }

    private fun isPasswordLengthEnough(password: String): Boolean {
        return password.count() >= PASSWORD_MIN_LENGTH
    }



    private fun changeActivity(email: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(GLOBAL_EMAIL, email)
        startActivity(intent)
    }

    companion object{
        const val GLOBAL_EMAIL  = "email"
    }
}