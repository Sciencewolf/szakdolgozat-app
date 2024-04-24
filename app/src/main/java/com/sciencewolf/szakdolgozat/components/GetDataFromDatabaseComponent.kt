package com.sciencewolf.szakdolgozat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sciencewolf.szakdolgozat.R
import com.sciencewolf.szakdolgozat.scheme.DataScheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDataFromDatabaseComponent {
    @Composable
    fun GetAndDisplayDataFromDatabase(
        supabase: SupabaseClient,
        modifier: Modifier = Modifier
    ) {
        var it by remember {
            mutableStateOf<List<DataScheme>>(listOf())
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

        if (loadingText) {
            Text(
                text = "Loading..."
            )
        }
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                it = supabase.from("data")
                    .select(){
                        order("id", Order.DESCENDING)
                        limit(1)
                    }
                    .decodeList<DataScheme>()
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
                Content(
                    text = temp,
                    sign = "°C",
                    imageId = R.drawable.icons8_temperature_100,
                    imageContentDesc = "temperature"
                )
                Spacer(modifier = Modifier)
                Content(
                    text = hum,
                    sign = "%",
                    imageId = R.drawable.icons8_humidity_100,
                    imageContentDesc = "humidity"
                )

            }
        }
    }

    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        text: String,
        sign: String,
        imageId: Int,
        imageContentDesc: String
    ) {
        Row (
            modifier = Modifier.padding(4.dp)
        ){
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "$imageContentDesc icon"
            )
            Spacer(modifier = Modifier)
            Text(
                text.plus(sign),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}