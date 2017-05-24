package uk.ncl.ac.sshephard.simplemicro;

public class Ticket {
	
	private final int id; // unique ticket id
	private final String sport; // type of sport
	private final int day; // day of event
	private final String owner; // name of ticket owner (for booked ticket)
	private final int seat; // seat number

	public Ticket(int id, String sport, int day, String owner, int seat) {
		this.id = id;
		this.sport = sport;
		this.day = day;
		this.owner = owner;
		this.seat = seat;
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
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @return the seat
	 */
	public int getSeat() {
		return seat;
	}
	
}
