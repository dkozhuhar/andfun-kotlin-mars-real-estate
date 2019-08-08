/*
 *  Copyright 2018, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.android.marsrealestate.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.detail.DetailFragment
import com.example.android.marsrealestate.network.MarsProperty

/**
 * The [ViewModel] that is associated with the [DetailFragment].
 */
class DetailViewModel(@Suppress("UNUSED_PARAMETER")val marsProperty: MarsProperty, app: Application) : AndroidViewModel(app) {
    private val isRental = when (marsProperty.type) {
        "rent" -> true
        else -> false
    }
    val marsPropertyType = app.getString(R.string.display_type, when (isRental) {
        true -> app.getString(R.string.type_rent)
        else -> app.getString(R.string.type_sale)
    })
    val marsPropertyPrice = when (isRental) {
        true -> app.getString(R.string.display_price_monthly_rental, marsProperty.price)
        else -> app.getString(R.string.display_price, marsProperty.price)
    }
}
