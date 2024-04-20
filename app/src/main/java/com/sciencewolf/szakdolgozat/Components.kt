package com.sciencewolf.szakdolgozat

import android.app.Notification
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
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
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class Components {

    @Composable
    fun getDataFromDatabase(
        supabase: SupabaseClient,
        modifier: Modifier = Modifier
    ) {
        var it by remember {
            mutableStateOf<List<GetData>>(listOf())
        }
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                it = supabase.from("data")
                    .select()
                    .decodeList<GetData>()
            }
        }

        LazyColumn {
            items (
                it,
                key = { data -> data.id },
            ) { data ->
                Text(
                    "${data.temp}°C, ${data.hum}%, ${data.timewhen}",
                    modifier = Modifier.padding(8.dp)
                )
                Divider()
            }
        }
    }
}