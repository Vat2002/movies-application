package com.example.mobilecoursework2.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Actor(
    @PrimaryKey(autoGenerate = false) val actorName : String
)