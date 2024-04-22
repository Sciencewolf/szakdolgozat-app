package com.sciencewolf.szakdolgozat.pages

import android.content.pm.ModuleInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sciencewolf.szakdolgozat.schemes.GetDataScheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class ControlPage {
    @Composable
    fun LoadControlPage(
        supabase: SupabaseClient,
        modifier: Modifier = Modifier
    ) {
        var it by remember {
            mutableStateOf<List<GetDataScheme>>(listOf())
        }
        var loading by remember {
            mutableStateOf(true)
        }
        var version by remember {
            mutableStateOf("")
        }

        if (loading) {
            Text(text = "loading...")
        }

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                it = supabase
                    .from("data")
                    .select() {
                        order(column = "id", Order.DESCENDING)
                        limit(1)
                    }.decodeList()
                loading = !loading
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
                version = data.version
                Text(text = version)
                Divider()
            }
        }
    }
}