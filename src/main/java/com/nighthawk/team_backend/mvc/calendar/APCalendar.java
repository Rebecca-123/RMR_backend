package com.nighthawk.team_backend.mvc.calendar;

// Prototype Implementation

public class APCalendar {

    /** Returns true if year is a leap year and false otherwise.
     * isLeapYear(2019) returns False
     * isLeapYear(2016) returns True
     */          
    public static boolean isLeapYear(int year) {
        if (year % 4 == 0){
            return true;
        }

        return false;
        }

    static int dayofyear(int d, int m, int y){
        int t[] = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 };
        if (m < 3)
            y--;
        return (y + y / 4 - y / 100 + y / 400 + t[m - 1]+ d) % 7;
    }
     
        
    /** Returns the value representing the day of the week 
     * 0 denotes Sunday, 
     * 1 denotes Monday, ..., 
     * 6 denotes Saturday. 
     * firstDayOfYear(2019) returns 2 for Tuesday.
    */
    static int firstDayOfYear(int year) {
        // implementation not shown
        return dayofyear(1, 1, year);
        }


    /** Returns n, where month, day, and year specify the nth day of the year.
     * This method accounts for whether year is a leap year. 
     * dayOfYear(1, 1, 2019) return 1
     * dayOfYear(3, 1, 2017) returns 60, since 2017 is not a leap year
     * dayOfYear(3, 1, 2016) returns 61, since 2016 is a leap year. 
    */ 
    public static int dayOfYear(int month, int day, int year) {
        int day_number = 0;
        if (isLeapYear(year)){
            day_number +=1;
        }

        //jan
        if (month ==1){
            day_number = day_number + day;
        }
        //feb
        else if (month ==2){
            day_number = day_number + (31 + day);
        }
        else if (month ==3){
            day_number = day_number + (31 + 28 + day);
        }
        else if (month ==4){
            day_number = day_number + (31 + 28 + 31+ day);
        }
        else if (month ==5){
            day_number = day_number + (31 + 28 + 31+ 30 + day);
        }
        else if (month ==6){
            day_number = day_number + (31 + 28 + 31+ +30 + 31+ day);
        }
        else if (month ==7){
            day_number = day_number + (31 + 28 + 31+ +30 + 31+ 30 + day);
        }
        else if (month ==8){
            day_number = day_number + (31 + 28 + 31+ +30 + 31+ 30 + 31+ day);
        }
        else if (month ==9){
            day_number = day_number + (31 + 28 + 31+ +30 + 31+ 30 + 31+ 31 + day);
        }
        else if (month ==10){
            day_number = day_number + (31 + 28 + 31+ +30 + 31+ 30 + 31+ 31 + 30 + day);
        }
        else if (month ==11){
            day_number = day_number + (31 + 28 + 31+ +30 + 31+ 30 + 31+ 31 + 30 + 31 + day);
        }
        else if (month ==12){
            day_number = day_number + (31 + 28 + 31+ +30 + 31+ 30 + 31+ 31 + 30 + 31 + 30 + day);
        }


        return day_number;
    }

    /** Returns the number of leap years between year1 and year2, inclusive.
     * Precondition: 0 <= year1 <= year2
    */ 
    public static int numberOfLeapYears(int year1, int year2) {
         // to be implemented in part (a)
    
        int leap_counter = 0;
        for (int i=0; i<=(year2-year1);i++){
            if (isLeapYear(year1 + i)){
                leap_counter +=1;
            }
        }
        return leap_counter;
       
        

    }

    /** Returns the value representing the day of the week for the given date
     * Precondition: The date represented by month, day, year is a valid date.
    */
    public static int dayOfWeek(int month, int day, int year) { 
        // to be implemented in part (b)
        int day_of_the_year = dayOfYear(month, day, year);
        int remainder = day_of_the_year % 7;
        int first_day = firstDayOfYear(year);
        int day_of_week = first_day + remainder - 1;
        if (day_of_week>6){
            day_of_week = 6;
        }
        else if (day_of_week<0){
            day_of_week = 0;
        }
        return day_of_week;
        
        }


        

    /** Tester method */
    public static void main(String[] args) {
        // Private access modifiers
        System.out.println("firstDayOfYear: " + APCalendar.firstDayOfYear(2022));
        System.out.println("dayOfYear: " + APCalendar.dayOfYear(1, 1, 2022));

        // Public access modifiers
        System.out.println("isLeapYear: " + APCalendar.isLeapYear(2022));
        System.out.println("numberOfLeapYears: " + APCalendar.numberOfLeapYears(2000, 2022));
        System.out.println("dayOfWeek: " + APCalendar.dayOfWeek(1, 1, 2022));
    }

}