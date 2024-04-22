package com.sciencewolf.szakdolgozat.pages

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import coil.compose.AsyncImage
import com.sciencewolf.szakdolgozat.components.NavBarComponent
import com.sciencewolf.szakdolgozat.schemes.ImagesScheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class ImagesPage {

    @Composable
    fun LoadImagesPage(
        supabase: SupabaseClient,
        modifier: Modifier = Modifier
        ) {
        var it by remember {
            mutableStateOf<List<ImagesScheme>>(listOf())
        }
        var url by remember {
            mutableStateOf("")
        }
        var loading by remember {
            mutableStateOf(true)
        }

        if(loading) {
            Text(text = "Loading...")
        }

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                it = supabase
                    .from("images")
                    .select(columns = Columns.list("id", "url")) {
                        order(column = "id", Order.DESCENDING)
                        limit(2)
                    }
                    .decodeList<ImagesScheme>()
                loading = !loading
            }
        }

        LazyRow (
            modifier = Modifier.fillMaxWidth()
        ){
            items(
                it,
                key = {data -> data.id}
            ) { data ->
                url = data.url
                AsyncImage(
                    model = url,
                    contentDescription = "image icon"
                )
                Spacer(modifier = Modifier)
            }
        }
    }
}