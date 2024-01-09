package com.hotelreservationsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.hotelreservationsystem.DaoImpl.HotelDAOImplementation;
import com.hotelreservationsystem.DaoImpl.ReservationDAOImplementation;

public class HotelReservationSystem {
    // Database connection details
    private static final String url = "jdbc:mysql://localhost:3306/hotel";
    private static final String username = "root";
    private static final String password = "PHW#84#jeor";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Create instances of DAO classes
        ReservationDAOImplementation reservationDAO = new ReservationDAOImplementation();
        HotelDAOImplementation hotelDAO = new HotelDAOImplementation();
      /*  Hotel hotel = new Hotel("Hotel Dream Inn", "Laxmi Road, Pune, Maharashtra", 50, 25, "Swimming Pool, Gym, Wi-Fi");
	       
        hotelDAO.addHotel(hotel);*/
        

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            // Main application loop
            while (true) {
                System.out.println();
                System.out.println("WELCOME TO.....");
                System.out.println("HOTEL RESERVATION SYSTEM");
                System.out.println();
                
                // Display menu options
                System.out.println("1. View Hotel Details");
                System.out.println("2. Check Room Availability");
                System.out.println("3. Check Amenities");
                System.out.println("4. Reserve a room");
                System.out.println("5. Update Reservation");
                System.out.println("6. Checkout Room");
                System.out.println("7. Make Payment");
                System.out.println("8. Cancel Reservation");
                System.out.println("0. Exit");
                System.out.println();
                
                Scanner scanner = new Scanner(System.in);
                // Prompt user for choice
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                System.out.println();
                
               
                
                // Switch statement to handle user's choice
                switch (choice) {
                   case 1:
                        // View Hotel Details
                        hotelDAO.viewHotelDetails(connection);
                        break;
                   case 2:
                        // Check Room Availability
                        System.out.print("Enter required rooms: ");
                        int requiredRooms = scanner.nextInt();
                        String availabilityMessage = hotelDAO.checkRoomAvailability(connection, requiredRooms);
                        System.out.println(availabilityMessage);
                        break;
                    case 3:
                        // Check Amenities
                        String amenities = hotelDAO.checkAmenities(connection);
                        System.out.println("Amenities: " + amenities);
                        break;
                    case 4:
                        // Reserve Room
                        reservationDAO.reserveRoom(connection, scanner);
                        break;
                    case 5:
                        // Update Reservation
                        reservationDAO.updateReservation(connection, scanner);
                        break;
                    case 6:
                        // Checkout Room
                        reservationDAO.checkoutRoom(connection, scanner);
                        break;
                    case 7:
                        // Make Payment
                        reservationDAO.makePayment(connection, scanner);
                        break;
                    case 8:
                        // Delete Reservation
                        reservationDAO.cancelReservation(connection, scanner);
                        break;
                    case 0:
                        // Exit the application
                        reservationDAO.exit();
                        scanner.close();
                        return;
                    default:
                        // Invalid choice
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}