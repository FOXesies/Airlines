package com.test.testairlines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.test.home.HomeActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            Toast.makeText(this@SplashActivity, "sdfsd", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}