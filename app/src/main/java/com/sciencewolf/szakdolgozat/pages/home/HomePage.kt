package com.sciencewolf.szakdolgozat.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sciencewolf.szakdolgozat.R
import com.sciencewolf.szakdolgozat.pages.control.ControlRaspberryPi
import com.sciencewolf.szakdolgozat.utils.Routes
import java.time.LocalDate

open class HomePage {
    private val welcomeBackCardComponent = WelcomeBackCardComponent()
    private val getStarted = GetStarted()
    private val controlRaspberryPi = ControlRaspberryPi()

    private val today = LocalDate.now()

    @Composable
    fun LoadHomePage(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            welcomeBackCardComponent.ShowCard(modifier = Modifier)
            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.icons8_hatching_eggs_100),
                        contentDescription = "info",
                        modifier = Modifier.size(70.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Hatching Status",
                        fontSize = 24.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxSize().padding(0.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                controlRaspberryPi.GetDay()
            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Divider(modifier = Modifier)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icons8_statistics_100),
                    contentDescription = "stats icon"
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = "Statistics",
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f)
                )
            }

            Column {
                LoadData(day=today.toString())
            }

            Row {
                TextButton(
                    onClick = {
                        navController.navigate(Routes.DATA.route)
                    }
                ) {
                    Text(text = "See all data")
                }
            }

            Divider(modifier = Modifier)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.icons8_tips_100),
                    contentDescription = stringResource(R.string.tips_text),
                    modifier = Modifier.size(70.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(R.string.tips_text),
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f)
                )
            }

            Column (
                modifier = Modifier.fillMaxSize().padding(0.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                getStarted.GetTips(
                    modifier = Modifier,
                    url = stringResource(R.string.hatching_tips_1_url),
                    imageUrl = stringResource(R.string.hatching_tips_1_image_url),
                    text = stringResource(R.string.hatching_tips_1)
                )
                getStarted.GetTips(
                    modifier = Modifier,
                    url = stringResource(R.string.hatching_tips_2_url),
                    imageUrl = stringResource(R.string.hatching_tips_2_image_url),
                    text = stringResource(R.string.hatching_tips_2)
                )
                getStarted.GetTips(
                    modifier = Modifier,
                    url = stringResource(R.string.hatching_tips_3_url),
                    imageUrl = stringResource(R.string.hatching_tips_3_image_url),
                    text = stringResource(R.string.hatching_tips_3)
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 30.dp))
        }
    }
}