package com.thehalotech.planthealthtracker.presentation.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.thehalotech.planthealthtracker.presentation.navigation.Routes
import com.thehalotech.planthealthtracker.presentation.theme.LightGreenBackground
import com.thehalotech.planthealthtracker.presentation.theme.PlantGreen

@Composable
fun UserRegistration(viewmodel: UserViewModel, controller: NavController) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(LightGreenBackground),
        contentAlignment = Alignment.Center) {

        MainContent(viewmodel, controller)
    }
}

@Composable
fun MainContent(viewmodel: UserViewModel, controller: NavController) {
    var name by remember { mutableStateOf("Guest") }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Hi Guest \uD83D\uDC4B",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "What Should I call you?")
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedTextField(
                value = name,
                onValueChange = {name = it},
                label = { Text("Name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(50.dp)
            )

            FilledIconButton(
                onClick = {
                    viewmodel.saveUser(name)
                    controller.navigate(Routes.DASHBOARD){
                        popUpTo(Routes.USER_REGISTER){
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = PlantGreen,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next"
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}