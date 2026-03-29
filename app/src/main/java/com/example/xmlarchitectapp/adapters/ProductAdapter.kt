package com.example.xmlarchitectapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xmlarchitectapp.R
import com.example.xmlarchitectapp.models.Product

/**
 * Custom RecyclerView Adapter with ViewHolder pattern (F3).
 * Supports search/filter functionality (F5).
 */
class ProductAdapter(
    private var productList: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // Full list kept for filtering (F5)
    private var fullList: List<Product> = ArrayList(productList)

    /**
     * Custom ViewHolder that holds references to item views (F3).
     */
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.iv_product_image)
        val productName: TextView = itemView.findViewById(R.id.tv_product_name)
        val productCategory: TextView = itemView.findViewById(R.id.tv_product_category)
        val productPrice: TextView = itemView.findViewById(R.id.tv_product_price)
        val productRating: TextView = itemView.findViewById(R.id.tv_product_rating)
        val btnViewDetails: Button = itemView.findViewById(R.id.btn_view_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_row, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.productName.text = product.name
        holder.productCategory.text = product.category
        holder.productPrice.text = "$${product.price}"
        holder.productRating.text = "${product.rating} ⭐"

        // Set image from resource name
        val context = holder.itemView.context
        val resId = context.resources.getIdentifier(
            product.imageResName, "drawable", context.packageName
        )
        if (resId != 0) {
            holder.productImage.setImageResource(resId)
        }

        // Click listener passes Product via callback for Bundle passing (F2)
        holder.btnViewDetails.setOnClickListener {
            onItemClick(product)
        }

        // Also allow clicking the whole card
        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount(): Int = productList.size

    /**
     * Filter products by search query (F5).
     * Filters by name and category.
     */
    fun filter(query: String) {
        productList = if (query.isEmpty()) {
            ArrayList(fullList)
        } else {
            fullList.filter { product ->
                product.name.contains(query, ignoreCase = true) ||
                product.category.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    /**
     * Update the full dataset.
     */
    fun updateData(newList: List<Product>) {
        fullList = ArrayList(newList)
        productList = ArrayList(newList)
        notifyDataSetChanged()
    }
}
