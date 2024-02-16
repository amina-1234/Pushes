package com.example.pushes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pushes.presentation.pushes.PushesFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, PushesFragment())
        fragmentTransaction.commit()
    }
}