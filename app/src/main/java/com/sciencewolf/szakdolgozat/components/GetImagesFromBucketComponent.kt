package com.sciencewolf.szakdolgozat.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import coil.compose.AsyncImage
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.BucketItem
import io.github.jan.supabase.storage.SignedUrl
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.minutes

class GetImagesFromBucketComponent {

    @Composable
    fun GetImagesAndDisplayFromBucket(
        supabase: SupabaseClient,
        modifier: Modifier = Modifier
    ) {
        var it by remember {
            mutableStateOf<List<BucketItem>>(listOf())
        }
        var signedUrls by remember {
            mutableStateOf<List<SignedUrl>>(listOf())
        }
        var names by remember {
            mutableStateOf<List<String>>(listOf())
        }
        var loading by remember {
            mutableStateOf(true)
        }

        if (loading) {
            Text(text = "Loading...")
        }

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                it = supabase
                    .storage
                    .from("images")
                    .list()

                for (i in it) {
                    names += i.name
                }

                // create signed url's
                signedUrls = supabase
                    .storage
                    .from("images")
                    .createSignedUrls(expiresIn = 50.minutes, names)
                loading = !loading
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(signedUrls) {data ->
                AsyncImage(
                    model = data.signedURL,
                    contentDescription = "image"
                )
                Spacer(modifier = Modifier)
            }

        }
    }
}