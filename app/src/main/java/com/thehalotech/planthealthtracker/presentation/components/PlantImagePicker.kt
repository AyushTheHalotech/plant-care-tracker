package com.thehalotech.planthealthtracker.presentation.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.thehalotech.planthealthtracker.R

@Composable
fun PlantImagePicker(
    uri: Uri?,
    onImageSelected: () -> Unit) {

    Box {

        AsyncImage(
            model = uri?: R.drawable.plant_vector,
            contentDescription = "selected image",
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable { onImageSelected() }
        )

        FloatingActionButton(
            onClick = onImageSelected,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Photo,
                contentDescription = "Add Image"
            )
        }

    }

}
