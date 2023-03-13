package com.nighthawk.team_backend.mvc.StepTracker;


class Step {
    private boolean requiredSteps;
    private int totalSteps;
    private StepTracker object = new StepTracker(1000);
   

   // zero argument constructor
   public Step() {} 

   //getting steps 
   public int getStep() {
      return totalSteps;
   }
   public void setStep(int totalSteps) {
      this.totalSteps = totalSteps;
      this.setdailySteps(totalSteps);
   }


  //daily minimum steps getters and setters 
   public boolean getdailySteps(int totalSteps) {
      return object.dailySteps(totalSteps);
   }
   public void setdailySteps(int totalSteps) {  // this is private to avoid tampering
      this.requiredSteps = object.dailySteps(totalSteps);
   }

   /* isLeapYearToString formatted to be mapped to JSON */
   public String dailyStepsToString(){
      return ( "{ \"Enough:\": "  +this.requiredSteps+ " }" );
   }	
  
   public static void main(String[] args) {
      Step obj = new Step();
      obj.setStep(300);
      System.out.println(obj);
   }
}
