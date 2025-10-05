package com.example.a1gworkapp.network
import retrofit2.http.GET


interface ApiService {
    @GET("macros/s/AKfycbwer2pwl-mZUBQs7bpuGqgDKT1fQExchThXWcn6i1xU3Bdpm_9r1YRS8qMhbNb7OkA/exec")
    suspend fun getUsers(): List<UserDto>
}