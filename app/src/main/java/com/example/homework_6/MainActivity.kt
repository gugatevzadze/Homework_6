package com.example.homework_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.homework_6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val activeUserList: MutableList<UserClass> = mutableListOf()
    private val deletedUserList: MutableList<UserClass> = mutableListOf()
    private lateinit var activeUserListCounter: TextView
    private lateinit var deletedUserListCounter: TextView

    private var etEmail: String = ""
    private var etFirstName: String = ""
    private var etLastName: String = ""
    private var etAge: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener {
            addUser()
        }

        binding.rmBtn.setOnClickListener {
            removeUser()
        }

        binding.updateBtn.setOnClickListener {
            when (binding.updateBtn.text) {
                "Update User" -> startUserUpdate()
                "Search With Email" -> searchUserForUpdate()
                "Save Changes" -> saveUserUpdate()
            }
        }
        activeUserListCounter = binding.activeUsers
        deletedUserListCounter = binding.deletedUsers
    }
    //adding user to the list
    private fun addUser() {
        etEmail = binding.emailEdit.text.toString()
        etFirstName = binding.firstNameEdit.text.toString()
        etLastName = binding.lastNameEdit.text.toString()
        etAge = binding.ageEdit.text.toString()

        if (validateInputs(etEmail, etFirstName, etLastName, etAge)) {
            val userObject = UserClass(etEmail, etFirstName, etLastName, etAge)
            if (!activeUserList.contains(userObject)) {
                activeUserList.add(userObject)
                showSuccessMessage("User added successfully")
                updateForLists()
            } else {
                showErrorMessage("User already exists")
            }
        }
    }
    //thought about making the familiar logic for remove function as update functions but gave up
    private fun removeUser() {

        if (validateInputs(etEmail, etFirstName, etLastName, etAge)) {
            val userObjectToRemove = UserClass(etEmail, etFirstName, etLastName, etAge)

            if (activeUserList.contains(userObjectToRemove)) {
                activeUserList.remove(userObjectToRemove)
                deletedUserList.add(userObjectToRemove)
                showSuccessMessage("User deleted successfully")
                updateForLists()
            } else {
                showErrorMessage("User does not exist")
            }
        }
    }
    //prepares layout
    private fun startUserUpdate() {
        hideUserFields()
        clearInputFields()
        binding.updateBtn.text = "Search With Email"
    }
    //checks email
    private fun searchUserForUpdate() {
        val matchingUser = activeUserList.find { it.email == binding.emailEdit.text.toString() }
        if (matchingUser != null) {
            showUserFields()
            binding.userEmail.text = binding.emailEdit.text
            binding.emailEdit.visibility = View.GONE
            binding.updateBtn.text = "Save Changes"
        } else {
            showErrorMessage("User Not Found")
        }
    }
    //updates the inputted properties
    private fun saveUserUpdate() {
        val etEmail = binding.emailEdit.text.toString()
        val etFirstName = binding.firstNameEdit.text.toString()
        val etLastName = binding.lastNameEdit.text.toString()
        val etAge = binding.ageEdit.text.toString()

        if (validateInputs(etEmail, etFirstName, etLastName, etAge)) {
            val matchingUser = activeUserList.find { it.email == etEmail }
            matchingUser?.apply {
                email = etEmail
                firstName = etFirstName
                lastName = etLastName
                age = etAge
            }
            showSuccessMessage("User Successfully Updated")
            resetUI()
        }
    }
    //layout modification function
    //they were grouped together to better work with update functions
    private fun showUserFields() {
        binding.firstNameView.visibility = View.VISIBLE
        binding.lastNameView.visibility = View.VISIBLE
        binding.ageView.visibility = View.VISIBLE
        binding.firstNameEdit.visibility = View.VISIBLE
        binding.lastNameEdit.visibility = View.VISIBLE
        binding.ageEdit.visibility = View.VISIBLE
        binding.userEmail.visibility = View.VISIBLE
    }
    private fun hideUserFields() {
        binding.addBtn.visibility = View.GONE
        binding.rmBtn.visibility = View.GONE
        binding.firstNameView.visibility = View.GONE
        binding.lastNameView.visibility = View.GONE
        binding.ageView.visibility = View.GONE
        binding.firstNameEdit.visibility = View.GONE
        binding.lastNameEdit.visibility = View.GONE
        binding.ageEdit.visibility = View.GONE
    }
    private fun clearInputFields() {
        binding.ageEdit.text?.clear()
        binding.emailEdit.text?.clear()
        binding.firstNameEdit.text?.clear()
        binding.lastNameEdit.text?.clear()
        binding.ageEdit.text?.clear()
    }
    private fun showAddRemoveButtons() {
        binding.addBtn.visibility = View.VISIBLE
        binding.rmBtn.visibility = View.VISIBLE
    }
    private fun showSuccessMessage(message: String) {
        binding.success.visibility = View.VISIBLE
        binding.success.text = message
        binding.error.visibility = View.GONE
    }
    private fun showErrorMessage(message: String) {
        binding.error.visibility = View.VISIBLE
        binding.error.text = message
        binding.success.visibility = View.GONE
    }
    private fun resetUI() {
        showAddRemoveButtons()
        clearInputFields()
        binding.updateBtn.text = "Update User"
        binding.emailEdit.visibility = View.VISIBLE
        binding.firstNameView.visibility = View.VISIBLE
        binding.lastNameView.visibility = View.VISIBLE
        binding.ageView.visibility = View.VISIBLE
        binding.firstNameEdit.visibility = View.VISIBLE
        binding.lastNameEdit.visibility = View.VISIBLE
        binding.ageEdit.visibility = View.VISIBLE
        binding.userEmail.visibility = View.GONE
        binding.success.visibility = View.GONE
        binding.error.visibility = View.GONE
    }
    //function to display the lists
    private fun updateForLists() {
        activeUserListCounter.text = "Number of Active Users: ${activeUserList.size}"
        deletedUserListCounter.text = "Number of Deleted Users: ${deletedUserList.size}"
    }
    //added validation functions from previous homework
    //combined all input validations into one function to call it in adduser and remove user functions
    private fun validateInputs(etEmail: String, etFirstName: String, etLastName: String, etAge: String): Boolean {
        if (!filledInputs(etEmail, etFirstName, etLastName, etAge)) {
            return false
        }
        if (!emailValidation(etEmail)) {
            return false
        }
        if (!nameValidation(etFirstName, etLastName)) {
            return false
        }
        if (!ageValidation(etAge)) {
            return false
        }
        return true
    }
    //function for checking if all the fields are filled
    private fun filledInputs(
        etEmail: String,
        etFirstName: String,
        etLastName: String,
        etAge: String
    ): Boolean {
        if (etEmail.isBlank() || etFirstName.isBlank() || etLastName.isBlank() || etAge.isBlank()) {
            toastFunction("Please fill in all fields")
            return false
        }
        return true
    }
    //email validation pattern
    private fun emailValidation(etEmail: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(etEmail).matches()) {
            return true
        }
        toastFunction("Please enter a valid email address")
        return false
    }
    //validating name and last name together as they are basically the same
    private fun nameValidation(etFirstName: String, etLastName: String): Boolean {
        if (etFirstName.isNotBlank() && etLastName.isNotBlank()) {
            return true
        }
        toastFunction("Please enter both a first name and a last name")
        return false
    }
    //even though nothing aside numbers are permitted from xml, still wrote number validation
    private fun ageValidation(etAge: String): Boolean {
        if (!etAge.contains(".") && !etAge.contains("-")) {
            return try {
                val ageToInt = etAge.toInt()
                ageToInt >= 1
            } catch (e: NumberFormatException) {
                toastFunction("Please enter your age in correct format")
                false
            }
        }
        toastFunction("Please enter your age in correct format")
        return false
    }
    //wrote one function for toast to reduce redundancy in code
    private fun toastFunction(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
