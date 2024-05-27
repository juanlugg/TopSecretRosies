package com.moronlu18.item.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.moronlu18.item.entity.item
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemDao {

    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert (item: item) : Long

    @Update
    fun update (item: item)

    @Query("SELECT * FROM item")
    fun selectAll(): Flow<List<item>>
    @Query("SELECT * FROM item")
    fun selectAllRAW(): List<item>

    @Delete
    fun delete(item: item)


}