package com.example.signup.ui.preferences

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.signup.utils.Locator
import com.moronlu18.invoice.R
import java.util.Locale

class  SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)
        initPreferencesInvoice()
        initPreferencesItem()
        initPreferencesTask()
        initPreferencesCustomer()
        initPreferencesTheme()
        initPreferencesLanguaje()
        //preferenceManager.preferenceDataStore = Locator.settingsPreferencesRepository


    }

    private fun initPreferencesTheme() {
        val option = preferenceManager.findPreference<Preference>(getString(R.string.theme)) as SwitchPreference?

        option?.setOnPreferenceChangeListener { preference, newValue ->
            if(newValue == true){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Locator.invoicePreferencesRepository.saveTheme("true")
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Locator.invoicePreferencesRepository.saveTheme("false")
            }
            true
        }

    }
    private fun initPreferencesLanguaje() {
        val option = preferenceManager.findPreference<Preference>(getString(R.string.languaje)) as SwitchPreference?
        var languageCode = Locale.getDefault().language

        option?.setOnPreferenceChangeListener { preference, newValue ->
            if(newValue == true){
                languageCode = "en"
                Locator.invoicePreferencesRepository.saveLanguaje("true")
            }else{
                Locator.invoicePreferencesRepository.saveLanguaje("false")
            }
            true
        }
        val recursos = context?.resources
        val displayMetrics = recursos?.displayMetrics
        val configuration = resources.configuration

        configuration.setLocale(Locale(languageCode))
        recursos?.updateConfiguration(configuration,displayMetrics)
        configuration.locale = Locale(languageCode)
        resources.updateConfiguration(configuration, displayMetrics)
    }

    private fun initPreferencesCustomer() {
        val option = preferenceManager.findPreference<Preference>(getString(R.string.key_customer_order)) as ListPreference?

        option?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference){
                val index = preference.findIndexOfValue(newValue.toString())
                val entry = preference.entries.get(index)
                val entryvalue = preference.entryValues.get(index)
                println("----------------------- ${entryvalue} ")
                Locator.invoicePreferencesRepository.saveCustomerOr(entryvalue.toString())
            }
            true
        }
    }


    private fun initPreferencesInvoice() {
        val option = preferenceManager.findPreference<Preference>(getString(R.string.key_ivoice_order)) as ListPreference?

        option?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference){
                val index = preference.findIndexOfValue(newValue.toString())
                val entry = preference.entries.get(index)
                val entryvalue = preference.entryValues.get(index)
                Locator.invoicePreferencesRepository.saveInvoiceOr(entryvalue.toString())
            }
            true
        }


    }

    private fun initPreferencesItem() {
        findPreference<ListPreference>(getString(R.string.key_item_order))?.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
                if (preference is ListPreference) {

                    val index = preference.findIndexOfValue(newValue.toString())

                    val entryValue = preference.entryValues.getOrNull(index)

                    entryValue?.let {
                        Locator.invoicePreferencesRepository.saveItemOrder(it.toString())
                    }

                }

                true
            }
        }
    }

    private fun initPreferencesTask() {
        findPreference<ListPreference>(getString(R.string.key_task_order))?.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
                if (preference is ListPreference) {
                    val index = preference.findIndexOfValue(newValue.toString())
                    val entryValue = preference.entryValues.getOrNull(index)
                    entryValue?.let {
                        Locator.invoicePreferencesRepository.saveTaskOrder(it.toString())
                    }
                }
                true
            }
        }
    }

}