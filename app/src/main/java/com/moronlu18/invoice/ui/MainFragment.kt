package com.moronlu18.invoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.signup.utils.Locator
import com.moronlu18.invoice.databinding.FragmentMainBinding
import java.util.Locale

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.btCustomerCreation.setOnClickListener {
            //findNavController().navigate()
           findNavController().navigate(R.id.action_mainFragment_to_customerCreationFragment)
        }*/
        /*binding.btCustomerDetails.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_customerDetailFragment)
        }*/
        binding.btCustomerList.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_nav_graph_customer)
        }
        /*binding.btInvoiceCreation.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_invoiceCreationFragment)
        }*/
        /*binding.btInvoiceDetails.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_invoicedetailFragment)
        }*/
        binding.btInvoiceList.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_nav_graph_invoice)
            //findNavController().navigate(R.id.action_mainFragment_to_featureAccountSignUp)
        }
        /*binding.btItemCreation.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_itemcreationFragment)
        }*/
        /* binding.btItemDetail.setOnClickListener {
             //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_itemdetailFragment)
         }*/
        binding.btItemList.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_nav_graph_item)
        }
        /* binding.btTaskCreation.setOnClickListener {
             //findNavController().navigate()
             findNavController().navigate(R.id.action_mainFragment_to_taskcreationFragment)
         }*/
        /*binding.btTaskDetails.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_taskdetailFragment)
        }*/
        binding.btTaskList.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_nav_graph_task)
        }
        initTheme()
        initLanguaje()
    }
    private fun initTheme() {
        var value = Locator.invoicePreferencesRepository.getTheme()
        if (value == "true") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
    private fun initLanguaje() {
        var value = Locator.invoicePreferencesRepository.getLanguaje()
        var languageCode = Locale.getDefault().language

        if (value == "true") {
            languageCode = "en"
        } // si es false, se queda con el valor por defecto
        println("AAAAAAAAAAAAAAAAAAA--A------ $value")
        println("AAAAAAAAAAAAAAAAAAA--A------ $languageCode")

        val recursos = resources
        val displayMetrics = recursos.displayMetrics
        val configuration = resources.configuration

        configuration.setLocale(Locale(languageCode))
        recursos.updateConfiguration(configuration,displayMetrics)
        configuration.locale = Locale(languageCode)
        resources.updateConfiguration(configuration, displayMetrics)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}