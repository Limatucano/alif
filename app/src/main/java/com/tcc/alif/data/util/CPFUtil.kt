package com.tcc.alif.data.util

class CPFUtil {
    companion object {

        fun myValidateCPF(cpf : String) : Boolean{
            val cpfClean = cpf.replace(".", "").replace("-", "")

            if (cpfClean.isEmpty()) return false

            val numbers = cpfClean.filter { it.isDigit() }.map {
                it.toString().toInt()
            }

            if (numbers.size != 11) return false

            //repeticao
            if (numbers.all { it == numbers[0] }) return false

            //digito 1
            val dv1 = ((0..8).sumOf { (it + 1) * numbers[it] }).rem(11).let {
                if (it >= 10) 0 else it
            }

            val dv2 = ((0..8).sumOf { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
                if (it >= 10) 0 else it
            }

            return numbers[9] == dv1 && numbers[10] == dv2
        }
    }
}
