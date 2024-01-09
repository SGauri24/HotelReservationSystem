package com.hotelreservationsystem.Dao;

import java.sql.Connection;

import com.hotelreservationsystem.model.Hotel;

public interface HotelDAO {
	// Adds a hotel to the database
    void addHotel(Connection connection, Hotel hotel);

    // View details of a specific hotel from the database
    void viewHotelDetails(Connection connection);

    // Check room availability for a specified hotel
    String checkRoomAvailability(Connection connection, int requiredRooms);

    // Check amenities for a specified hotel
    String checkAmenities(Connection connection);
}
