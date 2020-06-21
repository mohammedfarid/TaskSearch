package com.swvl.tasksearch.utils

import android.content.Context
import com.swvl.tasksearch.ui.HomeActivity
import com.zeugmasolutions.localehelper.LocaleHelper
import java.util.*

object LanguageUtil {
    private lateinit var language: String

    fun getLanguage(): String {
        language = Locale.getDefault().language
        return language
    }

    fun isArabic(): Boolean {
        return getLanguage() == "ar"
    }

    fun isEnglish(): Boolean {
        return getLanguage() == "en"
    }


    fun setLanguage(mContext: Context, activity: HomeActivity, lang: String) {
        //            (activity as HomeActivity).updateLocale(Locale("ar"))
        LocaleHelper.setLocale(mContext, Locale(lang))
        activity.restartHomeActivity()
    }

}