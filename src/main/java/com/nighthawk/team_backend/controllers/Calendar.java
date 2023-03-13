package com.nighthawk.team_backend.controllers;
/* MVC code that shows defining a simple Model, calling View, and this file serving as Controller
 * Web Content with Spring MVCSpring Example: https://spring.io/guides/gs/serving-web-con
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nighthawk.team_backend.mvc.calendar.APCalendar;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class Calendar {

    // @GetMapping handles GET request for /calendar, maps it to calendar() method
    @GetMapping("/calendar")
    // @RequestParam handles variables binding to frontend, defaults, etc
    public String calendar(@RequestParam(name="year", required=false, defaultValue="2022") int year, Model model) {

        boolean isLeapYear = APCalendar.isLeapYear(year);
        int dayOfWeek = APCalendar.dayOfWeek(1, 1, year);

        // model attributes are visible to Thymeleaf when HTML is "pre-processed"
        model.addAttribute("year", year);
        model.addAttribute("isLeapYear", isLeapYear);
        model.addAttribute("dayOfWeek", dayOfWeek);

        // load HTML VIEW (calendar.html)
        return "calendar"; 

    }

    // @GetMapping("/calendar")
    //  // @RequestParam handles variables binding to frontend, defaults, etc
    //  public String leapYearCount(@RequestParam(name="year1", required=false, defaultValue="2000") int year1, @RequestParam(name="year2", required=false, defaultValue="2022")int year2, Model model) {

    //     int leapYearCount = APCalendar.numberOfLeapYears(year1, year2);

    //     // model attributes are visible to Thymeleaf when HTML is "pre-processed"
    //     model.addAttribute("year1", year1);
    //     model.addAttribute("year2", year2);
    //     model.addAttribute("leapYearCount", leapYearCount);

    //     // load HTML VIEW (calendar.html)
    //     return "calendar"; 

    // }

}