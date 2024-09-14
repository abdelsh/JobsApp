package com.abdelsh.jobsapp

import RefugeeJobAppTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyListScreen(
    viewModel: VacancyViewModel,
    onVacancyClick: (Vacancy) -> Unit
) {
    val vacancies by viewModel.vacancies.collectAsState()
    val error by viewModel.error.collectAsState()
    var searchText by remember { mutableStateOf("") }

    val filteredVacancies = vacancies.filter {
        it.title.contains(searchText, ignoreCase = true) ||
                it.company.contains(searchText, ignoreCase = true)
    }

    if (error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = error!!, color = MaterialTheme.colorScheme.error)
        }
    } else {
        Column {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            placeholder = { Text("Search...") },
                            modifier = Modifier
                                .weight(1f) // Takes up available space
                                .clip(RoundedCornerShape(10.dp)),
                            colors = TextFieldDefaults.colors (
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                errorTextColor = MaterialTheme.colorScheme.error,

                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                errorContainerColor = MaterialTheme.colorScheme.error,

                                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                errorPlaceholderColor = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),

                                cursorColor = MaterialTheme.colorScheme.onSurface,
                                errorCursorColor = MaterialTheme.colorScheme.error
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // Adds space between TextField and Button
                        Button(onClick = { viewModel.searchVacancies(searchText) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )) {
                            Text("Search")
                        }
                    }
                }
            )
            LazyColumn {
                items(filteredVacancies) { vacancy ->
                    VacancyItem(vacancy = vacancy, onVacancyClick = onVacancyClick)
                }
            }
        }
    }
}


@Composable
fun VacancyItem(vacancy: Vacancy, onVacancyClick: (Vacancy) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onVacancyClick(vacancy) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface, // Surface color from the theme
            contentColor = MaterialTheme.colorScheme.onSurface // Text color on surface
        )
    ) {

            Column {
                AsyncImage(
                    model = vacancy.image_url,
                    contentDescription = "Vacancy thumbnail",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(5.dp))
//                Text(modifier = Modifier.padding(5.dp, 0.dp), text = "ID: ${vacancy.job_id}", style = MaterialTheme.typography.bodySmall) // Displaying ID
                Text(modifier = Modifier.padding(5.dp, 0.dp),text = vacancy.title, style = MaterialTheme.typography.headlineSmall)
                Text(modifier = Modifier.padding(5.dp, 0.dp),text = vacancy.company, style = MaterialTheme.typography.bodyMedium)
                Text(modifier = Modifier.padding(5.dp, 0.dp),text = vacancy.description, style = MaterialTheme.typography.bodySmall)
                Text(modifier = Modifier.padding(5.dp, 0.dp,0.dp,10.dp),text = "Posted on: ${vacancy.date_posted}", style = MaterialTheme.typography.bodySmall)
            }
    }
}
