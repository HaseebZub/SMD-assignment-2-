package com.example.xmlarchitectapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.xmlarchitectapp.R

/**
 * HomeFragment - Dashboard home content (F4).
 * Receives user data via Bundle arguments.
 * Displays the smart home dashboard with stats and controls.
 */
class HomeFragment : Fragment() {

    private var userName: String = ""
    private var userEmail: String = ""

    companion object {
        private const val ARG_USER_NAME = "user_name"
        private const val ARG_USER_EMAIL = "user_email"

        /**
         * Factory method using Bundle for data passing.
         * Data is passed to Fragment via Bundle arguments (not static variables).
         */
        fun newInstance(userName: String, userEmail: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle().apply {
                putString(ARG_USER_NAME, userName)
                putString(ARG_USER_EMAIL, userEmail)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve data from Bundle arguments
        arguments?.let {
            userName = it.getString(ARG_USER_NAME, "Guest")
            userEmail = it.getString(ARG_USER_EMAIL, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Update UI with data received via Bundle
        val tvUserName = view.findViewById<TextView>(R.id.tv_user_name)
        val tvUserStatus = view.findViewById<TextView>(R.id.tv_user_status)
        val tvGreeting = view.findViewById<TextView>(R.id.tv_greeting)

        tvUserName?.text = userName
        tvUserStatus?.text = userEmail
        tvGreeting?.text = "Good day, $userName! 🏠"
    }
}
