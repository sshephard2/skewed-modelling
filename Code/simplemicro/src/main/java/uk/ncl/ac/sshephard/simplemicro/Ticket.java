package uk.ncl.ac.sshephard.simplemicro;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Ticket {
	
	@PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	private final int id; // unique ticket id
	@PrimaryKeyColumn(name = "sport", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private final String sport; // type of sport
	private final int day; // day of event
	private final int seat; // seat number
	private final String owner; // name of ticket owner (for booked ticket)

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
