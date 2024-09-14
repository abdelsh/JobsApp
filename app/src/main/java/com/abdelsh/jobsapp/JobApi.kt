package com.abdelsh.jobsapp

import retrofit2.http.GET
import retrofit2.http.Path

interface JobApi {
    @GET("jobs/api")
    suspend fun getVacancies(): List<Vacancy>

    @GET("jobs/api/{id}")
    suspend fun getVacancyDetails(@Path("id") id: String): Vacancy
}