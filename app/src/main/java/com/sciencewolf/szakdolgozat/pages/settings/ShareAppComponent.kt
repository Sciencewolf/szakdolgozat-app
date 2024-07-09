package com.sciencewolf.szakdolgozat.pages.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.sciencewolf.szakdolgozat.R

open class ShareAppComponent {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShareApp(
        modifier: Modifier = Modifier,
        icon: Int,
        text: String,
    ) {
        var showBottomSheet by remember { mutableStateOf(false) }
        val bottomSheetState = rememberModalBottomSheetState()
        val clipboardManager = LocalClipboardManager.current

        val textUrl by remember {
            mutableStateOf(
                Uri.parse("https://hippo-immense-plainly.ngrok-free.app")
                    .toString()
            )
        }

        val context = LocalContext.current
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(textUrl)
        )

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
                    showBottomSheet = !showBottomSheet
                }, contentPadding = PaddingValues(0.dp)
                ) {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = text,
                            Modifier.background(Color.Transparent),
                            color = Color.White
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_more_than_30),
                            contentDescription = "arrow right icon"
                        )
                    }
                }
            }
        }
        
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = bottomSheetState,
                dragHandle = { BottomSheetDefaults.DragHandle() }
            ) {
                Column (
                    modifier = Modifier
                        .padding(10.dp)
                        .height(120.dp)
                ) {
                    TextButton(onClick = {
                        clipboardManager.setText(AnnotatedString(textUrl))
                        showBottomSheet = !showBottomSheet
                    }) {
                        Row (
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icons8_link_60),
                                contentDescription = "copy link icon"
                            )
                            Text(
                                text = "Copy Link",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    TextButton(onClick = {
                        context.startActivity(intent)
                        showBottomSheet = !showBottomSheet
                    }) {
                        Row (
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icons8_browser_60),
                                contentDescription = "browser icon"
                            )
                            Text(
                                text = "Open in Browser",
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}