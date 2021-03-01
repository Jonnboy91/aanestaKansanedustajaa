package com.example.aanestakansanedustajaa.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "voting_database")
data class VotingData(
    @PrimaryKey
    val hetekaID: Int,
    @ColumnInfo(name = "voting_record")
    val score: Int
): Parcelable