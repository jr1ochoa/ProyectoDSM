package com.example.veterinary.db.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USERS_TABLE)
        db.execSQL(CREATE_PETS_TABLE)
        db.execSQL(CREATE_APPOINTMENTS_TABLE)

        insertInitialData(db)
    }

    private fun insertInitialData(db: SQLiteDatabase) {
        // Inserta datos en la tabla de usuarios
        insertUser(db, "Diego", "Vasquez", "owner", "diego@udb.com", "12345678")
        insertUser(db, "Juan", "Torres", "doctor", "juan@udb.com", "12345678")
        insertUser(db, "Leo", "Saravia", "admin", "leo@udb.com", "12345678")

        // Inserta datos en la tabla de mascotas
        insertPet(db, 1, "Phillipa")

        // Inserta datos en la tabla de citas
        insertAppointment(db, 1, 1, 2, "2023-01-17", "Consulta general")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Aquí puedes manejar actualizaciones de la base de datos
    }

    companion object {
        private const val DATABASE_NAME = "VeterinaryDatabase.db"
        private const val DATABASE_VERSION = 1
        private const val CREATE_USERS_TABLE = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "last_name TEXT," +
                "type TEXT," +
                "email TEXT," +
                "password TEXT)"
        private const val CREATE_PETS_TABLE = "CREATE TABLE pets (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "name TEXT," +
                "FOREIGN KEY(user_id) REFERENCES users(id))"
        private const val CREATE_APPOINTMENTS_TABLE = "CREATE TABLE appointments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "owner_id INTEGER," +
                "pet_id INTEGER," +
                "doctor_id INTEGER," +
                "date TEXT," +
                "notes TEXT," +
                "FOREIGN KEY(owner_id) REFERENCES users(id)," +
                "FOREIGN KEY(pet_id) REFERENCES pets(id)," +
                "FOREIGN KEY(doctor_id) REFERENCES users(id))"
    }

    fun addUser(name: String, lastName: String, type: String, email: String, password: String): Boolean {
        return try {
            val db = this.writableDatabase
            val contentValues = ContentValues().apply {
                put("name", name)
                put("last_name", lastName)
                put("type", type)
                put("email", email)
                put("password", password)
            }

            val result = db.insert("users", null, contentValues)
            db.close()
            result != -1L
        } catch (e: Exception) {
            false
        }
    }

    private fun insertUser(db: SQLiteDatabase, name: String, lastName: String, type: String, email: String, password: String) {
        val contentValues = ContentValues().apply {
            put("name", name)
            put("last_name", lastName)
            put("type", type)
            put("email", email)
            put("password", password)
        }
        db.insert("users", null, contentValues)
    }

    private fun insertPet(db: SQLiteDatabase, userId: Int, name: String) {
        val contentValues = ContentValues().apply {
            put("user_id", userId)
            put("name", name)
        }
        db.insert("pets", null, contentValues)
    }

    private fun insertAppointment(db: SQLiteDatabase, ownerId: Int, petId: Int, doctorId: Int, date: String, notes: String) {
        val contentValues = ContentValues().apply {
            put("owner_id", ownerId)
            put("pet_id", petId)
            put("doctor_id", doctorId)
            put("date", date)
            put("notes", notes)
        }
        db.insert("appointments", null, contentValues)
    }

    fun checkUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            "users",
            arrayOf("id"),
            "email = ? AND password = ?",
            arrayOf(email, password),
            null,
            null,
            null
        )
        val userExists = cursor.count > 0
        cursor.close()
        db.close()
        return userExists
    }
}

