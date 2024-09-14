package com.abdelsh.jobsapp

import RefugeeJobAppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RefugeeJobAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: VacancyViewModel = viewModel()

                    NavHost(navController = navController, startDestination = "vacancyList") {
                        composable("vacancyList") {
                            VacancyListScreen(
                                viewModel = viewModel,
                                onVacancyClick = { vacancy ->
                                    navController.navigate("vacancyDetails/${vacancy.job_id}")
                                }
                            )
                        }
                        composable("vacancyDetails/{vacancyId}") { backStackEntry ->
                            val vacancyId = backStackEntry.arguments?.getString("vacancyId")
                            val vacancyList = viewModel.vacancies.collectAsState().value
                            val vacancy = vacancyList.find { it.job_id == vacancyId }
                            if (vacancy != null) {
                                VacancyDetailsScreen(
                                    vacancy = vacancy,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}