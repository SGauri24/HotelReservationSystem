//package declaration
package com.hotelreservationsystem.model;

public class Hotel {

	 // Attributes of the Hotel class
    private int hotelId; // Unique identifier for the hotel
    private String hotelName; // Name of the hotel
    private String hotelAddress; // Address of the hotel
    private int totalRooms; // Total number of rooms in the hotel
    private int availableRooms; // Number of currently available rooms in the hotel
    private String hotelAmenities; // List of amenities provided by the hotel

    // Getters and Setters for the attributes

    // Getter for hotelId
    public int getHotelId() {
        return hotelId;
    }

    // Setter for hotelId
    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    // Getter for hotelName
    public String getHotelName() {
        return hotelName;
    }

    // Setter for hotelName
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    // Getter for hotelAddress
    public String getHotelAddress() {
        return hotelAddress;
    }

    // Setter for hotelAddress
    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    // Getter for totalRooms
    public int getTotalRooms() {
        return totalRooms;
    }

    // Setter for totalRooms
    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    // Getter for availableRooms
    public int getAvailableRooms() {
        return availableRooms;
    }

    // Setter for availableRooms
    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    // Getter for hotelAmenities
    public String getHotelAmenities() {
        return hotelAmenities;
    }

    // Setter for hotelAmenities
    public void setHotelAmenities(String hotelAmenities) {
        this.hotelAmenities = hotelAmenities;
    }

    // Constructors

    // Parameterized constructor
    public Hotel(String hotelName, String hotelAddress, int totalRooms, int availableRooms,
                 String hotelAmenities) {
        super();
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.hotelAmenities = hotelAmenities;
    }
    
   //default class constructor
	public Hotel() {
		super();
		
	}
 }
/*com.hotelreservationsyste.model
com.hotelreservationsyste.Dao
com.hotelreservationsyste.DaoImpl*/