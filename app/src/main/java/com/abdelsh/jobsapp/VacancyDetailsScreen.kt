package com.abdelsh.jobsapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyDetailsScreen(vacancy: Vacancy, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text(text = "Job ID: ${vacancy.job_id}", style = MaterialTheme.typography.bodyMedium) },
            navigationIcon = {
                IconButton(onClick = { onBackClick() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }

        )

        AsyncImage(
            model = vacancy.image_url,
            contentDescription = "Vacancy image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = vacancy.title, style = MaterialTheme.typography.headlineMedium)
        Text(text = vacancy.company, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Location: ${vacancy.location}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Salary: ${vacancy.salary}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Overview:", style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp))
        Text(text = vacancy.description, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Description:", style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp))
        Text(text = vacancy.long_description, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Posted on: ${vacancy.date_posted}", style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary))

    }
}
