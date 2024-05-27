package com.moronlu18.item

import com.moronlu18.item.entity.ItemId
import com.moronlu18.item.entity.item
import com.moronlu18.item.entity.itemType
import org.junit.Test

class ClassCastExceptionTest {

    @Test(expected = ClassCastException::class)
    fun `testItemToStringConversion`() {
        val testItem = item(
            ItemId(1),
            "Test Item",
            10.0,
            itemType.PRODUCT,
            "Description",
            true,
            5.0
        )

        val itemAsString: String = testItem as String
    }
}