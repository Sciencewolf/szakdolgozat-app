package com.sciencewolf.szakdolgozat.pages

import androidx.compose.runtime.Composable
import com.sciencewolf.szakdolgozat.components.GetDataFromDatabaseComponent
import com.sciencewolf.szakdolgozat.components.NavBarComponent
import com.sciencewolf.szakdolgozat.components.TopBarComponent
import io.github.jan.supabase.SupabaseClient

open class HomePage {
    private val topBarComponent = TopBarComponent()
    private val getDataFromDatabaseComponent = GetDataFromDatabaseComponent()

    @Composable
    fun LoadHomePage(supabase: SupabaseClient) {
        topBarComponent.TopBar()
        getDataFromDatabaseComponent.GetDataFromDatabase(supabase = supabase)
    }
}