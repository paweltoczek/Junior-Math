package com.amadev.juniormath.util

import android.content.Context
import com.amadev.juniormath.R
import dagger.hilt.android.qualifiers.ApplicationContext
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock

class MessageTest {

    @Test
    fun getContext() {
    }

    @Test
    fun getFieldCantBeEmpty() {
        val context = mock(Context::class.java)
        val fieldCantBeEmpty = Message.values().firstOrNull()?.fieldCantBeEmpty.toString()
        assertThat(fieldCantBeEmpty, equalTo(context.getString(R.string.fieldCantBeEmpty)))
    }
}