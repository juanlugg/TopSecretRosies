package com.moronlu18.invoice.ui


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.signup.utils.Locator
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.invoice.MainActivity
import com.moronlu18.invoice.adapter.AdaptadorFacturas
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.invoice.usecase.InvoiceListViewModel
import com.moronlu18.invoiceFragment.R
import com.moronlu18.invoiceFragment.databinding.FragmentInvoiceListBinding


class InvoiceListFragment : Fragment(), MenuProvider {
    private var _binding: FragmentInvoiceListBinding? = null
    private val binding
        get() = _binding!!
    lateinit var channel: NotificationChannel;
    lateinit var adapterInvoice: AdaptadorFacturas

    /*fun ViewImage(){
      if (facturas.isEmpty()) {
          binding.rvInvoiceList.visibility = View.GONE
          binding.imgNada.visibility = View.VISIBLE
      } else {
          binding.rvInvoiceList.visibility = View.VISIBLE
          binding.imgNada.visibility = View.GONE
      }
  }*/
    private val viewModel: InvoiceListViewModel by viewModels()

    private val NOTIFICATION_ID = 700
    private val CHANNEL_ID = "delete_chanel"
    private val TAG = "DeleteTAG"


    private fun setUpToolbar() {
        //Modismo aplly de kotlin
        (requireActivity() as? MainActivity)?.toolbar?.apply {
            visibility = View.VISIBLE
        }
        val menuhost: MenuHost = requireActivity()
        menuhost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //ViewImage()
        _binding = FragmentInvoiceListBinding.inflate(inflater, container, false)

        channel = NotificationChannel(
            CHANNEL_ID,
            "Channel Invoice",
            NotificationManager.IMPORTANCE_LOW,
        ).apply {
            description = "Invoice Borrado"
        }

        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        setUpToolbar()

        //adapter.notifyDataSetChanged()
        //binding.rvInvoiceList.scrollToPosition(facturas.size - 1)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpInvoiceRecycler()

        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                is InvoiceListState.noDataError -> {
                    binding.imgNada.visibility = View.VISIBLE
                    binding.rvInvoiceList.visibility = View.GONE
                }

                is InvoiceListState.Success -> onSuccess()
            }
        }

        if (adapterInvoice.currentList.size < 1) {
            binding.rvInvoiceList.visibility = View.GONE
            binding.imgNada.visibility = View.VISIBLE
        } else {
            binding.rvInvoiceList.visibility = View.VISIBLE
            binding.imgNada.visibility = View.GONE
        }

        viewModel.allinvoice.observe(viewLifecycleOwner) {
            if(it.isEmpty()){
                binding.imgNada.visibility = View.VISIBLE
                binding.rvInvoiceList.visibility = View.GONE
            }else{
                binding.imgNada.visibility = View.GONE
                binding.rvInvoiceList.visibility = View.VISIBLE
                adapterInvoice.submitList(it)
            }
            it.let { adapterInvoice.submitList(it)
            }
        }

        binding.fabInvoice.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_invoiceListFragment_to_invoiceCreationFragment)
        }

        //viewModel.validate()
    }

    private fun setUpInvoiceRecycler() {
        adapterInvoice = AdaptadorFacturas(
            { fa: Invoice,  n: Int ->
                var bundle = Bundle().apply {
                    putSerializable("invoice", fa)
                    putBoolean("editar",true)
                }
                if (n == 0) {
                    findNavController().navigate(
                        R.id.action_invoiceListFragment_to_invoiceDetailsFragment,
                        bundle
                    )
                } else if (n == 1) {
                    findNavController().navigate(
                        R.id.action_invoiceListFragment_to_invoiceCreationFragment,
                        bundle
                    )
                }
            },
            { i: Int ->
                showDeleteConfirmationDialog(i)
            }, {
                viewModel.getLineaItem(it)
            }
        )

        with(binding.rvInvoiceList) {
            layoutManager = LinearLayoutManager(requireContext())
            //setHasFixedSize(true) Con list adapter hay que quitar esto
            //El recyckerview es dinamico ya que utilizamos listAdapter y se modifica en el número de elementos
            //Se debe quitar setHasFixedSize(true)
            this.adapter = adapterInvoice
        }
    }

    private fun onSuccess() {
        binding.imgNada.visibility = View.GONE
        binding.rvInvoiceList.visibility = View.VISIBLE
        //Utils.showSnackBar(binding.root, "Lista Creada")

    }

    private fun showDeleteConfirmationDialog(posicion: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("¿Deseas eliminar esta factura?")
        builder.setPositiveButton("Eliminar") { _, _ ->

            showNotification(requireContext(),"Invoice Borrado","Invoice borrado con exito");
            viewModel.removeInvoice(adapterInvoice.currentList[posicion])
            if (adapterInvoice.currentList.size < 1) {
                binding.rvInvoiceList.visibility = View.GONE
                binding.imgNada.visibility = View.VISIBLE
            } else {
                binding.rvInvoiceList.visibility = View.VISIBLE
                binding.imgNada.visibility = View.GONE
            }
            binding.rvInvoiceList.adapter?.notifyDataSetChanged()

        }

        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menulist, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_sort -> {
               adapterInvoice.sortId()
                binding.rvInvoiceList.adapter?.notifyDataSetChanged()
                return true
            }

            R.id.action_refresh -> {
                adapterInvoice.sortIdCliente()
                binding.rvInvoiceList.adapter?.notifyDataSetChanged()
                return true
            }
            else -> false
        }
    }
    private fun showNotification(context: Context, title: String, message: String) {
        println("-----------------------------------")
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)

        val pendingIntent = NavDeepLinkBuilder(requireContext())
            .setComponentName(MainActivity::class.java)
            .setGraph(com.moronlu18.invoice.R.navigation.nav_graph)
            .setDestination(com.moronlu18.invoice.R.id.mainFragment)
            .createPendingIntent()

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    override fun onStart() {
        super.onStart()

        viewModel.getInvoiceList()
        if (Locator.invoicePreferencesRepository.getInvoiceOr() == "No") {
            adapterInvoice.sortIdCliente()
        } else {
            adapterInvoice.sortId()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}