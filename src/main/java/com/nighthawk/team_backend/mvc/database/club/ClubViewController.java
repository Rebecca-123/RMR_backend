package com.nighthawk.team_backend.mvc.database.club;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// Built using article: https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html
// or similar: https://asbnotebook.com/2020/04/11/spring-boot-thymeleaf-form-validation-example/
@Controller
@RequestMapping("/mvc/club")
public class ClubViewController {
    // Autowired enables Control to connect HTML and POJO Object to database easily
    // for CRUD
    @Autowired
    private ClubDetailsService repository;

    @GetMapping("/read")
    public String club(Model model) {
        List<Club> list = repository.listAll();
        model.addAttribute("list", list);
        return "club/read";
    }

    /*
     * The HTML template Forms and ClubForm attributes are bound
     * 
     * @return - template for club form
     * 
     * @param - Club Class
     */
    @GetMapping("/create")
    public String clubAdd(Club club) {
        return "club/create";
    }

    /*
     * Gathers the attributes filled out in the form, tests for and retrieves
     * validation error
     * 
     * @param - Club object with @Valid
     * 
     * @param - BindingResult object
     */
    @PostMapping("/create")
    public String clubSave(@Valid Club club, BindingResult bindingResult) {
        // Validation of Decorated ClubForm attributes
        if (bindingResult.hasErrors()) {
            return "club/create";
        }
        repository.save(club);
        repository.addRoleToClub(club.getEmail(), "ROLE_STUDENT");
        // Redirect to next step
        return "redirect:/mvc/club/read";
    }

    @GetMapping("/update/{id}")
    public String clubUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("club", repository.get(id));
        return "club/update";
    }

    @PostMapping("/update")
    public String clubUpdateSave(@Valid Club club, BindingResult bindingResult) {
        // Validation of Decorated ClubForm attributes
        if (bindingResult.hasErrors()) {
            return "club/update";
        }
        repository.save(club);
        repository.addRoleToClub(club.getEmail(), "ROLE_STUDENT");

        // Redirect to next step
        return "redirect:/mvc/club/read";
    }

    @GetMapping("/delete/{id}")
    public String clubDelete(@PathVariable("id") long id) {
        repository.delete(id);
        return "redirect:/mvc/club/read";
    }

    @GetMapping("/search")
    public String club() {
        return "club/search";
    }

}