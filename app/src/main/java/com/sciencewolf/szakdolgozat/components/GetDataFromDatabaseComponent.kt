package com.sciencewolf.szakdolgozat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.sciencewolf.szakdolgozat.schemes.GetDataScheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDataFromDatabaseComponent {
    @Composable
    fun GetDataFromDatabase(
        supabase: SupabaseClient,
        modifier: Modifier = Modifier
    ) {
        var it by remember {
            mutableStateOf<List<GetDataScheme>>(listOf())
        }
        var temp by remember {
            mutableStateOf("")
        }
        var hum by remember {
            mutableStateOf("")
        }
        var loadingText by remember {
            mutableStateOf(true)
        }
        Row(
            modifier = Modifier
                .alpha(if (loadingText) 1f else 0f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (loadingText) {
                Text(
                    text = "Loading..."
                )
                Icon(
                    Icons.Rounded.Info,
                    contentDescription = "Loading icon"
                )
            }
        }
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                it = supabase.from("data")
                    .select()
                    .decodeList<GetDataScheme>()
                loadingText = !loadingText
            }
        }

        LazyColumn {
            items(
                it,
                key = { data -> data.id },
            ) { data ->
                temp = data.temp
                hum = data.hum
                Text(
                    temp,
                    modifier = Modifier.padding(8.dp)
                )
                Divider()
                Text(
                    hum,
                    modifier = Modifier.padding(8.dp)
                )
                Divider()
            }
        }
    }
}