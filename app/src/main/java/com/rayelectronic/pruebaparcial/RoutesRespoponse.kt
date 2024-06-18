package com.rayelectronic.pruebaparcial

import com.google.gson.annotations.SerializedName

data class MenuResponse(
    val SUCCESS: Boolean,
    val DATA: List<Menu>
)

data class Menu(
    val ID_MENU: Int,
    val FECHA: String,
    val nombre: String
)