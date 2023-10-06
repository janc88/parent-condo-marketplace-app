package com.example.mobdeve_mco

import DummyData
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdeve_mco.databinding.FragmentAddListingStep2Binding

class AddListingStep2Fragment : Fragment() {
    private var _binding: FragmentAddListingStep2Binding? = null
    private val binding: FragmentAddListingStep2Binding get() = _binding!!

    private lateinit var rvProperties : RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddListingStep2Binding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvProperties = view.findViewById(R.id.rvProperties)



        rvProperties.setHasFixedSize(true)
        rvProperties.layoutManager = LinearLayoutManager(this.context)


        // TODO: filter listings by the university the user selected in step 1

        val property2Adapter = Property2Adapter(DummyData.propertyList)
        rvProperties.adapter = property2Adapter

        val verticalSpacingHeightInPixels = 55
        val itemDecoration = VerticalSpaceItemDecoration(verticalSpacingHeightInPixels)

        rvProperties.addItemDecoration(itemDecoration)

        property2Adapter.onItemClick = {

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}