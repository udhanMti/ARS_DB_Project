<<<<<<< HEAD:src/Flight.java

public class Flight {
=======
public class PastFlight {
>>>>>>> 67e1b920000f044d2555bfb8e95bebce39465e2d:src/PastFlight.java
	private String flight_id;
    private String schedule_id;
    private String passenger_count;
    private String date;
    private String airplane_id;
	public String getAirplane_id() {
		return airplane_id;
	}
	public void setAirplane_id(String airplane_id) {
		this.airplane_id = airplane_id;
	}
	public String getFlight_id() {
		return flight_id;
	}
	public void setFlight_id(String flight_id) {
		this.flight_id = flight_id;
	}
	public String getSchedule_id() {
		return schedule_id;
	}
	public void setSchedule_id(String schedule_id) {
		this.schedule_id = schedule_id;
	}
	public String getPassenger_count() {
		return passenger_count;
	}
	public void setPassenger_count(String passenger_count) {
		this.passenger_count = passenger_count;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
