package com.sciencewolf.szakdolgozat.components.settingscomponent

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sciencewolf.szakdolgozat.R

open class ContentComponent {

    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        icon: Int,
        text: String,
        url: String
    ) {
        val context = LocalContext.current
        val intent = remember {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        }

        Row (
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "$text icon",
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                    context.startActivity(intent)
                }, contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = text,
                        Modifier.background(Color.Transparent),
                        color = Color.White
                    )
                }

                Icon(
                    painter = painterResource(id = R.drawable.icons8_more_than_30),
                    contentDescription = "arrow right icon"
                )
            }
        }
        Divider(modifier = Modifier.padding(4.dp))
    }
}