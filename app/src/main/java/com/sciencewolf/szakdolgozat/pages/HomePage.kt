package com.sciencewolf.szakdolgozat.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sciencewolf.szakdolgozat.components.homecomponents.GetDataFromDatabaseComponent
import com.sciencewolf.szakdolgozat.components.homecomponents.ShowLastImageComponent
import io.github.jan.supabase.SupabaseClient

open class HomePage {
    private val getDataFromDatabaseComponent = GetDataFromDatabaseComponent()
    private val showLastImageComponent = ShowLastImageComponent()

    @Composable
    fun LoadHomePage(supabase: SupabaseClient) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(4.dp)
        ) {
            Row {
                Text(
                    text = "Welcome Back!",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(modifier = Modifier)
            getDataFromDatabaseComponent.GetAndDisplayDataFromDatabase(supabase = supabase)
            Spacer(modifier = Modifier)
            showLastImageComponent.ShowLastImage(supabaseStorage = supabase)
        }
    }
}