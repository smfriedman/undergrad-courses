package studio8;

public class Time {
	public final int hour;
	public final int minute;
	public final boolean twelveHour;
	
	public Time(int hour, int minute, boolean twelveHour){
		this.hour = hour;
		this.minute = minute;
		this.twelveHour = twelveHour;
	}

	public int getHour(){
		return hour;
	}
	
	public int getMinute(){
		return minute;
	}
	
	public boolean getTwelveHour(){
		return twelveHour;
	}
	
	public String toString(){
		if (twelveHour == true) {
			if (hour % 12 == 0) return 12 + ":" + minute;
			else return (hour % 12) + ":" + minute;
		}
		return hour + ":" + minute;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hour;
		result = prime * result + minute;
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
		Time other = (Time) obj;
		if (hour != other.hour)
			return false;
		if (minute != other.minute)
			return false;
		return true;
	}
	
	public static void main(String[] args) {
		Time a = new Time(20, 14, true);
		Time b = new Time(20, 14, false);
		Time c = new Time(24, 14, true);
		
		System.out.println(a.toString());
		System.out.println(b.toString());
		System.out.println(c.toString());
		System.out.println(a.equals(b));
		System.out.println(b.equals(c));
	}

}
