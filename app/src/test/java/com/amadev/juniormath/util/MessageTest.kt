package com.amadev.juniormath.util

import com.amadev.juniormath.R
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MessageTest {


    @Test
    fun getFieldCantBeEmpty() {
        val field = R.string.fieldCantBeEmpty
        val fieldCantBeEmpty = Message.values().firstOrNull()?.fieldCantBeEmpty.toString()
//        assertThat(fieldCantBeEmpty, equalTo(context.getString(R.string.fieldCantBeEmpty)))
    }
}