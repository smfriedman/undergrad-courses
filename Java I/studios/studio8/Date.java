package studio8;

import java.util.HashSet;
import java.util.LinkedList;

public class Date {
	public final int day;
	public final int month;
	public final int year;
	public final boolean holiday;
	
	public Date(int day, int month, int year, boolean holiday){
		this.day = day;
		this.month = month;
		this.year = year;
		this.holiday = holiday;
	}
	
	public int getDay(){
		return day;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getYear(){
		return year;
	}
	
	public String toString(){
		if (holiday == true) return month + "/" + day + "/" + year + " holiday.";
		return month + "/" + day + "/" + year + " not holiday.";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + month;
		result = prime * result + year;
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
		Date other = (Date) obj;
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	public static void main(String[] args) {
		Date a = new Date(31, 5, 2015, true);
		Date b = new Date(6, 4, 190, false);
		Date c = new Date(6, 4, 190, true);
		Date d = new Date(1, 1, 1, true);
		Date e = new Date (16, 10, 1995, true);

		System.out.println(a.toString());
		System.out.println(b.toString());
		System.out.println(a.equals(b));
		System.out.println(b.equals(c));
		
		LinkedList<Date> list = new LinkedList<Date>();
		list.add(a);
		list.add(b);
		list.add(d);
		list.add(e);
		System.out.println(list);
		
		LinkedList<Date> list2 = new LinkedList<Date>();
		list2.add(b);
		list2.add(c);
		list2.add(b);
		System.out.println(list2);
		
		HashSet<Date> set = new HashSet<Date>();
		set.add(b);
		set.add(c);
		set.add(b);
		System.out.println(set);
		
	}

}
