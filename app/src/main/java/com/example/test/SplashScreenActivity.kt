package com.example.test

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

/**
 * Splash screen activity
 *
 * @constructor Create empty Splash screen activity
 */
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        logo.alpha = 0f
        logo.animate().setDuration(1500).alpha(1f).withEndAction() {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}