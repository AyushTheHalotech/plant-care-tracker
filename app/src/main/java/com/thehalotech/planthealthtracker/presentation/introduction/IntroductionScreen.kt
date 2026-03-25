package com.thehalotech.planthealthtracker.presentation.introduction

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.thehalotech.planthealthtracker.R
import com.thehalotech.planthealthtracker.presentation.navigation.Routes
import com.thehalotech.planthealthtracker.presentation.theme.PlantGreen

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun IntroductionScreen(controller: NavController) {

    // Disable system font scaling for this screen to maintain proportions
    val density = LocalDensity.current
    val customDensity = Density(density = density.density, fontScale = 1f)

    CompositionLocalProvider(LocalDensity provides customDensity) {
        BoxWithConstraints(modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    0.0f to Color(0xFF7BF13B),
                    1.0f to Color(0xFFE5FFD8),
                    radius = 600f
                )
            )) {

            Column(modifier = Modifier
                .padding(top = 70.dp, start = 40.dp, end = 40.dp)
                .align(Alignment.TopStart)) {
                Text(text = "Inspired",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF18004F)
                )
                Text(text = "By",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF4A7327)
                )
                Text(text = "Nature",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF659403)
                )
            }

            Image(
                painter = painterResource(R.drawable.plant),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center),
                contentScale = ContentScale.Fit
            )

            Column(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally) {

                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Plant care made simple and smart with water reminders and health tracker",
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    color = Color(0xFF88888F)
                )

                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    modifier = Modifier.fillMaxWidth()
                        .height(60.dp)
                        .padding(start = 24.dp, end = 24.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PlantGreen),
                    onClick = {
                        controller.navigate(Routes.USER_REGISTER)
                    },
                ) {
                    Text(text = "Get Started")
                }

                TextButton(onClick = {
                    controller.navigate(Routes.DASHBOARD)
                }) {
                    Text("Skip →", color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}