package com.example.imageSearchApp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imageSearchApp.R
import com.example.imageSearchApp.model.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image_list.view.*


/**
 * List adapter to bind data for employee list
 */
class ImageGridLayoutViewAdapter(
        private val context: Context,
        private val userItemClickListener: UserItemClickListener?,
        private var userList: ArrayList<Data>
) : RecyclerView.Adapter<ImageGridLayoutViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_list, parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    fun updateMovieList(newUserList: List<Data>) {
        this.userList = newUserList as ArrayList<Data>
        notifyDataSetChanged()

    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(position)

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            itemView.text_view_user_name.text = userList[position].title

            if (userList[position].link.isNullOrBlank()) {
                Picasso.get().load(R.drawable.ic_followers).placeholder(R.drawable.ic_person).fit()
                        .centerCrop().into(itemView.image_view_user)
            } else {

                if(userList[position].innerImages!=null){
                    Picasso.get().load(userList[position]?.innerImages[0]?.link)
                            .placeholder(R.drawable.ic_person)
                            .fit()
                            .centerCrop()
                            .into(itemView.image_view_user)
                }

            }

            itemView.setOnClickListener { userItemClickListener?.onUserItemClicked(userList[position], position) }
        }


    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    /**
     * Interface to handle click listeners
     */
    interface UserItemClickListener {
        fun onUserItemClicked(userModel: Data, position: Int)
    }


}