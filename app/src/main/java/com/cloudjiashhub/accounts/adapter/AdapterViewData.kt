package com.cloudjiashhub.accounts.adapter

import android.content.ClipData
import android.content.ClipboardManager
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
import com.cloudjiashhub.accounts.model.ItemsDataView
import com.cloudjiashhub.accounts.model.ItemsViewModel
import com.cloudjiashhub.accounts.ui.ActivityViewAccount

class AdapterViewData (private val context: Context, private val mList: List<ItemsDataView>) : RecyclerView.Adapter<AdapterViewData.ViewHolder>() {

    // Create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_data_view, parent, false)

        return ViewHolder(view)
    }

    // Bind the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]

//        holder.imageView.setImageResource(item.image)
        holder.text_title.text = item.title
        holder.text_value.text = item.value

        holder.itemView.setOnLongClickListener {
            // في داخل النشاط أو الخدمة الخاصة بك
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // النص الذي تريد نسخه إلى الحافظة
            val textToCopy = item.value
            // إنشاء محتوى الحافظة
            val clipData = ClipData.newPlainText(item.value, textToCopy)
            // نسخ النص إلى الحافظة
            clipboardManager.setPrimaryClip(clipData)
            // رسالة تأكيد (اختياري)
            Toast.makeText(context, "تم نسخ إلى الحافظة", Toast.LENGTH_SHORT).show()
            true
        }

        holder.image_copy.setOnClickListener {
            // في داخل النشاط أو الخدمة الخاصة بك
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // النص الذي تريد نسخه إلى الحافظة
            val textToCopy = item.value
            // إنشاء محتوى الحافظة
            val clipData = ClipData.newPlainText(item.value, textToCopy)
            // نسخ النص إلى الحافظة
            clipboardManager.setPrimaryClip(clipData)
            // رسالة تأكيد (اختياري)
            Toast.makeText(context, "تم نسخ إلى الحافظة", Toast.LENGTH_SHORT).show()
        }

    }

    // Return the number of items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image_copy: ImageView = itemView.findViewById(R.id.image_copy)
        val text_title: TextView = itemView.findViewById(R.id.text_view_title)
        val text_value: TextView = itemView.findViewById(R.id.text_view_value)

    }
}
