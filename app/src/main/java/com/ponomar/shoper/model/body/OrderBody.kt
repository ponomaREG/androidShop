package com.ponomar.shoper.model.body

import com.ponomar.shoper.model.entities.Address
import com.ponomar.shoper.model.entities.Cart

data class OrderBody(
        override val token: String,
        val address: Address,
        val products:List<Cart>
):BaseToken()
