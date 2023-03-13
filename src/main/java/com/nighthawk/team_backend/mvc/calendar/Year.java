package com.nighthawk.team_backend.mvc.calendar;
//import com.nighthawk.spring_portfolio.mvc.calendar.APCalendar.firstDayOfYear;

/** Simple POJO 
 * Used to Interface with APCalendar
 * The toString method(s) prepares object for JSON serialization
 * Note... this is NOT an entity, just an abstraction
 */
class Year {
   private int year;
   private boolean isLeapYear;
   private int firstDayOfYear;
   private int dayOfWeek;
   private int month;
   private int day;
   private int year1;
   private int year2;
   private int numberOfLeapYears;
   

   // zero argument constructor
   public Year() {} 

   /* year getter/setters */
   public int getYear() {
      return year;
   }
   public void setYear(int year) {
      this.year = year;
      this.setIsLeapYear(year);
      this.setfirstDayOfYear(year);
      
   }

   public void setDate(int month, int day, int year) {
      this.year = year;
      this.month = month;
      this.day = day;
      this.setdayOfWeek(month, day, year);
   }

   public void setYears(int year1, int year2) {
      this.year1 = year1;
      this.year2 =year2;
      this.setnumberOfLeapYears(year1, year2);
      
   }

   



   /* isLeapYear getter/setters */
   public boolean getIsLeapYear(int year) {
      return APCalendar.isLeapYear(year);
   }
   private void setIsLeapYear(int year) {  // this is private to avoid tampering
      this.isLeapYear = APCalendar.isLeapYear(year);
   }

   /* isLeapYearToString formatted to be mapped to JSON */
   public String isLeapYearToString(){
      return ( "{ \"year\": "  +this.year+  ", " + "\"isLeapYear\": "  +this.isLeapYear+ " }" );
   }	
    /* standard toString placeholder until class is extended */
    public String toString() { 
      return isLeapYearToString(); 
   }




   /* firstDayOfYear getter/setters */
   public int getfirstDayOfYear(int year) {
      return APCalendar.firstDayOfYear(year);
   }

   private void setfirstDayOfYear(int year) {  // this is private to avoid tampering
      this.firstDayOfYear = APCalendar.firstDayOfYear(year);
   }
  
   public String firstDayOfYearToString(){
      return ( "{ \"year\": "  +this.year+  ", " + "\"firstDayOfYear\": "  +this.firstDayOfYear+ " }" );
   }	






   /* firstDayOfYear getter/setters */
   public int getnumberOfLeapYears(int year) {
      return APCalendar.numberOfLeapYears(year1, year2);
   }

   private void setnumberOfLeapYears(int year1, int year2) {  // this is private to avoid tampering
      this.numberOfLeapYears = APCalendar.numberOfLeapYears(year1, year2);
   }
  
   public String numberOfLeapYearsToString(){
      return ( "{ \"years\": "  +  "\"" + this.year1+ "-" + this.year2+ "\"" + ", " + "\"numberOfLeapYears\": "  +this.numberOfLeapYears+ " }" );
   }	


   




   /* firstDayOfYear getter/setters */
   public int getdayOfWeek(int month, int day, int year) {
      return APCalendar.dayOfWeek(month, day, year);
   }

   private void setdayOfWeek(int month, int day, int year) {  // this is private to avoid tampering
      this.dayOfWeek = APCalendar.dayOfWeek(month, day, year);
   }
  
   public String dayOfWeekToString(){
      return ( "{ \"Date\": "  + "\"" + this.month + "-" + this.day +"-" +  this.year + "\"" +  ", " + "\"dayOfWeek\": "  + this.dayOfWeek + " }" );
   }	





   public static void main(String[] args) {
      Year year_obj = new Year();
      //year_obj.setYear(2022);
      //year_obj.setDate(1,17,2022);
      year_obj.setYears(2016,2022);
      System.out.println(year_obj.numberOfLeapYearsToString());
   }
}