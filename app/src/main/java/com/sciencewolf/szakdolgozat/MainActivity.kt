package com.sciencewolf.szakdolgozat

import android.media.MediaRouter2.RoutingController
import android.os.Bundle
import android.service.controls.Control
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sciencewolf.szakdolgozat.components.ExperimentalComponents
import com.sciencewolf.szakdolgozat.components.GetDataFromDatabaseComponent
import com.sciencewolf.szakdolgozat.components.NavBarComponent
import com.sciencewolf.szakdolgozat.components.TopBarComponent
import com.sciencewolf.szakdolgozat.pages.ControlPage
import com.sciencewolf.szakdolgozat.pages.HomePage
import com.sciencewolf.szakdolgozat.pages.ImagesPage
import com.sciencewolf.szakdolgozat.ui.theme.SzakdolgozatTheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class MainActivity : ComponentActivity() {
    private val homePage = HomePage()
    private val imagesPage = ImagesPage()
    private val controlPage = ControlPage()
    private val navBarComponent = NavBarComponent()
    private val activePage = FOCUS_ON.HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        val supabase = createSupabaseClient(
            supabaseUrl = resources.getString(R.string.supabaseUrl),
            supabaseKey = resources.getString(R.string.supabaseKey)

        ) {
            install(Postgrest)
        }

        super.onCreate(savedInstanceState)
        setContent {
            SzakdolgozatTheme(darkTheme = true) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    when(activePage) {
                        FOCUS_ON.HOME -> {
                            homePage.LoadHomePage(supabase = supabase)
                            navBarComponent.NavBar(focusOn = FOCUS_ON.HOME)
                        }
                        FOCUS_ON.IMAGES -> {
                            imagesPage.LoadImagesPage(supabase = supabase)
                            navBarComponent.NavBar(focusOn = FOCUS_ON.IMAGES)
                        }
                        FOCUS_ON.CONTROL -> {
                            controlPage.LoadControlPage(supabase = supabase)
                            navBarComponent.NavBar(focusOn = FOCUS_ON.CONTROL)
                        }
                    }
                }
            }
        }
    }
}

// future nav system?
@Composable
fun RoutingController(
    supabase: SupabaseClient,
    homePage: HomePage,
    imagesPage: ImagesPage,
    controlPage: ControlPage,
    navBarComponent: NavBarComponent
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.HOME.toString()
    ) {
        composable(route = Routes.HOME.toString()) {

        }
        composable(route = Routes.IMAGES.toString()) {

        }
        composable(route = Routes.CONTROL.toString()) {

        }
    }
}








