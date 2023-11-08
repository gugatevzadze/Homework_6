//package com.example.homework_6
//
//import android.util.Patterns
//import android.widget.Toast
//
//class UserInputValidations {
//    fun filledInputs(): Boolean {
//        for (input in inputArray) {
//            val inputtedText = input.text.toString()
//            if (inputtedText.isEmpty()) {
//                return false
//            }
//        }
//        return true
//    }
//    //used predefined email validator
//    fun emailValidation(inputtedEmail:String): Boolean {
//        val emailMatcher = Patterns.EMAIL_ADDRESS.matcher(inputtedEmail)
//        if (emailMatcher.matches()) {
//            emailMatcher.toString()
//            return true
//        }
//        return false
//    }
//    fun namevalidation(inputtedFirstName:String, inputtedLastName:String): Boolean {
//        return if(!inputtedFirstName.contains(" ") && !inputtedLastName.contains(" ")) {
//            true
//        }else{
//            Toast.makeText(this, "გთხოვთ შეიყვანოთ სახელი ან/და გვარი სწორი ფორმატით", Toast.LENGTH_SHORT).show().toString()
//            false
//        }
//    }
//
//    fun ageValidation(inputtedAge:String): Boolean {
//        if(!inputtedAge.contains(".") && !inputtedAge.contains("-")){
//            return try {
//                val ageToInt = inputtedAge.toInt()
//                (ageToInt >= 1).toString()
//                true
//            } catch (e: NumberFormatException) {
//                Toast.makeText(this, "Input type is invalid. Try again", Toast.LENGTH_SHORT).show().toString()
//                false
//            }
//        }
//        Toast.makeText(this, "Input type is invalid. Try again", Toast.LENGTH_SHORT).show().toString()
//        return false
//    }
//}