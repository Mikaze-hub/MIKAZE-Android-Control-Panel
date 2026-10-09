package com.mikaze.controlpanel

import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mikaze.controlpanel.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isPanelOpen = false
    private var isLicenseVerified = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.black)
        window.navigationBarColor = getColor(R.color.black)

        setupUI()
        updateClock()
        setupVerification()
        setupPanelControls()
    }

    private fun setupUI() {
        // Initial state
        binding.panelContainer.visibility = View.GONE
        binding.menuClosedBadge.visibility = View.VISIBLE
        binding.closeMenuButton.text = "OPEN MENU"
        isPanelOpen = false
    }

    private fun setupVerification() {
        binding.verifyButton.setOnClickListener {
            val input = binding.licenseInput.text?.toString()?.trim().orEmpty()
            when {
                input.isEmpty() -> {
                    Toast.makeText(this, "Please enter a license key", Toast.LENGTH_SHORT).show()
                }
                input.equals("MIKAZE", ignoreCase = true) || input.contains("MIKAZE", ignoreCase = true) -> {
                    isLicenseVerified = true
                    binding.connectionStatus.text = "✓ Connected • License Verified"
                    binding.connectionStatus.setTextColor(getColor(R.color.gold))
                    binding.licenseHint.text = "Access granted to control panel."
                    binding.licenseHint.setTextColor(getColor(R.color.gold))
                    binding.verifyButton.alpha = 0.8f
                    binding.licenseInput.isEnabled = false
                    Toast.makeText(this, "License Verified Successfully!", Toast.LENGTH_SHORT).show()
                    // Panel auto-opens after verification
                    binding.panelContainer.visibility = View.VISIBLE
                    binding.menuClosedBadge.visibility = View.GONE
                    binding.closeMenuButton.text = "CLOSE MENU"
                    isPanelOpen = true
                }
                else -> {
                    Toast.makeText(this, "Invalid License Key", Toast.LENGTH_SHORT).show()
                    binding.connectionStatus.text = "✗ Restricted • License Required"
                    binding.connectionStatus.setTextColor(getColor(R.color.text_secondary))
                    binding.licenseHint.text = "Enter a valid MIKAZE key to unlock controls."
                    binding.licenseHint.setTextColor(getColor(R.color.text_secondary))
                    binding.panelContainer.visibility = View.GONE
                    binding.menuClosedBadge.visibility = View.VISIBLE
                    binding.closeMenuButton.text = "OPEN MENU"
                    isPanelOpen = false
                }
            }
        }
    }

    private fun setupPanelControls() {
        binding.closeMenuButton.setOnClickListener {
            if (!isLicenseVerified) return@setOnClickListener
            isPanelOpen = !isPanelOpen
            binding.panelContainer.visibility = if (isPanelOpen) View.VISIBLE else View.GONE
            binding.menuClosedBadge.visibility = if (isPanelOpen) View.GONE else View.VISIBLE
            binding.closeMenuButton.text = if (isPanelOpen) "CLOSE MENU" else "OPEN MENU"
        }

        setupSwitchListeners()
    }

    private fun setupSwitchListeners() {
        binding.switchFps.setOnCheckedChangeListener { _, isChecked ->
            binding.fpsState.text = if (isChecked) "ON" else "OFF"
            binding.fpsState.setTextColor(getColor(if (isChecked) R.color.gold else R.color.text_secondary))
        }

        binding.switchTimer.setOnCheckedChangeListener { _, isChecked ->
            binding.timerState.text = if (isChecked) "ON" else "OFF"
            binding.timerState.setTextColor(getColor(if (isChecked) R.color.gold else R.color.text_secondary))
        }

        binding.switchClock.setOnCheckedChangeListener { _, isChecked ->
            binding.clockState.text = if (isChecked) "ON" else "OFF"
            binding.clockState.setTextColor(getColor(if (isChecked) R.color.gold else R.color.text_secondary))
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            binding.darkModeState.text = if (isChecked) "ON" else "OFF"
            binding.darkModeState.setTextColor(getColor(if (isChecked) R.color.gold else R.color.text_secondary))
        }

        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            binding.notifState.text = if (isChecked) "ON" else "OFF"
            binding.notifState.setTextColor(getColor(if (isChecked) R.color.gold else R.color.text_secondary))
        }

        binding.switchAdvanced.setOnCheckedChangeListener { _, isChecked ->
            binding.advancedState.text = if (isChecked) "ON" else "OFF"
            binding.advancedState.setTextColor(getColor(if (isChecked) R.color.gold else R.color.text_secondary))
        }

        binding.switchPerformance.setOnCheckedChangeListener { _, isChecked ->
            binding.performanceState.text = if (isChecked) "TURBO" else "NORMAL"
            binding.performanceState.setTextColor(getColor(if (isChecked) R.color.gold else R.color.text_secondary))
        }

        binding.switchGfx.setOnCheckedChangeListener { _, isChecked ->
            binding.gfxState.text = if (isChecked) "ULTRA" else "HIGH"
            binding.gfxState.setTextColor(getColor(if (isChecked) R.color.gold else R.color.text_secondary))
        }
    }

    private fun updateClock() {
        val timer = object : Runnable {
            override fun run() {
                val calendar = Calendar.getInstance()
                val time = DateFormat.format("HH:mm", calendar).toString()
                binding.clockText.text = time
                binding.mainClock.text = time
                binding.root.postDelayed(this, 1000)
            }
        }
        binding.root.post(timer)
    }
}
