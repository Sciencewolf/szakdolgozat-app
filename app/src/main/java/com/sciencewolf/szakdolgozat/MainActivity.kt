package com.sciencewolf.szakdolgozat

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sciencewolf.szakdolgozat.pages.control.ControlPage
import com.sciencewolf.szakdolgozat.pages.home.HomePage
import com.sciencewolf.szakdolgozat.pages.settings.SettingsPage
import com.sciencewolf.szakdolgozat.utils.FOCUS_ON
import com.sciencewolf.szakdolgozat.utils.Routes
import com.sciencewolf.szakdolgozat.ui.theme.AppTheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {
    private val homePage = HomePage()
    private val controlPage = ControlPage()
    private val settingsPage = SettingsPage()
    private val navBarComponent = NavBarComponent()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        val supabase = createSupabaseClient(
            supabaseUrl = resources.getString(R.string.supabaseUrl),
            supabaseKey = resources.getString(R.string.supabaseKey)

        ) {
            install(Postgrest)
            install(Storage) {
                transferTimeout = 90.seconds
            }
        }

        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = true) {
                Scaffold {innerPadding ->
                    Column (
                        modifier = Modifier.padding(innerPadding)
                    ){
                        RoutingController(
                            supabase = supabase,
                            homePage = homePage,
                            controlPage = controlPage,
                            settingsPage = settingsPage,
                            navBarComponent = navBarComponent
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RoutingController(
    supabase: SupabaseClient,
    homePage: HomePage,
    controlPage: ControlPage,
    settingsPage: SettingsPage,
    navBarComponent: NavBarComponent
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.HOME.route
    ) {
        composable(route = Routes.HOME.route) {
            homePage.LoadHomePage()
            navBarComponent.NavBar(
                focusOn = FOCUS_ON.HOME,
                navController = navController
            )
        }
        composable(route = Routes.CONTROL.route) {
            controlPage.LoadControlPage()
            navBarComponent.NavBar(
                focusOn = FOCUS_ON.CONTROL,
                navController = navController
            )
        }
        composable(route = Routes.SETTINGS.route) {
            settingsPage.LoadSettingsPage(supabase = supabase)
            navBarComponent.NavBar(
                focusOn = FOCUS_ON.SETTINGS,
                navController = navController
            )
        }
    }
}