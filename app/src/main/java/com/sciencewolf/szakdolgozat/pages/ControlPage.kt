package com.sciencewolf.szakdolgozat.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.sciencewolf.szakdolgozat.components.GetVersionComponent
import com.sciencewolf.szakdolgozat.scheme.VersionScheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class ControlPage {
    private val getVersionComponent = GetVersionComponent()
    @Composable
    fun LoadControlPage(
        supabase: SupabaseClient,
        modifier: Modifier = Modifier
    ) {
        getVersionComponent.GetAndDisplayVersion(supabase = supabase)
    }
}