package com.studyhub.app

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val auth = FirebaseAuth.getInstance()
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvStatus = findViewById<TextView>(R.id.tvLoading)

        // Setting a longer duration (12 seconds) to show a deliberate "filling" animation
        val duration: Long = 12000
        
        val progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        progressAnimator.duration = duration
        progressAnimator.interpolator = LinearInterpolator()

        // Update the status badge text based on progress for a "Dev Console" feel
        progressAnimator.addUpdateListener { animator ->
            val progress = animator.animatedValue as Int
            when {
                progress < 25 -> tvStatus.text = getString(R.string.splash_status_init)
                progress < 50 -> tvStatus.text = getString(R.string.splash_status_sync)
                progress < 75 -> tvStatus.text = getString(R.string.splash_status_config)
                else -> tvStatus.text = getString(R.string.splash_status_prep)
            }
        }
        
        progressAnimator.addListener(object : android.animation.AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator) {
                // Check if user is already signed in
                val intent = if (auth.currentUser != null) {
                    Intent(this@SplashActivity, MainActivity::class.java)
                } else {
                    Intent(this@SplashActivity, LoginActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        })

        progressAnimator.start()
    }
}
