package com.hotelreservationsystem.Dao;

import java.sql.Connection;
import java.util.Scanner;

public interface ReservationDAO {
	// Reserves a room by updating the database with user input.
    void reserveRoom(Connection connection, Scanner scanner);

    // Updates an existing reservation in the database based on user input.
    void updateReservation(Connection connection, Scanner scanner);

    // Checks out a room, completing the reservation process and updating the database.
    void checkoutRoom(Connection connection, Scanner scanner);

    // Processes a payment for a reservation using user input.
    void makePayment(Connection connection, Scanner scanner);

    // Deletes a reservation from the database based on the provided reservation ID.
    void cancelReservation(Connection connection, Scanner scanner);

    // Exits the reservation system, closing any necessary resources.
    void exit();
}
