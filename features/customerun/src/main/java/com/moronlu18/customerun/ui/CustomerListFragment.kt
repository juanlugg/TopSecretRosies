package com.moronlu18.customerun.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.InvoiceDavid.Repository.InvoiceRepository
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.repository.CustomerRepository
import com.moronlu18.customerun.R
import com.moronlu18.customerun.adapter.CustomerAdapter
import com.moronlu18.customerun.databinding.FragmentCustomerListBinding
import com.moronlu18.customerun.usecase.CustomerListViewModel
import com.moronlu18.invoice.MainActivity
import com.moronlu18.invoice.ui.utils.Notification
import com.moronlu18.task.repository.TaskRepository

class CustomerListFragment : Fragment(), MenuProvider {
    private var _binding: FragmentCustomerListBinding? = null
    lateinit var adaptercustomer: CustomerAdapter
    private val binding get() = _binding!!

    private val viewModel: CustomerListViewModel by viewModels()
    lateinit var canal: NotificationChannel;
    private val NOTIFICATION_ID = 800
    private val CHANNEL_ID = "modification_customer"

    private fun setUpToolbar() {
        (requireActivity() as? MainActivity)?.toolbar?.apply {
            visibility = View.VISIBLE
        }
        val menuhost: MenuHost = requireActivity()
        menuhost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        viewModel.validate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerListBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        canal = NotificationChannel(
            CHANNEL_ID,
            "Channel Customer",
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = "Customer Modificado"
        }
        setUpToolbar()
        binding.listcustomer.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Inflate the layout for this fragment
        adaptercustomer = CustomerAdapter({ c: Customer, n: Int ->
            var bundle = Bundle().apply {
                putSerializable("customer", c)
            }
            when (n){
                0->{parentFragmentManager.setFragmentResult("key",bundle)
                    findNavController().navigate(R.id.action_customerListFragment_to_customerDetailFragment2)}
                1->{parentFragmentManager.setFragmentResult("key",bundle)
                findNavController().navigate(R.id.action_customerListFragment_to_customerCreationFragment2)
                    Notification.showNotificationWithNavMain(requireContext(),"Alerta de seguridad ","El cliente ${c.getFullName()} esta siendo modificado",canal, CHANNEL_ID,NOTIFICATION_ID)
                }
            }

        }, { i: Int ->
            showDeleteConfirmationDialog(i)
        })
        viewModel.allcustomers.observe(viewLifecycleOwner) {
            it.let { adaptercustomer.submitList(it) }
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_customerListFragment_to_customerCreationFragment2)
        }
        binding.listcustomer.adapter = adaptercustomer
        binding.listcustomer.adapter?.notifyDataSetChanged()
        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                is CustomerListState.noDataError -> {
                    binding.imgNada.visibility = View.VISIBLE
                    binding.listcustomer.visibility = View.GONE
                }

                is CustomerListState.Success -> onSuccess()
            }
        }

        if (adaptercustomer.currentList.size < 1) {
            binding.listcustomer.visibility = View.GONE
            binding.imgNada.visibility = View.VISIBLE
        } else {
            binding.listcustomer.visibility = View.VISIBLE
            binding.imgNada.visibility = View.GONE
        }

        viewModel.allcustomers.observe(viewLifecycleOwner) {
            if(it.isEmpty()){
                binding.imgNada.visibility = View.VISIBLE
                binding.listcustomer.visibility = View.GONE
            }else{
                binding.imgNada.visibility = View.GONE
                binding.listcustomer.visibility = View.VISIBLE
                adaptercustomer.submitList(it)
            }
            it.let { adaptercustomer.submitList(it)
            }
        }
    }

    private fun onSuccess() {
        binding.imgNada.visibility = View.GONE
        binding.listcustomer.visibility = View.VISIBLE
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menuitem, menu)
    }

    private fun showDeleteConfirmationDialog(posicion: Int) {

        val builder = AlertDialog.Builder(requireContext())
        var rtarea = false
        var rinvoice = false
        for (task in TaskRepository.selectAllTaskListRAW()) {
            if (task.customer.id == viewModel.allcustomers.value?.get(posicion)!!.id) {
                rtarea = true
                break
            }
        }
        for (invoice in InvoiceRepository.getInvoiceListRAW()) {
            if (invoice.idCliente == viewModel.allcustomers.value?.get(posicion)!!.id) {
                rinvoice = true
                break
            }
        }
        when {
            rtarea -> {
                builder.setTitle("Cliente referenciado en Task")
                builder.setNegativeButton("Ok", null)
            }

            rinvoice -> {
                builder.setTitle("Cliente referenciado en Invoice")
                builder.setNegativeButton("Ok", null)
            }

            else -> {
                builder.setTitle("¿Deseas eliminar este Cliente?")
                builder.setPositiveButton("Eliminar") { _, _ ->
                    //Implementar eliminar con base de datos
                    CustomerRepository.delete(viewModel.allcustomers.value!![posicion])
                    if (viewModel.allcustomers.value!!.isEmpty()) {
                        adaptercustomer.notifyChanged()
                        binding.listcustomer.visibility = View.GONE
                        binding.imgNada.visibility = View.VISIBLE
                    } else {
                        binding.listcustomer.visibility = View.VISIBLE
                        binding.imgNada.visibility = View.GONE
                    }
                    binding.listcustomer.adapter?.notifyDataSetChanged()
                }
                builder.setNegativeButton("Cancel", null)
            }
        }


        builder.show()
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_refresh -> {
                adaptercustomer.sortId()
                binding.listcustomer.adapter?.notifyDataSetChanged()
                return true
            }

            R.id.action_sort -> {
                adaptercustomer.sortbyName()
                binding.listcustomer.adapter?.notifyDataSetChanged()
                return true
            }

            else -> return false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
