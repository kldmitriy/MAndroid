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
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class SignUp : AppCompatActivity() {
    private val PASSWORD_MIN_LENGTH: Int = 8
    private val STORAGE_LOGIN: String = "LoginDataStorage"
    private val STORAGE_LOGIN_REMEMBER: String = "Remember"
    private val STORAGE_LOGIN_EMAIL: String = "Email"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        val lSettings: SharedPreferences = getSharedPreferences(STORAGE_LOGIN, Context.MODE_PRIVATE)

        if (lSettings.contains(STORAGE_LOGIN_REMEMBER)) {
            if (lSettings.getBoolean(STORAGE_LOGIN_REMEMBER, false)) {
                changeActivity(lSettings.getString(STORAGE_LOGIN_EMAIL, "").toString())
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
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

        val editPassword = findViewById<TextInputLayout>(R.id.edit_password)
        val editPasswordText = findViewById<TextInputEditText>(R.id.edit_password_text)
        editPasswordText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val password = editPasswordText.text.toString()
                editPassword.error =
                    if (!isPasswordLengthEnough(password))
                        getString(R.string.ERROR_SHORT_PASSWORD)
                    else if (isPasswordValid(password)) null
                    else getString(R.string.ERROR_NOT_VALID_PASSWORD)
            }
        })
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

    fun onClickRegister(view: View) {
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

    private fun changeActivity(email: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(getString(R.string.GLOBAL_EMAIL), email)
        startActivity(intent)
    }
}