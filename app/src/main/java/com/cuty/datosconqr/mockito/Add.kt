package com.cuty.datosconqr.mockito

class Add(var validNumber: ValidNumber,var print: Print) {

    fun add(a: Any, b: Any): Int {
        return if (validNumber.check(a) && validNumber.check(b)) {
            a as Int+b as Int
        } else 0
    }
    fun addInt(a:Any?) : Int{
        return validNumber.doubleToInt(a)*2
    }
    fun addPrint(a:Any,b:Any){
        if (validNumber.check(a) && validNumber.check(b)){
            val result = a as Int + b as Int
            print.showMessage(result)
        }else{
            print.showError()
        }
    }
}