/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.network

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://mars.udacity.com/"

val moshi = Moshi.Builder()
        .build()

val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

interface MarsApiService {
    @GET("realestate")
    suspend fun getProperties(@Query("filter") filter: String) : List<MarsProperty>
}

enum class MarsApiFilter(val string: String) {
    SHOW_All("all"),
    SHOW_RENT("rent"),
    SHOW_BUY("buy")
}


val apiService: MarsApiService by lazy { retrofit.create(MarsApiService::class.java) }

