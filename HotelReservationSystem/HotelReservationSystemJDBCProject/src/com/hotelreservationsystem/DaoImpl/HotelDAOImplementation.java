package com.hotelreservationsystem.DaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hotelreservationsystem.Dao.HotelDAO;
import com.hotelreservationsystem.model.Hotel;


public class HotelDAOImplementation implements HotelDAO {

	    // Adds a hotel to the database
	    @Override
	    public void addHotel(Connection connection, Hotel hotel) {
	        try {
	            String query = "INSERT INTO hotels (hotel_name, hotel_address, total_rooms, available_rooms, hotel_amenities) VALUES (?, ?, ?, ?, ?)";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	                preparedStatement.setString(1, hotel.getHotelName());
	                preparedStatement.setString(2, hotel.getHotelAddress());
	                preparedStatement.setInt(3, hotel.getTotalRooms());
	                preparedStatement.setInt(4, hotel.getAvailableRooms());
	                preparedStatement.setString(5, hotel.getHotelAmenities());

	                preparedStatement.executeUpdate();
	                System.out.println("Hotel added successfully.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    private static final int HOTEL_ID = 1;  
	    // View details of a specific hotel from the database
	    @Override
	    public void viewHotelDetails(Connection connection) {
	        try {
	            String query = "SELECT * FROM hotels WHERE hotel_id = ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	                preparedStatement.setInt(1, HOTEL_ID);
	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                    if (resultSet.next()) {
	                        System.out.println("Hotel Details:");
	                        System.out.println("--------------");
	                        System.out.printf("%-20s: %s%n", "Hotel Name", resultSet.getString("hotel_name"));
	                        System.out.printf("%-20s: %s%n", "Address", resultSet.getString("hotel_address"));
	                        System.out.printf("%-20s: %d%n", "Total Rooms", resultSet.getInt("total_rooms"));
	                        System.out.printf("%-20s: %d%n", "Available Rooms", resultSet.getInt("available_rooms"));
	                        System.out.printf("%-20s: %s%n", "Amenities", resultSet.getString("hotel_amenities"));
	                        System.out.println("--------------");
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    // Check room availability for a specified hotel
	    @Override
	    public String checkRoomAvailability(Connection connection, int requiredRooms) {
	        try {
	            String query = "SELECT available_rooms FROM hotels WHERE hotel_id = ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	                preparedStatement.setInt(1, HOTEL_ID);
	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                    if (resultSet.next()) {
	                        int availableRooms = resultSet.getInt("available_rooms");
	                        if (availableRooms >= requiredRooms) {
	                            return "Rooms Are Available! Book Your Room Now!!! ";
	                        } else {
	                            return "Rooms Are Not Available. Sorry For Inconvenience";
	                        }
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return "Error occurred while checking room availability.";
	    }

	    // Check amenities for a specified hotel
	    @Override
	    public String checkAmenities(Connection connection) {
	        try {
	            String query = "SELECT hotel_amenities FROM hotels WHERE hotel_id = ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	                preparedStatement.setInt(1, HOTEL_ID);
	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                    if (resultSet.next()) {
	                        return resultSet.getString("hotel_amenities");
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return "Amenities not available for the specified hotel.";
	    }
	}


