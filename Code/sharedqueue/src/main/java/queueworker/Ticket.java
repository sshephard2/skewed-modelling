package queueworker;

import java.io.Serializable;

/**
 * Ticket entity
 * @author Stephen Shephard
 */
public final class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;
	private String sport; // type of sport
	private String owner; // name of ticket owner (for booked ticket)
	private int day; // day of event
	private int id; // unique ticket id
	private int seat; // seat number
	
	/**
	 * No-arg dummy constructor for Jackson JSON serialization
	 */
	public Ticket() {
		// Do nothing
	}

	/**
	 * Constructor for ticket POJO
	 * @param id - unique ticket id
	 * @param sport - type of sport (Athletics, Cycling, Diving)
	 * @param day - day of event (day 1 to N)
	 * @param seat - seat number
	 * @param owner - name of ticket owner (for booked tickets)
	 */
	public Ticket(int id, String sport, int day, int seat, String owner) {
		this.id = id;
		this.sport = sport;
		this.day = day;
		this.seat = seat;
		this.owner = owner;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the sport
	 */
	public String getSport() {
		return sport;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @return the seat
	 */
	public int getSeat() {
		return seat;
	}
	
	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	
}