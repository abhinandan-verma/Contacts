package com.example.contacts.room

class Repository(private val dao: UserDao) {

    val users = dao.getAllUserInDB()

    suspend fun insert(user: User): Long{
        return dao.insertUser(user)
    }
    suspend fun delete(user: User) {
        return dao.deleteUser(user)
    }
    suspend fun update(user: User){
        return dao.updateUser(user)
    }
    suspend fun deleteAll(){
        return dao.deleteAll()
    }
}