package com.example.contacts.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.room.Repository
import com.example.contacts.room.User
import kotlinx.coroutines.launch

class UserViewModel(private val repository: Repository) : ViewModel(),Observable {
    val user = repository.users
    private var isUpdateOrDelete = false
    private lateinit var userToUpdateOrDelete : User

    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate(){
        if (isUpdateOrDelete){
            // Update
            userToUpdateOrDelete.name = inputName.value!!
            userToUpdateOrDelete.email = inputEmail.value!!
            update(userToUpdateOrDelete)
        }else {
            // Insert Functionality
            val name = inputName.value!!
            val email = inputEmail.value!!

            insert(User(0, name, email))
            inputName.value = null
            inputEmail.value = null
        }
    }
     fun clearAllOrDelete(){
         if (isUpdateOrDelete){
             delete(userToUpdateOrDelete)
         }else {
             clearAll()
         }
    }

    private fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }
    private fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }
    private fun update(user: User) = viewModelScope.launch {
        repository.update(user)

        // Resetting the Buttons & Fields
        inputEmail.value = null
        inputName.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }
    private fun delete(user: User) = viewModelScope.launch {
        repository.delete(user)

        // Resetting the Buttons & Fields
        inputEmail.value = null
        inputName.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }
    fun initUpdateAndDelete(user: User){
        // Resetting the Buttons & Fields
        inputEmail.value = user.email
        inputName.value = user.name
        isUpdateOrDelete = true
        userToUpdateOrDelete = user
        saveOrUpdateButtonText.value = "Update"
        clearOrDeleteButtonText.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}