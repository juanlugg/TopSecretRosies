package com.moronlu18.item.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.moronlu18.item.entity.itemType
import com.moronlu18.itemcreation.R

class ItemDetailFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { args ->
            val itemId = args.getInt("id")
            val itemName = args.getString("name")
            val itemRate = args.getDouble("rate")
            val itemType = args.getSerializable("type") as itemType
            val itemDescription = args.getString("description")
            val isTaxable = args.getBoolean("isTaxable")
            
            updateDetail(itemId, itemName, itemRate, itemType, itemDescription, isTaxable)
        }

    }

    private fun updateDetail(
        itemId: Int,
        itemName: String?,
        itemRate: Double,
        itemType: itemType,
        itemDescription: String?,
        isTaxable: Boolean
    ) {
        

        view?.findViewById<TextView>(R.id.tvIdDetail)?.text = itemId.toString()
        view?.findViewById<TextView>(R.id.tvNameDetail)?.text = itemName
        view?.findViewById<TextView>(R.id.tvRateDetail)?.text = String.format("%.2f", itemRate)
        view?.findViewById<TextView>(R.id.tvTypeDetail)?.text = itemType.toString()
        view?.findViewById<TextView>(R.id.tvDescriptionDetail)?.text = itemDescription
        view?.findViewById<TextView>(R.id.tvIstaxableDetail)?.text = isTaxable.toString()

    }

}