package com.sciencewolf.szakdolgozat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sciencewolf.szakdolgozat.components.NavBarComponent
import com.sciencewolf.szakdolgozat.pages.ControlPage
import com.sciencewolf.szakdolgozat.pages.HomePage
import com.sciencewolf.szakdolgozat.pages.ImagesPage
import com.sciencewolf.szakdolgozat.pages.ProfilePage
import com.sciencewolf.szakdolgozat.routing.FOCUS_ON
import com.sciencewolf.szakdolgozat.routing.Routes
import com.sciencewolf.szakdolgozat.ui.theme.SzakdolgozatTheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {
    private val homePage = HomePage()
    private val imagesPage = ImagesPage()
    private val controlPage = ControlPage()
    private val profilePage = ProfilePage()
    private val navBarComponent = NavBarComponent()

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
            SzakdolgozatTheme(darkTheme = true) {
                Column(
                    modifier = Modifier
                ) {
                    RoutingController(
                        supabase = supabase,
                        homePage = homePage,
                        imagesPage = imagesPage,
                        controlPage = controlPage,
                        profilePage = profilePage,
                        navBarComponent = navBarComponent
                    )
                }
            }
        }
    }
}

@Composable
fun RoutingController(
    supabase: SupabaseClient,
    homePage: HomePage,
    imagesPage: ImagesPage,
    controlPage: ControlPage,
    profilePage: ProfilePage,
    navBarComponent: NavBarComponent
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.HOME.route
    ) {
        composable(route = Routes.IMAGES.route) {
            imagesPage.LoadImagesPage(supabase = supabase)
            navBarComponent.NavBar(
                focusOn = FOCUS_ON.IMAGES,
                navController = navController
            )
        }
        composable(route = Routes.HOME.route) {
            homePage.LoadHomePage(supabase = supabase)
            navBarComponent.NavBar(
                focusOn = FOCUS_ON.HOME,
                navController = navController
            )
        }
        composable(route = Routes.CONTROL.route) {
            controlPage.LoadControlPage(supabase = supabase)
            navBarComponent.NavBar(
                focusOn = FOCUS_ON.CONTROL,
                navController = navController
            )
        }
        composable(route = Routes.PROFILE.route) {
            profilePage.LoadProfile()
            navBarComponent.NavBar(
                focusOn = FOCUS_ON.PROFILE,
                navController = navController
            )
        }
    }
}