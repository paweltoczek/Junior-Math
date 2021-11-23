package com.amadev.juniormath.util

import android.content.Context
import com.amadev.juniormath.R
import dagger.hilt.android.qualifiers.ApplicationContext
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert
import org.junit.Test

class MessageTest {

    @Test
    fun getContext() {
    }

    @Test
    fun getFieldCantBeEmpty(@ApplicationContext context: Context) {
        val fieldCantBeEmpty = Message.values().firstOrNull()?.fieldCantBeEmpty
        Assert.assertThat(fieldCantBeEmpty, equalTo(context.getString(R.string.fieldCantBeEmpty)))
    }
}