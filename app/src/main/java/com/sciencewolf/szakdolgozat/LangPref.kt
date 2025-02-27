import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore(name = "settings")

class LangPref(private val context: Context) {

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("selected_language")
    }

    // ✅ Save selected language asynchronously
    suspend fun saveLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }

    // ✅ Load language asynchronously
    val selectedLanguage = context.dataStore.data
        .map { preferences -> preferences[LANGUAGE_KEY] }

    // ✅ Load language synchronously (Blocking)
    fun getSavedLanguageBlocking(): String? {
        return runBlocking {
            context.dataStore.data.first()[LANGUAGE_KEY]
        }
    }
}
