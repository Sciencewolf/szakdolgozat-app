package com.sciencewolf.szakdolgozat.pages.settings

import LangPref
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.sciencewolf.szakdolgozat.R
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class SettingsPage {
    private val whatsNewComponent = WhatsNewComponent()
    private val shareAppComponent = ShareAppComponent()
    private val copyrightComponent = CopyrightComponent()
    private val showAppVersionComponent = GetVersionComponent()

    @Composable
    fun LoadSettingsPage(
        modifier: Modifier = Modifier,
        langPref: LangPref
    ) {
        var expanded by remember { mutableStateOf(false) }
        val context = LocalContext.current

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row (
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.settings_text),
                    fontSize = 32.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                showAppVersionComponent.GetAndDisplayVersion()
            }
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            Column (
                modifier = Modifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                whatsNewComponent.Content(
                    modifier = Modifier,
                    icon = R.drawable.icons8_present_96,
                    text = stringResource(R.string.whats_new_text),
                    url = stringResource(R.string.releases_url)
                )
                shareAppComponent.Content(
                    modifier = Modifier,
                    icon = R.drawable.icons8_share_96,
                    text = stringResource(R.string.share_app_text)
                )
                Divider(modifier = Modifier.padding(4.dp))
                val languages = listOf("en" to "English", "hu" to "Magyar")

                Column(modifier = Modifier) {
                    Box {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icons8_language_100),
                                contentDescription = "Language icon",
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Text(
                                text = stringResource(R.string.choose_lang_text),
                                modifier = Modifier
                                    .clickable { expanded = true }
                                    .padding(vertical = 16.dp, horizontal = 8.dp)
                            )

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                languages.forEach { (code, name) ->
                                    DropdownMenuItem(
                                        text = { Text(name) },
                                        onClick = {
                                            changeLanguage(context, code, langPref)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                copyrightComponent.Content(modifier = Modifier)
            }
        }
    }

    private fun changeLanguage(context: Context, language: String, languagePreference: LangPref) {
        val locale = LocaleListCompat.forLanguageTags(language)
        AppCompatDelegate.setApplicationLocales(locale)

        CoroutineScope(Dispatchers.IO).launch {
            languagePreference.saveLanguage(language)
        }
    }
}