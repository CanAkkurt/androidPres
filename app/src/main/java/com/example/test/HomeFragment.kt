package com.example.test

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.test.databinding.FragmentHomeBinding
import com.example.test.screens.members.MemberFragmentDirections
import com.example.test.screens.virtual_machines.VirtualMachineListViewModel
import com.example.test.screens.virtual_machines.VirtualMachineListViewModelFactory
import com.example.test.screens.virtual_machines.VirtualMachineListener
import com.example.test.screens.virtual_machines.VirtualMachinesAdapter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import okhttp3.*
import org.json.JSONObject


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: VirtualMachinesAdapter
    private var jsonDataList = mutableListOf<JsonData>()
    private var jsonDataListPie = mutableListOf<JsonData>()

    data class JsonData(val month: String, val value: Double)


    private val vmViewModel: VirtualMachineListViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(
            this,
            VirtualMachineListViewModelFactory(activity.application)
        )[VirtualMachineListViewModel::class.java]
    }


    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.virtualMachineListViewModel = vmViewModel

        // Specify the current activity as the lifecycle owner of the binding. This is used so that
        // the binding can observe LiveData updates
        binding.lifecycleOwner = this
        //action_memberFragment_to_virtualMachineDetailFragment
        adapter = VirtualMachinesAdapter(VirtualMachineListener { virtualMachineID ->
            findNavController().navigate(
                MemberFragmentDirections.actionMemberFragmentToVirtualMachineDetailFragment
                    (
                    virtualMachineID
                )
            )
        })

        val recyclerView = binding.virtualMachineList
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )

        vmViewModel.virtualMachineList.observe(viewLifecycleOwner) {
            println("List got ${it.size}")
            adapter.submitList(it)
        }

        val chipGroup: ChipGroup = binding.chipGroup
// Sample filter options
        val filterOptions = listOf("Active", "Not Active", "Requested", "Processing", "Denied")

        for (option in filterOptions) {

            val chip =
                Chip(ContextThemeWrapper(chipGroup.context, R.style.Theme_MaterialComponents_Light))
            chip.text = option
            chip.isCheckable = true

            // Set a click listener to handle chip selection
            chip.setOnCheckedChangeListener { _, _ ->
                // Call a method to apply filters to your RecyclerView
                applyFilters(chipGroup, adapter)
            }

            chipGroup.addView(chip)
        }
        // End of sample filter options
        // searchbar
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterItems(newText)
                return true
            }
        })
//        val string = vmViewModel.virtualMachineList.value?.get(0)?.fySiekeServer
//        val stringPie = vmViewModel.virtualMachineList.value?.get(1)?.fySiekeServer
//        if (string != null) {
//            getData(string)
//        }
//        if (stringPie != null) {
//            getDataPie(stringPie)
//        }


        makeChart(binding.root, savedInstanceState)
        makePieChart(binding.root)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        jsonDataListPie = mutableListOf()
        jsonDataList = mutableListOf()
        super.onViewCreated(view, savedInstanceState)


    }


    private fun applyFilters(
        chipGroup: ChipGroup,
        adapter: VirtualMachinesAdapter
    ) {
        // Implement your filtering logic here for virtualmachinelist
        // You can call adapter.submitList() here with the filtered list
        val selectedFilters = mutableListOf<String>()

        // Iterate through selected chips in the ChipGroup
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            if (chip.isChecked) {
                selectedFilters.add(chip.text.toString())
            }
        }

        // Filter the data based on the selected chips in virtualmachinelist
        if (selectedFilters.isEmpty()) {
            // If no chips are selected, show the full list
            adapter.submitList(vmViewModel.virtualMachineList.value) // Replace 'yourFullList' with your complete data source
        } else {

            val filteredData = vmViewModel.virtualMachineList.value?.filter { vm ->
                // Apply filtering logic here
                selectedFilters.all { filter ->
                    filter.equals(vm.active, ignoreCase = true) ||
                            filter.equals(vm.state.name, ignoreCase = true)

                }

            }
            adapter.submitList(filteredData)
        }

    }


    private fun filterItems(query: String?) {
        val filteredList = vmViewModel.virtualMachineList.value?.filter { item ->
            item.name.contains(query ?: "", ignoreCase = true) // Adjust filtering logic as needed
        }

        adapter.submitList(filteredList)
    }


    private fun makeChart(view: View, savedInstanceState: Bundle?) {
        val string = vmViewModel.virtualMachineList.value?.get(0)?.fySiekeServer
        if (string != null) {
            getData(string)
        }
        super.onViewCreated(view, savedInstanceState)
//        val response = OkHttpClient().newCall(Request.Builder().url("http://5000/virtualmachines/all").build()).execute()
        val barChart: BarChart = view.findViewById(R.id.chart)

        val months = jsonDataList.map { it.month }
        val value = jsonDataList.map { it.value }

        val dates = months.toTypedArray()
        val counts = value.toTypedArray().map { it.toFloat() }

        // Create a list of BarEntry objects
        val entries = ArrayList<BarEntry>()
        for (i in dates.indices) {
            val count = counts[i]
//            val imageResId = R.drawable.sample_image // Replace with your image resource
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.hogent)
            val barEntry = BarEntry(i.toFloat(), count, bitmap)
            entries.add(barEntry)
        }

        // Create a BarDataSet
        val dataSet = BarDataSet(entries, "Count")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.asList() // Customize bar colors if needed

        // Create a BarData object with the BarDataSet
        val barData = BarData(dataSet)

        // Set the BarData to the chart
        barChart.data = barData

        // Configure the X-axis and Y-axis
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = object : IndexAxisValueFormatter(dates) {
            @Deprecated("Deprecated in Java", ReplaceWith("dates.getOrNull(value.toInt()) ?: \"\""))
            override fun getFormattedValue(value: Float, axis: AxisBase): String {
                return dates.getOrNull(value.toInt()) ?: ""
            }
        }
        val yAxisLeft = barChart.axisLeft
        yAxisLeft.axisMinimum = 0f

        // Optionally, customize other chart properties
        barChart.description.isEnabled = false // Disable chart description
        barChart.legend.isEnabled = false // Disable legend

        // Refresh the chart
        barChart.invalidate()
    }

    private fun makePieChart(view: View) {
        val string = vmViewModel.virtualMachineList.value?.get(1)?.fySiekeServer
        if (string != null) {
            getDataPie(string)
        }
        val pieChart: PieChart = view.findViewById(R.id.Piechart)


        val months = jsonDataListPie.map { it.month }
        val value = jsonDataListPie.map { it.value }
        // Sample data
        val entries = ArrayList<PieEntry>()
        if (value.isNotEmpty()) {
            entries.add(PieEntry(value[0].toFloat(), months[0]))
            entries.add(PieEntry(value[2].toFloat(), months[2]))
            entries.add(PieEntry(value[3].toFloat(), months[3]))
        }
        // Create a PieDataSet
        val dataSet = PieDataSet(entries, "Pie Chart")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.asList() // Customize slice colors if needed

        // Create a PieData object with the PieDataSet
        val pieData = PieData(dataSet)

        // Set the PieData to the chart
        pieChart.data = pieData

        // Optionally, customize other chart properties
        pieChart.description.isEnabled = false // Disable chart description
        pieChart.legend.isEnabled = false // Disable legend

        // Refresh the chart
        pieChart.invalidate()
    }


    private fun getData(string: String) {
        try {
            val jsonObject = JSONObject(string)

            // Extract the 'data' array
            val dataArray = jsonObject.getJSONArray("data")

            // Create a list to store JsonData objects

            // Iterate through the array to access individual objects
            for (i in 0 until dataArray.length()) {
                val dataObject = dataArray.getJSONObject(i)

                // Extract values from each object
                val month = dataObject.getString("month")
                val value = dataObject.getDouble("value")

                // Create a JsonData object and add it to the list
                val jsonData = JsonData(month, value)
                jsonDataList.add(jsonData)
            }

            // Now jsonDataList contains the extracted data as a list of JsonData objects
            // You can return this list or use it as needed
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun getDataPie(string: String) {
        try {
            val jsonObject = JSONObject(string)

            // Extract the 'data' array
            val dataArray = jsonObject.getJSONArray("data")

            // Create a list to store JsonData objects

            // Iterate through the array to access individual objects
            for (i in 0 until dataArray.length()) {
                val dataObject = dataArray.getJSONObject(i)

                // Extract values from each object
                val month = dataObject.getString("month")
                val value = dataObject.getDouble("value")

                // Create a JsonData object and add it to the list
                val jsonData = JsonData(month, value)
                jsonDataListPie.add(jsonData)
            }

            // Now jsonDataList contains the extracted data as a list of JsonData objects
            // You can return this list or use it as needed
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}

// Clear the au
