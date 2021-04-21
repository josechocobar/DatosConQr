package com.cuty.datosconqr.vo

sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data : T) : Resource<T>()
    data class Failure<out T>(val exception : T) : Resource<T>()
    //famosa sealed class que permitira
}