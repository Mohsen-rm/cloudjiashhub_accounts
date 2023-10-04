// CustomAdapter.kt
package com.cloudjiashhub.accounts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cloudjiashhub.accounts.R
import com.cloudjiashhub.accounts.model.ItemsViewModel
import com.cloudjiashhub.accounts.ui.ActivityViewAccount

class CustomAdapter(private val context: Context, private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // Create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // Bind the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]

        holder.imageView.setImageResource(item.image)
        holder.textView.text = item.title

        // Set an item click listener
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ActivityViewAccount::class.java)
            intent.putExtra("data", mList[position])
            context.startActivity(intent);
        }

        holder.itemView.setOnLongClickListener {
            Toast.makeText(context,"Long", Toast.LENGTH_LONG).show()
            true
        }
    }

    // Return the number of items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}
