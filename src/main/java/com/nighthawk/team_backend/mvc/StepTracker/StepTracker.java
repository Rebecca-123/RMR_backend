package com.nighthawk.team_backend.mvc.StepTracker;

import java.util.ArrayList;


    public class StepTracker {
        ArrayList<Integer> steps = new ArrayList<Integer>(); 
        private int minDailySteps;
        private int totalDays;
        private int activeDays; 
        private int totalSteps;
        private double averageSteps;
        private boolean requiredSteps;  
        
        public StepTracker (int x) {
            minDailySteps = x;
            totalDays = 0;
            requiredSteps = false; 
        }
    
        public int activeDays() {
           return activeDays;
        }
        
        
        public boolean dailySteps(int totalSteps){
            steps.add(totalSteps);
            if (totalSteps>=minDailySteps){
                requiredSteps = true;
            }
            System.out.println(steps);
            return requiredSteps;
        }
        
        
        public double averageSteps() {
            
            double sum = 0;
            double days = steps.size();

            if(totalDays == 0) {
                return 0.0;
            }
            for (int i=0; i<days;i++){
                sum = sum + steps.get(i);
            }

            averageSteps= sum/days;
            return averageSteps;

        
        }
        public static void main(String[] args){
            StepTracker obj = new StepTracker(300);
            System.out.println(obj.dailySteps(30000));
            System.out.println(obj.dailySteps(900000));
            System.out.println(obj.activeDays());
            System.out.println(obj.averageSteps());
        
        }
    }
