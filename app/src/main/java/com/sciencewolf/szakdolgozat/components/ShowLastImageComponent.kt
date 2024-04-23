package com.sciencewolf.szakdolgozat.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.LogcatWriter
import co.touchlab.kermit.MessageStringFormatter
import coil.compose.AsyncImage
import com.sciencewolf.szakdolgozat.scheme.ImagesScheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.storage.Bucket
import io.github.jan.supabase.storage.BucketApi
import io.github.jan.supabase.storage.BucketItem
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import io.ktor.util.Identity.decode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.codec.binary.Base64
import java.io.ByteArrayInputStream
import kotlin.time.Duration.Companion.minutes

class ShowLastImageComponent {
    @Composable
    fun ShowLastImage(
        supabaseStorage: SupabaseClient,
        modifier: Modifier = Modifier
    ) {
        var url by remember {
            mutableStateOf("")
        }
        var loading by remember {
            mutableStateOf(true)
        }

        Row (
            modifier = Modifier
                .alpha(if (loading) 1f else 0f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            if (loading) {
                Text(text = "Loading...")
            }
        }

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                url = supabaseStorage
                    .storage
                    .from("images")
                    .publicUrl("last-image.png")
                loading = !loading
            }
        }
        
        Column (
            modifier = Modifier.padding(4.dp)
        ){
            Text(
                text = "Last Image",
                fontSize = 30.sp,
                modifier = Modifier.padding(12.dp)
            )
            Spacer(modifier = Modifier)
            AsyncImage(
                model = url,
                contentDescription = "last image"
            )
        }
    }
}