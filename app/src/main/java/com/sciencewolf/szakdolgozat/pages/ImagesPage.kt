package com.sciencewolf.szakdolgozat.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.T
import coil.compose.AsyncImage
import com.sciencewolf.szakdolgozat.components.GetImagesFromBucketComponent
import com.sciencewolf.szakdolgozat.scheme.ImagesScheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.storage.BucketItem
import io.github.jan.supabase.storage.SignedUrl
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.minutes

open class ImagesPage {
    private val getImagesFromBucketComponent = GetImagesFromBucketComponent()

    @Composable
    fun LoadImagesPage(supabase: SupabaseClient) {
        getImagesFromBucketComponent.GetImagesAndDisplayFromBucket(supabase = supabase)
    }

}