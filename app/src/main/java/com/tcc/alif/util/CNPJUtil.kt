package com.tcc.alif.util

import java.lang.Exception

class CNPJUtil {

    companion object{
        fun myValidateCNPJ(cnpj : String) : Boolean{
            val cnpjClean = cnpj.replace(".","").replace("/","").replace("-","")

            if(cnpjClean.length != 14)
                return false

            try {
                val number = cnpjClean.toLong()
            }catch (e: Exception){
                return false
            }
            val cnpj12 = IntArray(12)
            val cnpj13 = IntArray(13)
            var dvCurrent12 = cnpjClean.substring(12,13).toInt()
            var dvCurrent13= cnpjClean.substring(13,14).toInt()
            val validatorsOne = listOf<Int>(5,4,3,2,9,8,7,6,5,4,3,2)
            val validatorsTwo = listOf<Int>(6,5,4,3,2,9,8,7,6,5,4,3,2)

            validatorsTwo.forEachIndexed{ index, element ->
                cnpj13[index] = cnpjClean.substring(index,index+1).toInt()
            }

            validatorsOne.forEachIndexed{ index, element ->
                cnpj12[index] = cnpjClean.substring(index,index+1).toInt()
            }

            val sumProductOne = IntArray(12)
            val sumProductTwo = IntArray(13)

            for((index,value) in cnpj13.withIndex()){
                sumProductTwo[index] = value * validatorsTwo[index]
            }
            var dvSecondDigit = sumProductTwo.sum() % 11


            if(dvSecondDigit < 2) dvSecondDigit = 0 else dvSecondDigit =  11 - dvSecondDigit

            if(dvCurrent13 != dvSecondDigit){
                return false
            }


            for((index, value) in cnpj12.withIndex()){
                sumProductOne[index] = value * validatorsOne[index]
            }
            var dvFirstDigit = sumProductOne.sum() % 11

            if(dvFirstDigit < 2) dvFirstDigit = 0 else dvFirstDigit =  11 - dvFirstDigit

            if(dvCurrent12 != dvFirstDigit){
                return false
            }

            return true

        }
    }
}