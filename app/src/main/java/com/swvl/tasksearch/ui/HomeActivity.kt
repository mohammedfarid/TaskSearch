package com.swvl.tasksearch.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swvl.tasksearch.R
import com.swvl.tasksearch.utils.FragmentUtils

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        startFragment()
    }

    private fun startFragment() {
        FragmentUtils.replaceFragment(
            activity = this@HomeActivity,
            TAG = MovieFragment.TAG,
            addToBackStack = true,
            fragment = MovieFragment(),
            container = R.id.fragment_container
        )
    }

    override fun onBackPressed() {
        when (val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)) {
            is MovieFragment ->
                fragment.onBackPressed()
            is DetailFragment ->
                fragment.onBackPressed()
            else ->
                super.onBackPressed()
        }
    }

    fun restartHomeActivity() {
        finish()
        val newIntent = intent
        newIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(newIntent)
    }
}
