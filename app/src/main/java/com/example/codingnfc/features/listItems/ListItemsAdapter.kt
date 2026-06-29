package com.example.codingnfc.features.listItems

import android.content.Intent
import android.graphics.BitmapFactory
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.codingnfc.ItemElement
import com.example.codingnfc.databinding.ItemObjectBinding
import com.example.codingnfc.features.infoItem.InfoItemActivity
import java.lang.Exception


class ListItemsAdapter(private val context: Context, var items: List<ItemElement>): RecyclerView.Adapter<ListItemsAdapter.ViewHolder>() {
    class ViewHolder(val binding : ItemObjectBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemObjectBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val bitmap = BitmapFactory.decodeFile(item.image) //.absolutePath)
        with(holder.binding) {
            itemNametextView.text = item.itemValue[0].itemTags[0].name
            println(item.itemValue[0].itemTags[0].name)

            // TODO add other values (object) found
            itemImageView.setImageBitmap(bitmap)
        }
        holder.itemView.setOnClickListener{
            val intent = Intent(context, InfoItemActivity::class.java)
            intent.putExtra("image", item.image)
            intent.putExtra("name", item.itemValue[0].itemTags[0].name)
            intent.putExtra("confidence", item.itemValue[0].itemTags[0].confidence)
            intent.putExtra("x", item.itemValue[0].itemBoundingBox.x)
            intent.putExtra("y", item.itemValue[0].itemBoundingBox.y)
            intent.putExtra("h", item.itemValue[0].itemBoundingBox.h)
            intent.putExtra("w", item.itemValue[0].itemBoundingBox.w)
            context.startActivity(intent)
        }

    }


}