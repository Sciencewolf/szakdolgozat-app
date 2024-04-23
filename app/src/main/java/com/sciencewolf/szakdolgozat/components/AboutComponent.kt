package com.sciencewolf.szakdolgozat.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sciencewolf.szakdolgozat.scheme.GetDataScheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class AboutComponent {
    @Composable
    fun About(supabase: SupabaseClient) {
        var data by remember {
            mutableStateOf<List<GetDataScheme>>(listOf())
        }
        var version by remember {
            mutableStateOf("")
        }
        LaunchedEffect(key1 = Unit) {
            withContext(Dispatchers.IO){
                data = supabase.from("data").select().decodeList()
            }
        }
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
//            items(key = {id -> data.id})
//            Text(text = "Version: ".plus("v").plus(version))
        }
    }
}