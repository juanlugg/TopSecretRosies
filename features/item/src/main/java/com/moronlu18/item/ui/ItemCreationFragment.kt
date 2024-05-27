package com.moronlu18.item.ui

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.moronlu18.item.entity.ItemId
import com.moronlu18.item.entity.item
import com.moronlu18.item.entity.itemType
import com.moronlu18.item.usecase.ItemViewModel
import com.moronlu18.itemcreation.R
import com.moronlu18.itemcreation.databinding.FragmentItemCreationBinding


class ItemCreationFragment : Fragment() {

    private lateinit var tilEditId: TextInputEditText
    private lateinit var tilEditName: TextInputEditText
    private lateinit var tilEditRate: TextInputEditText
    private lateinit var spinnerItemType: Spinner
    private lateinit var tilEditDescription: TextInputEditText
    private lateinit var chbIsTaxable: CheckBox
    private lateinit var fabAdd: FloatingActionButton

    private lateinit var itemViewModel: ItemViewModel

    private var _binding: FragmentItemCreationBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemViewModel.clearNewItem()
        itemViewModel.clearItemDetails()

        itemViewModel.description.removeObservers(viewLifecycleOwner)
        itemViewModel.itemId.removeObservers(viewLifecycleOwner)

        arguments?.let { args ->
            val itemId = args.getInt("id")
            val itemName = args.getString("name")
            val itemRate = args.getDouble("rate")
            val itemType_ = args.getSerializable("type") as itemType
            val itemDescription = args.getString("description")
            val isTaxable = args.getBoolean("isTaxable")

            itemViewModel.updateItemDetails(itemId, itemName, itemRate, itemType_, itemDescription, isTaxable)

            itemViewModel.description.observe(viewLifecycleOwner) { newDescription ->
                binding.tilEditDescription.setText(newDescription)
            }

            itemViewModel.itemId.observe(viewLifecycleOwner) { newId ->
                binding.tilEditId.setText(newId)
            }


            itemViewModel.itemType.observe(viewLifecycleOwner) { newType ->
                val position = when (newType) {
                    itemType.PRODUCT -> 0
                    itemType.SERVICE -> 1
                    null -> {
                        0
                    }
                }
                binding.spnType.setSelection(position)
            }

        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemCreationBinding.inflate(inflater, container, false)
        val view = binding.root

        tilEditId = view.findViewById(R.id.tilEditId)
        tilEditName = view.findViewById(R.id.tilEditName)
        tilEditRate = view.findViewById(R.id.tilEditRate)
        spinnerItemType = view.findViewById(R.id.spnType)
        tilEditDescription = view.findViewById(R.id.tilEditDescription)
        chbIsTaxable = view.findViewById(R.id.chbIsTaxable)
        fabAdd = view.findViewById(R.id.fabadd)

        val itemTypes = arrayOf("PRODUCT", "SERVICE")

        itemViewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)

        binding.viewModel = itemViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        fabAdd.setOnClickListener {
            val itemName = tilEditName.text.toString()
            val itemRate = tilEditRate.text.toString()
            itemViewModel.isTaxable.value = chbIsTaxable.isChecked
            itemViewModel.validateFields(itemName, itemRate)

            itemViewModel.itemState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is ItemState.NameEmptyError -> {
                        showValidationError(state.message, null)
                    }
                    is ItemState.RateFormatError -> {
                        showValidationError(null, state.message)
                    }
                    else -> {
                        showValidationError(null, null)

                        if (state == null) {
                            if (itemViewModel.isTaxable.value == true) {
                                itemViewModel.applyTaxToRate()
                            } else {
                                itemViewModel.rateWithTax.value = itemRate.toDoubleOrNull() ?: 0.0
                            }

                            val newItem = createItemFromInput()

                            if (itemViewModel.itemId.value.isNullOrEmpty()) {
                                itemViewModel.addItem(newItem)
                                showNotification(newItem)
                                findNavController().popBackStack()
                            } else {
                                itemViewModel.updateItemDao(newItem)
                                findNavController().popBackStack()
                                itemViewModel.clearNewItem()
                            }
                        }
                    }
                }
            }
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerItemType.adapter = adapter


        spinnerItemType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedType = parent?.getItemAtPosition(position).toString()
                showToast("Tipo seleccionado: $selectedType")

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        return view
    }



    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun createItemFromInput(): item {
        val id = tilEditId.text.toString().toIntOrNull() ?: 0
        val name = tilEditName.text.toString()
        val rate = itemViewModel.rateWithTax.value ?: 0.0
        val typeString = spinnerItemType.selectedItem.toString()
        val type = itemType.valueOf(typeString)
        val description = tilEditDescription.text.toString()
        val isTaxable = chbIsTaxable.isChecked



        return item(ItemId(id), name, rate, type, description, isTaxable, 0.02)
    }

    private fun showValidationError(nameError: String?, rateError: String?) {
        if (nameError != null) {
            binding.textInputLayout2.error = nameError
        } else {
            binding.textInputLayout2.error = null
        }

        if (rateError != null) {
            binding.textInputLayout3.error = rateError
        } else {
            binding.textInputLayout3.error = null
        }
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(item: item) {
        createNotificationChannel()

        val notificationBuilder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_add_alert_24)
            .setContentTitle("Artículo añadido")
            .setContentText("Se ha añadido el artículo con ID: ${item.id.value}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireContext())) {
            notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Artículo Añadido"
            val descriptionText = "Notificación para informar cuando se añade un nuevo artículo"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val NOTIFICATION_ID = 1
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}