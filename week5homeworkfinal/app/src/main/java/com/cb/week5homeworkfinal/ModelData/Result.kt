package com.cb.week5homeworkfinal.ModelData

sealed class Result<out T>{
    data class Success<out T : Any>(val value: T): Result<T>()
    data class Failure(val e: Exception) : Result<Nothing>()
}

