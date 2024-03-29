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

package com.example.android.marsrealestate.overview

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.adapterBind
import com.example.android.marsrealestate.databinding.FragmentOverviewBinding
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsProperty

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        Log.i("OverviewFragment", "Viewmodel lazily initiated")
        ViewModelProviders.of(this).get(OverviewViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)

        // Giving the binding access to the OverviewViewModel

        binding.viewModel = viewModel
        val onItemClickListener: (GridViewItemBinding) -> Unit = { gridViewItemBinding ->
            gridViewItemBinding.property?.let {
                gridViewItemBinding.root.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
            }
        }
        binding.photosGrid.adapter = PhotoGridAdapter(onItemClickListener)

        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var filter = MarsApiFilter.SHOW_All
        when (item?.itemId) {
            R.id.show_rent_menu -> filter = MarsApiFilter.SHOW_RENT
            R.id.show_buy_menu -> filter = MarsApiFilter.SHOW_BUY
            else -> filter = MarsApiFilter.SHOW_All
        }
        viewModel.updateFilter(filter)
        return true
    }
}
