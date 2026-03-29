package com.example.xmlarchitectapp.models

import java.io.Serializable

/**
 * Product data class implementing Serializable for Bundle passing (F2).
 * Represents a smart home device/product.
 */
data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double,
    val rating: Float,
    val description: String,
    val imageResName: String
) : Serializable {

    companion object {
        /**
         * Returns a list of sample smart home products for the RecyclerView.
         */
        fun getSampleProducts(): List<Product> {
            return listOf(
                Product(
                    1, "Smart LED Bulb", "Lighting",
                    29.99, 4.5f,
                    "WiFi-enabled smart LED bulb with 16 million colors. Control via app or voice assistant. Energy efficient with 25,000 hour lifespan.",
                    "ic_avatar"
                ),
                Product(
                    2, "Smart Thermostat", "Climate",
                    199.99, 4.8f,
                    "AI-powered thermostat that learns your schedule. Saves up to 23% on heating and cooling bills. Compatible with all HVAC systems.",
                    "ic_avatar"
                ),
                Product(
                    3, "Security Camera", "Security",
                    89.99, 4.3f,
                    "1080p HD indoor/outdoor camera with night vision. Motion detection alerts sent to your phone. 30-day cloud storage included.",
                    "ic_avatar"
                ),
                Product(
                    4, "Smart Door Lock", "Security",
                    249.99, 4.6f,
                    "Keyless entry with fingerprint, PIN, and app unlock. Auto-lock feature and guest access codes. Battery backup included.",
                    "ic_avatar"
                ),
                Product(
                    5, "Robot Vacuum", "Cleaning",
                    349.99, 4.4f,
                    "LiDAR navigation with multi-floor mapping. 2500Pa suction power. Self-emptying dock with 60-day capacity.",
                    "ic_avatar"
                ),
                Product(
                    6, "Smart Speaker", "Entertainment",
                    99.99, 4.7f,
                    "360-degree premium sound with built-in voice assistant. Multi-room audio support. Smart home hub functionality.",
                    "ic_avatar"
                ),
                Product(
                    7, "Smart Plug", "Power",
                    19.99, 4.2f,
                    "WiFi smart plug with energy monitoring. Set schedules and timers. Works with Alexa and Google Home.",
                    "ic_avatar"
                ),
                Product(
                    8, "Video Doorbell", "Security",
                    179.99, 4.5f,
                    "2K HDR video with head-to-toe view. Two-way talk with noise cancellation. Pre-roll video capture.",
                    "ic_avatar"
                ),
                Product(
                    9, "Smart Blinds", "Comfort",
                    159.99, 4.1f,
                    "Motorized roller blinds with solar charging. Schedule-based automation. Light sensor for auto-adjustment.",
                    "ic_avatar"
                ),
                Product(
                    10, "Air Quality Monitor", "Climate",
                    129.99, 4.3f,
                    "Tracks PM2.5, CO2, humidity, and temperature. Real-time air quality index. Integrates with smart purifiers.",
                    "ic_avatar"
                )
            )
        }
    }
}
