package com.example.test.screens.virtual_machines

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.test.R
import com.example.test.databinding.FragmentVirtualMachineDetailBinding
import com.example.test.domain.VirtualMachine
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF


/**
 * Virtual machine detail fragment
 *
 * @constructor Create empty Virtual machine detail fragment
 */
class VirtualMachineDetailFragment : Fragment() {


    //binding
    private lateinit var binding: FragmentVirtualMachineDetailBinding
    private lateinit var myVM: VirtualMachine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = VirtualMachineDetailFragmentArgs.fromBundle(requireArguments())

        val viewModel: VirtualMachineViewModel by lazy {
            val activity = requireNotNull(this.activity)
            ViewModelProvider(
                this,
                VirtualMachineViewModelFactory(activity.application, args.vmId)
            ).get(
                VirtualMachineViewModel::class.java
            )
        }

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_virtual_machine_detail,
            container,
            false
        )

// Set the viewmodel for databinding - this allows the bound layout access to all of the
        // data in the VieWModel

        binding.virtualMachineViewModel = viewModel

        //viewModel


        // Specify the current activity as the lifecycle owner of the binding. This is used so that
        // the binding can observe LiveData updates
        binding.lifecycleOwner = this


        viewModel.virtualMachine.observe(viewLifecycleOwner) { data ->
            val chartMem: PieChart = binding.chartMemory
            val chartCpu: PieChart = binding.chartCpu
            val chartOpslag: PieChart = binding.chartStorage


            myVM = data
            val entriesMem: ArrayList<PieEntry> = ArrayList()
            entriesMem.add(PieEntry(myVM.memoryAmount.toFloat() - myVM.memoryAmountInUse))
            entriesMem.add(PieEntry(myVM.memoryAmountInUse.toFloat()))
            chartMem.description.text = "totaal VRam " + myVM.memoryAmount.toString() + " Gb"
            setupChart(entriesMem, chartMem, "Ram Gebruik")


            val entriesCpu: ArrayList<PieEntry> = ArrayList()
            entriesCpu.add(PieEntry(myVM.vcpUAmount.toFloat() - myVM.vcpUAmountInUse))
            entriesCpu.add(PieEntry(myVM.vcpUAmountInUse.toFloat()))
            chartCpu.description.text = "totaal Vcpu " + myVM.vcpUAmount.toString()
            setupChart(entriesCpu, chartCpu, "Cpu gebruik")

            val entriesOpslag: ArrayList<PieEntry> = ArrayList()
            entriesOpslag.add(PieEntry(myVM.storageAmount.toFloat() - myVM.storageAmountInUse))
            entriesOpslag.add(PieEntry(myVM.storageAmountInUse.toFloat()))
            chartOpslag.description.text = "totaal opslag " + myVM.storageAmount.toString() + "Gb"
            setupChart(entriesOpslag, chartOpslag, "Opslag gebruik")


        }


        //some manual binding stuff
        //============================================
//        var backupText = ""
//        backupText += when(myVM.backupFrequency){
//            0 -> "none"
//            1 -> "daily"
//            7 -> "weekly"
//            14 -> "bi-weekly"
//            30 -> "monthly"
//            else -> "every ${myVM.backupFrequency} days"
//        }
//        binding.textviewBackup.text = backupText

//        if(myVM.beginDate <= LocalDate.now() && //startDate is in past
//            myVM.endDate > LocalDate.now()&& //endDate is in future
//            myVM.beginDate < myVM.endDate//startdate is before enddate
//        ){
//            binding.textviewState.text =  "actief"
//        }
//        else{
//            binding.textviewState.text = "inactief"
//        }
        //============================================

        binding.lifecycleOwner = this

        return binding.root
    }

    private fun setupChart(data: List<PieEntry>, chart: PieChart, text: String) {


        val dataSet = PieDataSet(data, text)

        dataSet.setDrawIcons(false)
        dataSet.setSliceSpace(3f)
        dataSet.setIconsOffset(MPPointF(0F, 40F))
        dataSet.setSelectionShift(5f)
        val colors: ArrayList<Int> = ArrayList()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)

        data.setValueTextColor(Color.BLACK)
        dataSet.valueTextColor = Color.BLACK
//        data.setValueTypeface(tfLight)
        chart.data = data
        // undo all highlights
        // undo all highlights
        chart.highlightValues(null)
        chart.invalidate()
        chart.animateXY(750, 750)

    }


}