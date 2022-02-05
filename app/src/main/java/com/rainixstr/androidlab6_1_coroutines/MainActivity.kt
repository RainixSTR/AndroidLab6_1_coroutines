package com.rainixstr.androidlab6_1_coroutines

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private var startTime: Long = 0
    private lateinit var textSecondsElapsed: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        startTime = System.currentTimeMillis()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (isActive) {
                    textSecondsElapsed.text = getString(R.string.seconds_elapsed, secondsElapsed++)
                    delay(1000 + startTime - System.currentTimeMillis())
                    startTime += 1000
                    Log.d(
                        "Log Thread",
                        "$secondsElapsed Error sleeping: " +
                                "${System.currentTimeMillis() - startTime}")
                }
            }
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putInt(SECONDS_ELAPSED, secondsElapsed)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            secondsElapsed = getInt(SECONDS_ELAPSED)
        }
    }

    companion object {
        const val SECONDS_ELAPSED = "Seconds elapsed"
    }
}