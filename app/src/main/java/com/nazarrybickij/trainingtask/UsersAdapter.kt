package com.nazarrybickij.trainingtask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nazarrybickij.trainingtask.entities.UserDataEntity
import com.squareup.picasso.Picasso

class UsersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var callback:AdapterCallback? = null
    var listUsers = mutableListOf<UserDataEntity>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    interface AdapterCallback {
        fun onUserClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(position)
    }

    override fun getItemCount() = listUsers.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val avatar = itemView.findViewById<ImageView>(R.id.avatar_imageView)
        private val name = itemView.findViewById<TextView>(R.id.user_name_textView)
        init {
            itemView.setOnClickListener(this)
        }
        fun onBind(position: Int) {
            Picasso.with(itemView.context)
                .load(listUsers[position].avatar)
                .into(avatar)
            name.text = "${listUsers[position].firstName} ${listUsers[position].lastName}"
        }

        override fun onClick(p0: View?) {
            if (callback != null){
                callback?.onUserClick(listUsers[layoutPosition].id)
            }
        }
    }
}