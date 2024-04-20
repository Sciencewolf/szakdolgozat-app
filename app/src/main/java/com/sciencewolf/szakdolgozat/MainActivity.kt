package com.sciencewolf.szakdolgozat

import android.os.Bundle
import android.renderscript.RenderScript.Priority
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import com.sciencewolf.szakdolgozat.ui.theme.SzakdolgozatTheme
import io.github.jan.supabase.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val components = Components()
    private val supabase  = createSupabaseClient(
        supabaseUrl = "https://wwxbtwwiboelmaejmfel.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind3eGJ0d3dpYm9lbG1hZWptZmVsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTM0NjQ1MjgsImV4cCI6MjAyOTA0MDUyOH0.by0XncWfz9yZThVjJGL-M3Hp9nYpmAcubg0dwUu3Jaw"

    ) {
        install(Postgrest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SzakdolgozatTheme {
                Surface {
                    components.getDataFromDatabase(supabase)
                }
            }
        }
    }
}