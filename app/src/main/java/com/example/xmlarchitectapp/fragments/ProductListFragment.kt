package com.example.xmlarchitectapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xmlarchitectapp.R
import com.example.xmlarchitectapp.activities.DashboardActivity
import com.example.xmlarchitectapp.adapters.ProductAdapter
import com.example.xmlarchitectapp.models.Product

/**
 * ProductListFragment - Displays products in RecyclerView (F3) with search/filter (F5).
 * When a product is clicked, passes Product object via Bundle to ProductDetailFragment (F2).
 * Fragment transaction is handled by DashboardActivity (F4).
 */
class ProductListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var etSearch: EditText
    private var userName: String = ""

    companion object {
        private const val ARG_USER_NAME = "user_name"

        /**
         * Factory method using Bundle for data passing.
         */
        fun newInstance(userName: String): ProductListFragment {
            val fragment = ProductListFragment()
            val bundle = Bundle().apply {
                putString(ARG_USER_NAME, userName)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userName = it.getString(ARG_USER_NAME, "Guest")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etSearch = view.findViewById(R.id.et_search)
        recyclerView = view.findViewById(R.id.rv_products)

        setupRecyclerView()
        setupSearch()
    }

    /**
     * F3: Sets up RecyclerView with LinearLayoutManager,
     * custom ProductAdapter and ViewHolder pattern.
     */
    private fun setupRecyclerView() {
        val products = Product.getSampleProducts()

        // F3: Initialize adapter with click callback
        // F2: When item clicked, pass Product via Bundle to DetailFragment
        productAdapter = ProductAdapter(products) { product ->
            // F2: Pass product to detail fragment via Bundle (through DashboardActivity)
            (activity as? DashboardActivity)?.showProductDetail(product)
        }

        // F3: Configure RecyclerView with LinearLayoutManager
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
            setHasFixedSize(true)
        }
    }

    /**
     * F5: Implements search/filter for RecyclerView items.
     * Uses TextWatcher to filter products as user types.
     */
    private fun setupSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // F5: Filter products based on search query
                productAdapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
