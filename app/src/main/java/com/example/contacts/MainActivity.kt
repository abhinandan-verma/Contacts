package com.example.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.databinding.ActivityMainBinding
import com.example.contacts.room.Repository
import com.example.contacts.room.User
import com.example.contacts.room.UserDatabase
import com.example.contacts.ui.RecyclerViewAdapter
import com.example.contacts.viewmodel.UserViewModel
import com.example.contacts.viewmodel.UserViewModelFactory

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        // Room
        val dao = UserDatabase.getInstance(application).userDao
        val repository = Repository(dao)
        val factory = UserViewModelFactory(repository)

        viewModel = ViewModelProvider(this,factory)[UserViewModel::class.java]
        binding.myViewModel = viewModel

        binding.lifecycleOwner = this
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recycleview.layoutManager = LinearLayoutManager(this)
        displayUserList()
    }

    private fun displayUserList() {
        viewModel.user.observe(this, Observer {
            binding.recycleview.adapter =
                RecyclerViewAdapter(it) { selectedItem: User -> listItemClicked(selectedItem) }
        })
    }

    private fun listItemClicked(selectedItem: User) {
        Toast.makeText(this,"Selected Item: ${selectedItem.name}",Toast.LENGTH_LONG).show()

        viewModel.initUpdateAndDelete(selectedItem)
    }
}