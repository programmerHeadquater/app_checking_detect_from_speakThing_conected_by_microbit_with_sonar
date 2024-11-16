package com.example.myapplication
// Data class to represent the `channel` object
data class Channel(
    val id: Int,
    val name: String,
    val latitude: String,
    val longitude: String,
    val field1: String,
    val created_at: String,
    val updated_at: String,
    val last_entry_id: Int
)

// Data class to represent each item in the `feeds` array
data class Feed(
    val created_at: String,
    val entry_id: Int,
    val field1: String // Assuming field1 represents distance or some sensor data
)

// Data class to represent the entire API response
data class ApiResponse(
    val channel: Channel,
    val feeds: List<Feed>
)
