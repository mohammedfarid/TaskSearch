package com.swvl.tasksearch.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object FragmentUtils {

    fun replaceFragment(
        activity: AppCompatActivity,
        container: Int,
        fragment: Fragment,
        addToBackStack: Boolean,
        TAG: String
    ) {
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(container, fragment, TAG)
        if (addToBackStack)
            transaction.addToBackStack(TAG)
        else
            transaction.addToBackStack(null)
        transaction.commit()
    }

    fun addFragment(
        activity: AppCompatActivity,
        container: Int,
        fragment: Fragment,
        addToBackStack: Boolean,
        TAG: String
    ) {
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(container, fragment, TAG)
        if (addToBackStack)
            transaction.addToBackStack(TAG)
        else
            transaction.addToBackStack(null)
        transaction.commit()
    }
}