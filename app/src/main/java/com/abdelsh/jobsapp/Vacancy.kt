package com.abdelsh.jobsapp

data class Vacancy(
    val job_id: String,
    val title: String,
    val company: String,
    val description: String,
    val long_description: String,
    val location: String,
    val salary: String,
    val date_posted: String,
    val image_url: String
)