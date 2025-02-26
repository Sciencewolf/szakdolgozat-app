package com.sciencewolf.szakdolgozat

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleHelper {
    fun setLocale(context: Context, lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.createConfigurationContext(config)

        // Elmentjük a beállítást SharedPreferences-ben
        saveLanguagePreference(context, lang)
    }

    fun saveLanguagePreference(context: Context, lang: String) {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit().putString("app_lang", lang).apply()
    }

    fun loadLanguagePreference(context: Context): String {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return prefs.getString("app_lang", Locale.getDefault().language) ?: "en"
    }
}