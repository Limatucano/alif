package com.tcc.alif.data.util

import com.tcc.alif.data.model.Company
import com.tcc.alif.data.model.CompanyResponse

fun CompanyResponse.toCompany() : Company =
    Company(
        idCompany = this.idCompany,
        category = this.category,
        tradeName = this.tradeName,
        ownerName = this.ownerName,
        telephone = this.telephone,
        street = this.street,
        district = this.district,
        numberHouse = this.numberHouse,
        city = this.city,
        zipCode = this.zipCode,
        state = this.state,
        addressContinued = this.addressContinued,
        cnpj = this.cnpj,
        uuid = this.uuid
    )