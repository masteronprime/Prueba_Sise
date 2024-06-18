package com.rayelectronic.pruebaparcial

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date


interface ApiService {
    @GET("prueba_php/php_prueb.php")
    suspend fun getRoutes(
        @Query("fecha") fecha: String
    ): Response<MenuResponse>

}