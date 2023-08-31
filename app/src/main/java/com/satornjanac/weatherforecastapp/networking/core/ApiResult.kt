package com.satornjanac.weatherforecastapp.networking.core

interface ApiResult<T : Any>

class Success<T : Any>(val data: T) : ApiResult<T>
class Error<T : Any>(val code: Int = 1337, val message: String?) : ApiResult<T>