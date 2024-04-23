package com.sciencewolf.szakdolgozat.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sciencewolf.szakdolgozat.components.GetDataFromDatabaseComponent
import com.sciencewolf.szakdolgozat.components.NavBarComponent
import com.sciencewolf.szakdolgozat.components.ShowLastImageComponent
import com.sciencewolf.szakdolgozat.components.TopBarComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.Storage

open class HomePage {
    private val getDataFromDatabaseComponent = GetDataFromDatabaseComponent()
    private val showLastImageComponent = ShowLastImageComponent()

    @Composable
    fun LoadHomePage(supabase: SupabaseClient) {
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .padding(4.dp)
        ){
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