package com.shpp.mandroid.task1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.shpp.mandroid.task1.databinding.ActivitySignUpBinding

const val PASSWORD_MIN_LENGTH: Int = 8
const val STORAGE_LOGIN: String = "LoginDataStorage"
const val STORAGE_LOGIN_REMEMBER: String = "Remember"
const val STORAGE_LOGIN_EMAIL: String = "Email"
const val REGEX_PASSWORD_MATCH: String = "([a-zA-Z])(\\d)|(\\d)([a-zA-Z])"

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var lSettings: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lSettings = getSharedPreferences(STORAGE_LOGIN, Context.MODE_PRIVATE)
        if (lSettings.contains(STORAGE_LOGIN_REMEMBER)) {
            if (lSettings.getBoolean(STORAGE_LOGIN_REMEMBER, false)) {
                changeActivity(lSettings.getString(STORAGE_LOGIN_EMAIL, "").toString())
            }
        }

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

        setListeners()
    }

    private fun setListeners() {
        setDoOnTextChangedEmail()
        setDoOnTextChangedPassword()
        setOnClickRegisterListener()
    }

    private fun setDoOnTextChangedEmail() {
        binding.editEmailText.doOnTextChanged { text, start, before, count ->
            binding.editEmail.error =
                if (isEmailValid(text.toString())) null else getString(R.string.EMAIL_ERROR)
        }
    }

    private fun setDoOnTextChangedPassword() {
        binding.editPasswordText.doOnTextChanged { text, start, before, count ->
            binding.editPassword.error =
                if (!isPasswordLengthEnough(text.toString()))
                    getString(R.string.ERROR_SHORT_PASSWORD)
                else if (isPasswordValid(text.toString())) null
                else getString(R.string.ERROR_NOT_VALID_PASSWORD)
        }
    }

    private fun setOnClickRegisterListener() {
        binding.buttonRegister.setOnClickListener {
            val email = binding.editEmailText.text.toString()
            val password = binding.editPasswordText.text.toString()

            if (isEmailValid(email) && isPasswordLengthEnough(password) && isPasswordValid(password)) {
                if (binding.checkRememberMe.isChecked) setAutoLoginData(email)
                changeActivity(email)
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return Regex(REGEX_PASSWORD_MATCH).find(password) != null
    }

    private fun isPasswordLengthEnough(password: String): Boolean {
        return password.count() >= PASSWORD_MIN_LENGTH
    }

    private fun setAutoLoginData(email: String) {
        val editor: Editor = lSettings.edit()
        editor.putBoolean(STORAGE_LOGIN_REMEMBER, true)
        editor.putString(STORAGE_LOGIN_EMAIL, email)
        editor.apply()
    }

    private fun changeActivity(email: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.INTENT_KEY_EMAIL, email)
        startActivity(intent)
    }
}