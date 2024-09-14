package com.abdelsh.jobsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.util.Log

class VacancyViewModel : ViewModel() {
    private val _vacancies = MutableStateFlow<List<Vacancy>>(emptyList())
    val vacancies: StateFlow<List<Vacancy>> = _vacancies

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val jobApi: JobApi = Retrofit.Builder()
        .baseUrl("https://www.unhcrjo.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JobApi::class.java)

    init {
        fetchVacancies()
    }

    private fun fetchVacancies() {
        viewModelScope.launch {
            try {
                val response = jobApi.getVacancies()
                _vacancies.value = response
            } catch (e: Exception) {
                _error.value = "Error fetching vacancies: ${e.message}"
            }
        }
    }

    fun searchVacancies(query: String) {
        viewModelScope.launch {
            try {
                val response = jobApi.getVacancies()
                val filteredVacancies = response.filter {
                    it.title.contains(query, ignoreCase = true) ||
                            it.company.contains(query, ignoreCase = true)
                }
                _vacancies.value = filteredVacancies
            } catch (e: Exception) {
                _error.value = "Error fetching vacancies: ${e.message}"
            }
        }
    }
}