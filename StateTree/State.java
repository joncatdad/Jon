/**
 *	The object to store US state information.
 *
 *	@author	Jonathan Chen
 *	@since	May 21, 2025
 */
public class State implements Comparable<State>{
	private String name;			// state name
	private String abbreviation;	// state abbreviation
	private int population;			// state population
	private int area;				// state area in sq miles
	private int reps;				// number of House Reps
	private String capital;			// state capital city
	private int month;				// month joined Union
	private int day;				// day joined Union
	private int year;				// year joined Union
	public State(String name, String abbr, int population, int area, int reps, String capital, int month, int day, int year){
		this.name = name;
		this.abbreviation = abbr;
		this.population = population;
		this.area = area;
		this.reps = reps;
		this.capital = capital;
		this.month = month;
		this.day = day;
		this.year = year;
	}
	public State(String name){
		this.name = name.toLowerCase();  // store lowercase for comparison
	}
	@Override
	public int compareTo(State other){
		return this.name.toLowerCase().compareTo(other.name.toLowerCase());
	}
	public String getName(){
		return name;
	}
	@Override
	public String toString(){
		return String.format("%-20s %-5s %,10d %,8d %3d   %-20s %2d %2d %4d",
			name, abbreviation, population, area, reps, capital, month, day, year);
	}
}
