package com.moronlu18.item

import com.google.common.truth.Truth
import com.moronlu18.item.entity.ItemId
import org.junit.Assert
import org.junit.Test

class ItemIdTest {

    @Test
    fun `A es igual que B`(){
        val a = ItemId(1)
        val b = ItemId(1)
        Truth.assertThat(a).isEqualTo(b)

    }


    @Test
    fun `A es diferente que B`(){
        val a = ItemId(1)
        val b = ItemId(2)
        Truth.assertThat(a).isNotEqualTo(b)

    }

}