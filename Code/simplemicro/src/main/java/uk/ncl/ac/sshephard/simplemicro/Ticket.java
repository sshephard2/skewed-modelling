package uk.ncl.ac.sshephard.simplemicro;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Ticket entity
 * @author Stephen Shephard
 */
@Table
public final class Ticket {

	@PrimaryKeyColumn(name = "sport", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private final String sport; // type of sport
	@PrimaryKeyColumn(name = "owner", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	private final String owner; // name of ticket owner (for booked ticket)
	@PrimaryKeyColumn(name = "day", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
	private final int day; // day of event
	@PrimaryKeyColumn(name = "id", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
	private final int id; // unique ticket id
	private final int seat; // seat number

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
