package com.example.contacts.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.databinding.CardItemBinding
import com.example.contacts.room.User

class RecyclerViewAdapter(private val usersList: List<User>,
                          private val clickListener : (User)->Unit
                            ) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : CardItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.card_item,parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return usersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(usersList[position],clickListener)
    }


}

class MyViewHolder(private val binding: CardItemBinding)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(user: User, clickListener: (User) -> Unit){
        binding.nameText.text = user.name
        binding.emailText.text = user.email

        binding.listItemLayout.setOnClickListener {
            clickListener(user)
        }
    }
}