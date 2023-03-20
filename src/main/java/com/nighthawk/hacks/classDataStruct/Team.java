package com.nighthawk.hacks.classDataStruct;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

/*
Adapted from Team POJO, Plain Old Java Object.
 */
public class Team extends Generics{
    // Class data
    private static String classType = "Team";
    public static KeyTypes key = KeyType.title;  // static initializer
	public static void setOrder(KeyTypes key) {Team.key = key;}
	public enum KeyType implements KeyTypes {title, uid, name, person1, person2, person3}

    // Instance data
    private String uid;  // user / Team id
    private String name;
    private String person1;
    private String person2;
    private String person3;
    

    // Constructor with zero arguments
    public Team() {
        super.setType(classType);
    }

    // Constructor used when building object from an API
    public Team(String uid, String name, String person1, String person2, String person3) {
        this();  // runs zero argument constructor
        this.uid = uid;
        this.name = name;
        this.person1 = person1;
        this.person2 = person2;
        this.person3 = person3;
    }

    /* 'Generics' requires getKey to help enforce KeyTypes usage */
	@Override
	protected KeyTypes getKey() { return Team.key; }

    public String getUserID() {
        return uid;
    }

    /* 'Generics' requires toString override
	 * toString provides data based off of Static Key setting
	 */
	@Override
	public String toString() {		
		String output="";
		if (KeyType.uid.equals(this.getKey())) {
			output += this.uid;
		} else if (KeyType.name.equals(this.getKey())) {
			output += this.name;
		} else if (KeyType.person1.equals(this.getKey())) {
			output += this.person1;
		} else if (KeyType.person2.equals(this.getKey())) {
			output += this.person2;
		} else if (KeyType.person3.equals(this.getKey())) {
			output += this.person3;
		} else {
			output = super.getType() + ": " + this.uid + ", " + this.name + ", " + this.person1 + ", " + this.person2 + ", " + this.person3;
		}
		return output;
	}

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPerson1() {
        return person1;
    }

    public void setPerson1(String person1) {
        this.person1 = person1;
    }
    public String getPerson2() {
        return person2;
    }

    public void setPerson2(String person2) {
        this.person2 = person2;
    }

    public String getPerson3() {
        return person3;
    }

    public void setPerson3(String person3) {
        this.person3 = person3;
    }

    // Initialize static test data 
    public static Team[] init() {

        // basics of class construction
        Team p1 = new Team();
        p1.setName("Mini Monkeys");
        p1.setUid("user id??");
        p1.setPerson1("shreya");
        p1.setPerson2("rohan");
        p1.setPerson3("aint nobody");

        Team p2 = new Team();
        p2.setName("Mini-er Monkeys");
        p2.setUid("mads and rebecca test");
        p2.setPerson1("mads");
        p2.setPerson2("becky");
        p2.setPerson3("roro");

        Team p3 = new Team();
        p3.setName("Thomas Edison");
        p3.setUid("toby@gmail.com");
        p3.setPerson1("shreya");
        p3.setPerson2("rohan");
        p3.setPerson3("aint nobody");

        Team p4 = null;
        Team p5 = null;
        try {
            p4 = new Team(
                "monkey",
                "monkey",
                "monkey", 
                "monkey",
                "monkey"
            );
    
            p5 = new Team(
                "monkey1",
                "monkey1",
                "monkey1", 
                "monkey1",
                "monkey1"
                );
        } catch (Exception e) {
        }

        // Array definition and data initialization
        Team persons[] = {p1, p2, p3, p4, p5};
        return(persons);
    }

    public static void main(String[] args) {
        // obtain Team from initializer
        Team persons[] = init();
        Team.setOrder(Team.KeyType.title);

        // iterate using "enhanced for loop"
        for( Team Team : persons ) {
            System.out.println(Team);  // print object
        }
    }

}