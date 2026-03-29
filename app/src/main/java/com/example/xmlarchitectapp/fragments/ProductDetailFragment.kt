package com.example.xmlarchitectapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.xmlarchitectapp.R
import com.example.xmlarchitectapp.models.Product

/**
 * ProductDetailFragment - Shows detailed product info (F2).
 * Receives a Product object via Bundle arguments.
 * Demonstrates Fragment transaction from ProductListFragment (F4).
 */
class ProductDetailFragment : Fragment() {

    private var product: Product? = null

    companion object {
        private const val ARG_PRODUCT = "product"

        /**
         * F2: Factory method that passes a custom object (Product) via Bundle.
         * Product is Serializable, so it can be put into a Bundle.
         */
        fun newInstance(product: Product): ProductDetailFragment {
            val fragment = ProductDetailFragment()
            val bundle = Bundle().apply {
                // F2: Pass custom object via Bundle using putSerializable
                putSerializable(ARG_PRODUCT, product)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // F2: Retrieve Product object from Bundle
        arguments?.let {
            @Suppress("DEPRECATION")
            product = it.getSerializable(ARG_PRODUCT) as? Product
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find views
        val ivProduct = view.findViewById<ImageView>(R.id.iv_detail_product)
        val tvName = view.findViewById<TextView>(R.id.tv_detail_name)
        val tvCategory = view.findViewById<TextView>(R.id.tv_detail_category)
        val tvPrice = view.findViewById<TextView>(R.id.tv_detail_price)
        val tvRating = view.findViewById<TextView>(R.id.tv_detail_rating)
        val tvDescription = view.findViewById<TextView>(R.id.tv_detail_description)
        val btnAddToCart = view.findViewById<Button>(R.id.btn_add_to_cart)
        val btnBack = view.findViewById<Button>(R.id.btn_back)

        // F2: Display data received from Bundle
        product?.let { p ->
            tvName.text = p.name
            tvCategory.text = p.category
            tvPrice.text = "$${p.price}"
            tvRating.text = "${p.rating} ⭐"
            tvDescription.text = p.description

            // Set image
            val resId = requireContext().resources.getIdentifier(
                p.imageResName, "drawable", requireContext().packageName
            )
            if (resId != 0) {
                ivProduct.setImageResource(resId)
            }
        }

        btnAddToCart.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "${product?.name} added to cart!",
                Toast.LENGTH_SHORT
            ).show()
        }

        // F4: Navigate back using fragment back stack
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
