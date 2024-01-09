package com.hotelreservationsystem.DaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import com.hotelreservationsystem.Dao.ReservationDAO;
import com.hotelreservationsystem.model.Reservation;



public class ReservationDAOImplementation implements ReservationDAO {
	@Override
	public void reserveRoom(Connection connection, Scanner scanner) {
	    try {
	    	// Input guest information
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine().trim(); // Read the entire line, including spaces

            System.out.print("Enter guest address: ");
            String address = scanner.nextLine().trim(); // Read the entire line, including spaces

            // Validate contact number (must be 10 digits)
            String contactNumber;
            while (true) {
                System.out.print("Enter contact number: ");
                contactNumber = scanner.nextLine().trim();
                
                if (contactNumber.matches("\\d{10}")) {
                    break;  // Exit the loop if the contact number is valid
                } else {
                    System.out.println("Invalid contact number. Please enter exactly 10 digits.");
                }
            }
            // Display room types
            System.out.println("Room Types:");
            System.out.println("1. Standard");
            System.out.println("2. Deluxe");
            System.out.println("3. Suite");
            System.out.print("Choose room type (1/2/3): ");
            int roomTypeChoice = scanner.nextInt();

            String roomType;
            switch (roomTypeChoice) {
                case 1:
                    roomType = "Standard";
                    break;
                case 2:
                    roomType = "Deluxe";
                    break;
                case 3:
                    roomType = "Suite";
                    break;
                default:
                    System.out.println("Invalid room type choice. Using Standard as default.");
                    roomType = "Standard";
            }

            System.out.print("Enter ID proof: ");
            String idProofType = scanner.next().trim();
            scanner.nextLine(); // Consume newline character

            // Validate ID proof
            String idProof = idProofType + ":";

            if ("voter".equalsIgnoreCase(idProofType)) {
                System.out.print("Enter voter ID (10 digits): ");
                String voterId = scanner.next();
                if (voterId.length() != 10 || !voterId.matches("\\d+")) {
                    System.out.println("Invalid voter ID. Please enter a 10-digit numeric voter ID.");
                    return;
                }
                idProof += voterId;
            } else if ("aadhar".equalsIgnoreCase(idProofType)) {
                System.out.print("Enter Aadhar number (12 digits): ");
                String aadharNumber = scanner.next();
                if (aadharNumber.length() != 12 || !aadharNumber.matches("\\d+")) {
                    System.out.println("Invalid Aadhar number. Please enter a 12-digit numeric Aadhar number.");
                    return;
                }
                idProof += aadharNumber;
            } else {
                System.out.println("Invalid ID proof. Please enter 'voter' or 'aadhar'.");
                return;
            }

            // Get check-in date from the user
            System.out.print("Enter check-in date (YYYY-MM-DD): ");
            String checkinDateStr = scanner.next();

            // Parse the input string to a LocalDate
            java.time.LocalDate localDate = java.time.LocalDate.parse(checkinDateStr);

            // Convert LocalDate to java.sql.Date
            java.sql.Date checkinDate = java.sql.Date.valueOf(localDate);

            // Generate a room number between 16 and 40
            int roomNumber = generateRandomRoomNumber(16, 40);

            // Create a Reservation object and set the values
            Reservation reservation = new Reservation();
            reservation.setGuestName(guestName);
            reservation.setAddress(address);
            reservation.setContactNumber(contactNumber);
            reservation.setRoomType(roomType);
            reservation.setIdProof(idProof);
            reservation.setRoomNumber(roomNumber);
            reservation.setCheckinDate(checkinDate);

            // SQL query to insert the reservation into the database
            String insertQuery = "INSERT INTO reservations " +
                    "(guest_name, address, contact_number, room_type, id_proof, room_number, checkin_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            // SQL query to update available rooms
            String updateRoomsQuery = "UPDATE hotels SET available_rooms = available_rooms - 1";

            // Use PreparedStatement to safely execute the query and retrieve the generated keys
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement updateRoomsStatement = connection.prepareStatement(updateRoomsQuery)) {

                insertStatement.setString(1, reservation.getGuestName());
                insertStatement.setString(2, reservation.getAddress());
                insertStatement.setString(3, reservation.getContactNumber());
                insertStatement.setString(4, reservation.getRoomType());
                insertStatement.setString(5, reservation.getIdProof());
                insertStatement.setInt(6, reservation.getRoomNumber());
                insertStatement.setDate(7, reservation.getCheckinDate());
              
                // Execute the update and check the affected rows
                int affectedRows = insertStatement.executeUpdate();

                if (affectedRows > 0) {
                    // Execute the update to decrease available rooms
                    updateRoomsStatement.executeUpdate();

                    // Retrieve the generated keys (including the reservation ID)
                    try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int reservationId = generatedKeys.getInt(1);
                            System.out.println("Reservation successful!\nYour Reservation ID is: " + reservationId +
                                    "\nYour Room Number is: " + roomNumber);
                        } else {
                            System.out.println("Failed to retrieve reservation ID.");
                        }
                    }
                } else {
                    System.out.println("Reservation failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	// Helper method to generate a random room number within the specified range
	private int generateRandomRoomNumber(int min, int max) {
	    return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

   


	@Override
	public void updateReservation(Connection connection, Scanner scanner) {
	    try {
	        // Input reservation ID to update
	        System.out.print("Enter reservation ID to update: ");
	        int reservationId = scanner.nextInt();
	        scanner.nextLine(); // Consume the newline character

	        // Check if the reservation exists
	        if (!reservationExists(connection, reservationId)) {
	            System.out.println("Reservation not found for the given ID.");
	            return;
	        }

	        // Display options for updating
	        System.out.println("Select the information to update:");
	        System.out.println("1. Guest Name");
	        System.out.println("2. Guest Address"); // Change this option
	        System.out.println("3. Contact Number");
	        System.out.println("4. Room Type");
	        System.out.println("5. ID Proof");
	        System.out.println("6. Check-in Date");

	        System.out.print("Enter your choice (1-6): ");
	        int choice = scanner.nextInt();
	        scanner.nextLine(); // Consume the newline character

	        // Input new value based on user choice
	        String columnName = "";
	        String newValue = "";

	        switch (choice) {
	            case 1:
	                columnName = "guest_name";
	                System.out.print("Enter new guest name: ");
	                newValue = scanner.nextLine();
	                break;
	            case 2:
	                columnName = "address"; // Change column name to address
	                System.out.print("Enter new guest address: "); // Change prompt
	                newValue = scanner.nextLine();
	                break;
	            case 3:
	                columnName = "contact_number";
	                System.out.print("Enter new contact number: ");
	                newValue = scanner.nextLine();
	                break;
	            case 4:
	                columnName = "room_type";
	                System.out.print("Enter new room type: ");
	                newValue = scanner.nextLine();
	                break;
	            case 5:
	                columnName = "id_proof";
	                System.out.print("Enter new ID proof: ");
	                newValue = scanner.nextLine();
	                break;
	            case 6:
	                columnName = "checkin_date";
	                System.out.print("Enter new check-in date (YYYY-MM-DD): ");
	                newValue = scanner.nextLine();
	                break;
	            default:
	                System.out.println("Invalid choice. No update performed.");
	                return;
	        }

	        // SQL query to update the reservation information
	        String sql = "UPDATE reservations SET " + columnName + " = '" + newValue + "' " +
	                "WHERE reservation_id = " + reservationId;

	        // Use Statement to execute the update
	        try (Statement statement = connection.createStatement()) {
	            // Execute the update and check the affected rows
	            int affectedRows = statement.executeUpdate(sql);

	            if (affectedRows > 0) {
	                System.out.println("Reservation updated successfully!");
	            } else {
	                System.out.println("Reservation update failed.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

    @Override
    public void checkoutRoom(Connection connection, Scanner scanner) {
        try {
            // Input reservation ID to checkout
            System.out.print("Enter reservation ID to checkout: ");
            int reservationId = scanner.nextInt();

            // Check if the reservation exists
            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }

            // Check if the room has already been checked out
            if (isCheckedOut(connection, reservationId)) {
                System.out.println("Room already checked out.");
                return;
            }

            
            // Mark the room as checked out and update the checkout time
            String checkoutSql = "UPDATE reservations SET checkout_date = ? WHERE reservation_id = ?";
            try (PreparedStatement checkoutStatement = connection.prepareStatement(checkoutSql)) {
                checkoutStatement.setTimestamp(1, new Timestamp(new Date().getTime()));
                checkoutStatement.setInt(2, reservationId);

                // Execute the update and check the affected rows
                int affectedRows = checkoutStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Room checked out successfully!");
                } else {
                    System.out.println("Room checkout failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isCheckedOut(Connection connection, int reservationId) {
        try {
            // SQL query to select the checkout date for the given reservation
            String sql = "SELECT checkout_date FROM reservations WHERE reservation_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, reservationId);
                ResultSet resultSet = statement.executeQuery();

                // Check if there is a result and if the checkout date is not null
                return resultSet.next() && resultSet.getTimestamp("checkout_date") != null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   

    @Override
    public void makePayment(Connection connection, Scanner scanner) {
        try {
            // Input reservation ID to make payment
            System.out.print("Enter reservation ID to make payment: ");
            int reservationId = scanner.nextInt();

            // Check if the reservation exists
            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }

            // Check if the room has been checked out
            if (!isCheckedOut(connection, reservationId)) {
                System.out.println("Room must be checked out before making a payment.");
                return;
            }

            // Get room type for the reservation
            String roomType = getRoomType(connection, reservationId);

            // Calculate the number of days stayed
            int daysStayed = calculateDaysStayed(connection, reservationId);

            // Set the rate based on room type
            int ratePerDay;
            switch (roomType) {
                case "Standard":
                    ratePerDay = 2000;
                    break;
                case "Deluxe":
                    ratePerDay = 3000;
                    break;
                case "Suite":
                    ratePerDay = 5000;
                    break;
                default:
                    System.out.println("Invalid room type.");
                    return;
            }

            // Calculate the total payment amount
            int totalAmount = daysStayed * ratePerDay;

            // Display payment details
            System.out.println("Total amount for " + daysStayed + " days in a " + roomType + " room: " + totalAmount);

            // Update payment status and amount in the database
            String paymentSql = "UPDATE reservations SET payment_status = true, payment_amount = ? WHERE reservation_id = ?";
            try (PreparedStatement paymentStatement = connection.prepareStatement(paymentSql)) {
                paymentStatement.setInt(1, totalAmount);
                paymentStatement.setInt(2, reservationId);

                // Execute the update and check the affected rows
                int affectedRows = paymentStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Payment successful!");
                } else {
                    System.out.println("Failed to update payment status.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 // Updated getRoomType method
    private String getRoomType(Connection connection, int reservationId) {
        try {
            String sql = "SELECT room_type FROM reservations WHERE reservation_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, reservationId);
                ResultSet resultSet = statement.executeQuery();

                // Check if there is a result
                if (resultSet.next()) {
                    return resultSet.getString("room_type");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return a default value (e.g., "unknown") when the room type is not found
        return "unknown";
    }


    private int calculateDaysStayed(Connection connection, int reservationId) {
        try {
            // SQL query to calculate the difference between checkout and check-in dates
            String sql = "SELECT DATEDIFF(checkout_date, checkin_date) AS days_stayed FROM reservations WHERE reservation_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, reservationId);
                ResultSet resultSet = statement.executeQuery();

                // Check if there is a result
                if (resultSet.next()) {
                    return resultSet.getInt("days_stayed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void cancelReservation(Connection connection, Scanner scanner) {
        try {
            // Input reservation ID to delete
            System.out.print("Enter reservation ID to Cancel Reservation : ");
            int reservationId = scanner.nextInt();

            // Check if the reservation exists
            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }

            // SQL query to delete the reservation from the database
            String sql = "DELETE FROM reservations WHERE reservation_id = " + reservationId;

            // Use Statement to execute the deletion
            try (Statement statement = connection.createStatement()) {
                // Execute the deletion and check the affected rows
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation cancelled successfully!");
                } else {
                    System.out.println("Reservation cancellation failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public boolean reservationExists(Connection connection, int reservationId) {
        try {
            // SQL query to select the reservation ID for the given ID
            String sql = "SELECT reservation_id FROM reservations WHERE reservation_id = " + reservationId;

            // Use Statement and ResultSet to check if a result exists
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                return resultSet.next(); // If there's a result, the reservation exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle database errors as needed
        }
    }

    @Override
    public void exit() {
        try {
            System.out.print("Exiting System");
            int i = 5;
            while (i != 0) {
                System.out.print(".");
                Thread.sleep(100);
                i--;
            }
            System.out.println();
            System.out.println("Thank You For Using Hotel Reservation System!!!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error during exit: " + e.getMessage());
        }
    }

}




