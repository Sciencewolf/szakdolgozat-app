package com.sciencewolf.szakdolgozat.pages.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sciencewolf.szakdolgozat.R

class GetStarted {
    @Composable
    fun GetTips(modifier: Modifier = Modifier, url: String, imageUrl: String, text: String) {
        val context = LocalContext.current
        val intent = remember {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        }
        Row {
            TextButton(
                modifier = modifier.padding(horizontal = 0.dp),
                onClick = {
                    context.startActivity(intent)
                }) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 0.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier.size(100.dp),
                        model = imageUrl,
                        contentDescription = "$url image"
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    Text(text = text)
                }
            }

            Spacer(modifier = Modifier.size(2.dp))
        }
    }
}