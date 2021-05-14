package com.neobis.fms.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.neobis.fms.MainActivity
import com.neobis.fms.databinding.ActivityAuthBinding
import com.neobis.fms.model.LoginRequest
import com.neobis.fms.model.LoginResponse
import com.neobis.fms.network.RetrofitInstance
import com.neobis.fms.network.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)


        if (sessionManager.fetchUserToken() != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.signInBtn.setOnClickListener {

            loginUser(binding.email.text.toString().trim(), binding.password.text.toString().trim())
        }


        binding.missingPasswordTv.setOnClickListener {
            startActivity(Intent(this, RecoveryPasswordActivity::class.java))
        }

    }


    private fun loginUser(email: String, password: String) {

        val userBody = LoginRequest(email, password)
        RetrofitInstance.api.login(userBody).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (response.code() == 200 && response.body()?.token != null) {
                    sessionManager.saveUserToken(response.body()?.token)
                    startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                    finish()
                } else if (response.code() == 403) {

                    Toast.makeText(this@AuthActivity, "Вы ввели не правильный логин или пароль", Toast.LENGTH_SHORT).show()

                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@AuthActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

    }


}