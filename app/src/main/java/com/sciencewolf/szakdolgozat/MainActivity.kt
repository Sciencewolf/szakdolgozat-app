package com.sciencewolf.szakdolgozat

import android.os.Bundle
import android.service.controls.Control
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sciencewolf.szakdolgozat.components.ExperimentalComponents
import com.sciencewolf.szakdolgozat.components.GetDataFromDatabaseComponent
import com.sciencewolf.szakdolgozat.components.NavBarComponent
import com.sciencewolf.szakdolgozat.components.TopBarComponent
import com.sciencewolf.szakdolgozat.pages.ControlPage
import com.sciencewolf.szakdolgozat.pages.HomePage
import com.sciencewolf.szakdolgozat.pages.ImagesPage
import com.sciencewolf.szakdolgozat.ui.theme.SzakdolgozatTheme
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class MainActivity : ComponentActivity() {
    private val homePage = HomePage()
    private val imagesPage = ImagesPage()
    private val navBarComponent = NavBarComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        val supabase  = createSupabaseClient(
            supabaseUrl = resources.getString(R.string.supabaseUrl),
            supabaseKey = resources.getString(R.string.supabaseKey)

        ) {
            install(Postgrest)
        }
        super.onCreate(savedInstanceState)
        setContent {
            SzakdolgozatTheme(darkTheme = true) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    imagesPage.LoadImagesPage(supabase = supabase)
                    navBarComponent.NavBar(focusOn = FOCUS_ON.HOME)
                }
            }
        }
    }

//    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    fun preview() {
        SzakdolgozatTheme {
            Column (
                modifier = Modifier
                    .fillMaxSize()
            ){
                // components
            }
        }
    }
}