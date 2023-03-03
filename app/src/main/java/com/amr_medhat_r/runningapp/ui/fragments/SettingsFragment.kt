package com.amr_medhat_r.runningapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amr_medhat_r.runningapp.R
import com.amr_medhat_r.runningapp.databinding.FragmentSettingsBinding
import com.amr_medhat_r.runningapp.other.Constants
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Layouts inflation
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFieldsFromSharedPrefs()
        binding.btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPrefs()
            if (success) {
                Snackbar.make(requireView(), "Saved changes", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(requireView(), "Please enter all the fields", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun loadFieldsFromSharedPrefs() {
        val name = sharedPrefs.getString(Constants.KEY_NAME, "") ?: ""
        val weight = sharedPrefs.getFloat(Constants.KEY_WEIGHT, 80f)

        binding.etName.setText(name)
        binding.etWeight.setText(weight.toString())

    }

    private fun applyChangesToSharedPrefs(): Boolean {
        val name = binding.etName.text.toString()
        val weight = binding.etWeight.text.toString()

        if (name.isEmpty() || weight.isEmpty()) {
            return false
        }

        sharedPrefs.edit()
            .putString(Constants.KEY_NAME, name)
            .putFloat(Constants.KEY_WEIGHT, weight.toFloat())
            .apply()

        val toolbarText = "Let's go, $name"
        requireActivity().findViewById<TextView>(R.id.tvToolbarTitle).text = toolbarText
        return true
    }

}