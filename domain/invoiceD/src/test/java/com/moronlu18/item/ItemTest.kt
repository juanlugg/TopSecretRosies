package com.moronlu18.item

import com.moronlu18.item.entity.ItemId
import com.moronlu18.item.entity.item
import com.moronlu18.item.entity.itemType
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ItemTest {

    @Test
    fun testItem() {
        val id = ItemId(1)
        val name = "pelota"
        val rate = 10.0
        val type = itemType.PRODUCT
        val description = "prueba"
        val isTaxable = true
        val iva = 0.2

        val item = item(id, name, rate, type, description, isTaxable, iva)

        assertEquals(id, item.id)
        assertEquals(name, item.name)
        assertEquals(rate, item.rate, 0.0)
        assertEquals(type, item.type)
        assertEquals(description, item.description)
        assertEquals(isTaxable, item.isTaxable)
        assertEquals(iva, item.Iva, 0.0)
    }

    @Test
    fun testItemComparison() {
        val id1 = ItemId(1)
        val item1 = item(id1, "Item 1", 10.0, itemType.PRODUCT, "Description one", true, 0.2)

        val id2 = ItemId(2)
        val item2 = item(id2, "Item 2", 20.0, itemType.SERVICE, "Description two", false, 0.3)


        assert(item1 < item2)
    }

    @Test
    fun testItemToString() {
        val id = ItemId(1)
        val item = item(id, "Test boli", 10.0, itemType.PRODUCT, "Test azul", true, 0.2)

        val expectedString = "Test boli - 10.0€"
        assertEquals(expectedString, item.toString())
    }

    @Test
    fun testItemEquality() {
        val id1 = ItemId(1)
        val item1 = item(id1, "Item", 10.0, itemType.PRODUCT, "Description", true, 0.2)

        val id2 = ItemId(1)
        val item2 = item(id2, "Item", 10.0, itemType.PRODUCT, "Description", true, 0.2)

        assertEquals(item1, item2)
    }

    @Test
    fun testItemInequality() {
        val id1 = ItemId(1)
        val item1 = item(id1, "Item 1", 10.0, itemType.PRODUCT, "Description 1", true, 0.2)

        val id2 = ItemId(2)
        val item2 = item(id2, "Item 20", 20.0, itemType.SERVICE, "Description 20", false, 0.3)

        assert(item1 != item2)
    }

    @Test
    fun testItemCopy() {
        val id = ItemId(1)
        val originalItem = item(id, "Original Item", 10.0, itemType.PRODUCT, "Original Description", true, 0.2)

        val copiedItem = originalItem.copy(name = "Copied Item", rate = 20.0)


        assertEquals("Original Item", originalItem.name)
        assertEquals(10.0, originalItem.rate, 0.0)


        assertEquals("Copied Item", copiedItem.name)
        assertEquals(20.0, copiedItem.rate, 0.0)
    }
}