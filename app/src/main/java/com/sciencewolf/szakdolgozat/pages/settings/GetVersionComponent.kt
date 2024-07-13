package com.sciencewolf.szakdolgozat.pages.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
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
import com.sciencewolf.szakdolgozat.utils.VersionScheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetVersionComponent {
    @Composable
    fun GetAndDisplayVersion(supabase: SupabaseClient) {
        var it by remember {
            mutableStateOf<List<VersionScheme>>(listOf())
        }

        var version by remember {
            mutableStateOf("")
        }

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                it = supabase
                    .from("version")
                    .select()
                    .decodeList<VersionScheme>()
            }
        }

        LazyRow (
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.End
        ){
            items(
                it,
                key = {data -> data.id}
            ) { data ->
                version = data.v
                Text(text = "v${version}")
                Divider()
            }
        }
    }
}