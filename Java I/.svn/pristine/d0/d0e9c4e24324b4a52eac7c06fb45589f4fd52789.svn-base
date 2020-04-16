package studio8;

public class Appointment {
	public final Date date;
	public final Time time;
	
	public Appointment(Date date, Time time){
		this.date = date;
		this.time = time;
	}
	
	public Date getDate(){
		return date;
	}
	
	public Time getTime(){
		return time;
	}
	
	public String toString(){
		return time.toString() + " on " + date.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	
	public static void main(String[] args) {
		
	}
}
