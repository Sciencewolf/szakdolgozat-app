package com.sciencewolf.szakdolgozat.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sciencewolf.szakdolgozat.components.profilecomponents.GetVersionComponent
import io.github.jan.supabase.SupabaseClient

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