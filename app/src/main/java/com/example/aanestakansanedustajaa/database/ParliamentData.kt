package com.example.aanestakansanedustajaa.database

import android.os.Parcelable
import androidx.lifecycle.Transformations.map
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "parliament_member_database")
data class ParliamentData(
    @PrimaryKey
    val hetekaId: Int,
    @ColumnInfo(name = "seat_number")
    val seatNumber: Int,
    @ColumnInfo(name = "last_name")
    val lastname: String,
    @ColumnInfo(name = "first_name")
    val firstname: String,
    @ColumnInfo(name = "party")
    val party: String,
    @ColumnInfo(name = "minister")
    val minister: Boolean,
    @ColumnInfo(name = "picture_url")
    val pictureUrl: String
): Parcelable
