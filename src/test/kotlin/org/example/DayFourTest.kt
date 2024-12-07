package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class DayFourTest {
    @Test
    fun `indexed chars equal correctly`() {
        val ic1 = IndexedChar(0, 1, 'A')
        val ic2 = IndexedChar(0, 1, 'A')
        val ic3 = IndexedChar(1, 0, 'A')
        val ic4 = IndexedChar(0, 1, 'B')
        assertEquals(ic1, ic2)
        assertNotEquals(ic1, ic3)
        assertNotEquals(ic3, ic4)
    }

    @Test
    fun `indexed char arrays equal correctly`() {
        val ic1 = IndexedChar(0, 1, 'A')
        val ic2 = IndexedChar(0, 1, 'A')
        val list1 = arrayListOf(ic1, ic2)
        val list2 = arrayListOf(ic1, ic2)
        assertEquals(list1, list2)
        assertEquals(list1.hashCode(), list2.hashCode())
    }

    @Test
    fun `indexed char arrays not equal correctly`() {
        val ic1 = IndexedChar(0, 1, 'A')
        val ic2 = IndexedChar(0, 1, 'B')
        val list1 = arrayListOf(ic1, ic2)
        val list2 = arrayListOf(ic2, ic1)
        assertNotEquals(list1, list2)
        assertNotEquals(list1.hashCode(), list2.hashCode())
    }
}