package com.hotelreservationsystem.model;

import java.sql.Date;
import java.sql.Timestamp;

// Represents a hotel reservation made by a guest.
public class Reservation {

        private int reservationId; // Unique identifier for the reservation
	    private String guestName; // Name of the guest making the reservation
	    private String address; // Address of the guest
	    private int roomNumber; // Room number for the reservation
	    private String contactNumber; // Contact number of the guest
	    private String idProof; // ID proof of the guest
	    private Timestamp reservationDate; // Date and time when the reservation was made
	    private java.sql.Date checkinDate; // Date when the guest is expected to check in
	    private Timestamp checkoutDate; // Date and time when the guest is expected to check out
	    private boolean paymentStatus; // Payment status for the reservation
	    private String roomType; // Type of room reserved

	    // Getter for reservationId
	    public int getReservationId() {
	        return reservationId;
	    }

	    // Setter for reservationId
	    public void setReservationId(int reservationId) {
	        this.reservationId = reservationId;
	    }

	    // Getter for guestName
	    public String getGuestName() {
	        return guestName;
	    }

	    // Setter for guestName
	    public void setGuestName(String guestName) {
	        this.guestName = guestName;
	    }

	    // Getter for roomNumber
	    public int getRoomNumber() {
	        return roomNumber;
	    }

	    // Setter for roomNumber
	    public void setRoomNumber(int roomNumber) {
	        this.roomNumber = roomNumber;
	    }

	    // Getter for contactNumber
	    public String getContactNumber() {
	        return contactNumber;
	    }

	    // Setter for contactNumber
	    public void setContactNumber(String contactNumber) {
	        this.contactNumber = contactNumber;
	    }

	    // Getter for reservationDate
	    public Timestamp getReservationDate() {
	        return reservationDate;
	    }

	    // Setter for reservationDate
	    public void setReservationDate(Timestamp reservationDate) {
	        this.reservationDate = reservationDate;
	    }

	    // Getter for checkoutDate
	    public Timestamp getCheckoutDate() {
	        return checkoutDate;
	    }

	    // Setter for checkoutDate
	    public void setCheckoutDate(Timestamp checkoutDate) {
	        this.checkoutDate = checkoutDate;
	    }

	    // Getter for checkinDate
	    public java.sql.Date getCheckinDate() {
	        return checkinDate;
	    }

	    // Setter for checkinDate
	    public void setCheckinDate(java.sql.Date checkinDate) {
	        this.checkinDate = checkinDate;
	    }

	    // Getter for paymentStatus
	    public boolean isPaymentStatus() {
	        return paymentStatus;
	    }

	    // Setter for paymentStatus
	    public void setPaymentStatus(boolean paymentStatus) {
	        this.paymentStatus = paymentStatus;
	    }

	    // Getter and Setter for address
	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    // Getter and Setter for idProof
	    public String getIdProof() {
	        return idProof;
	    }

	    public void setIdProof(String idProof) {
	        this.idProof = idProof;
	    }

	    // Getter and Setter for roomType
	    public String getRoomType() {
	        return roomType;
	    }

	    public void setRoomType(String roomType) {
	        this.roomType = roomType;
	    }

	    // Parameterized constructor for creating a Reservation object with specified values.
	    public Reservation(String guestName, String address, int roomNumber, String contactNumber, String idProof,
	                       Timestamp reservationDate, Date checkinDate, Timestamp checkoutDate, boolean paymentStatus) {
	        super();
	        this.guestName = guestName;
	        this.address = address;
	        this.roomNumber = roomNumber;
	        this.contactNumber = contactNumber;
	        this.idProof = idProof;
	        this.reservationDate = reservationDate;
	        this.checkinDate = checkinDate;
	        this.checkoutDate = checkoutDate;
	        this.paymentStatus = paymentStatus;
	    }

	    // Default constructor for creating an empty Reservation object.
	    public Reservation() {
	        super();
	    }
	}