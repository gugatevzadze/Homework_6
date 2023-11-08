package com.example.homework_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
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

    private fun addUser() {
        etEmail = binding.emailEdit.text.toString()
        etFirstName = binding.firstNameEdit.text.toString()
        etLastName = binding.lastNameEdit.text.toString()
        etAge = binding.ageEdit.text.toString()

        val userObject = UserClass(etEmail, etFirstName, etLastName, etAge)

        if (!activeUserList.contains(userObject)) {
            activeUserList.add(userObject)
            showSuccessMessage("User added successfully")
            updateForLists()
        } else {
            showErrorMessage("User already exists")
        }
    }

    private fun removeUser() {
        val userObjectToRemove = UserClass(etEmail, etFirstName, etLastName, etAge)
        deletedUserList.add(userObjectToRemove)
        if (activeUserList.contains(userObjectToRemove)) {
            activeUserList.remove(userObjectToRemove)
            showSuccessMessage("User deleted successfully")
            updateForLists()
        } else {
            showErrorMessage("User does not exist")
        }
    }

    private fun startUserUpdate() {
        hideUserFields()
        clearInputFields()
        binding.updateBtn.text = "Search With Email"
    }

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

    private fun saveUserUpdate() {
        val emailToSearch = binding.emailEdit.text.toString()
        val matchingUser = activeUserList.find { it.email == emailToSearch }
        matchingUser?.apply {
            email = binding.emailEdit.text.toString()
            firstName = binding.firstNameEdit.text.toString()
            lastName = binding.lastNameEdit.text.toString()
            age = binding.ageEdit.text.toString()
        }
        showSuccessMessage("User Successfully Updated")
        resetUI()
    }

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
    private fun updateForLists(){
        activeUserListCounter.text = "Number of Active Users: ${activeUserList.size}"
        deletedUserListCounter.text = "Number of Deleted Users: ${deletedUserList.size}"
    }
}
