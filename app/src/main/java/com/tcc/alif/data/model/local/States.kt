package com.tcc.alif.data.model.local

enum class States {
    AC,
    AL,
    AP,
    AM,
    BA,
    CE,
    ES,
    GO,
    MA,
    MG,
    MT,
    MS,
    PA,
    RO,
    PB,
    PR,
    PE,
    PI,
    RJ,
    RN,
    RS,
    RR,
    SC,
    SE,
    SP,
    TO,
    DF
}

fun getAllStates() =
    States.values()