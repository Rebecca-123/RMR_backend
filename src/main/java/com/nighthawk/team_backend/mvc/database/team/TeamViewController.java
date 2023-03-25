package com.nighthawk.team_backend.mvc.database.team;

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
@RequestMapping("/mvc/team")
public class TeamViewController {
    // Autowired enables Control to connect HTML and POJO Object to database easily
    // for CRUD
    @Autowired
    private TeamDetailsService repository;

    @GetMapping("/read")
    public String team(Model model) {
        List<Team> list = repository.listAll();
        model.addAttribute("list", list);
        return "team/read";
    }

    /*
     * The HTML template Forms and TeamForm attributes are bound
     * 
     * @return - template for team form
     * 
     * @param - Team Class
     */
    @GetMapping("/create")
    public String teamAdd(Team team) {
        return "team/create";
    }

    /*
     * Gathers the attributes filled out in the form, tests for and retrieves
     * validation error
     * 
     * @param - Team object with @Valid
     * 
     * @param - BindingResult object
     */
    @PostMapping("/create")
    public String teamSave(@Valid Team team, BindingResult bindingResult) {
        // Validation of Decorated TeamForm attributes
        if (bindingResult.hasErrors()) {
            return "team/create";
        }
        repository.save(team);
        repository.addRoleToTeam(team.getEmail(), "ROLE_STUDENT");
        // Redirect to next step
        return "redirect:/mvc/team/read";
    }

    @GetMapping("/update/{id}")
    public String teamUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("team", repository.get(id));
        return "team/update";
    }

    @PostMapping("/update")
    public String teamUpdateSave(@Valid Team team, BindingResult bindingResult) {
        // Validation of Decorated TeamForm attributes
        if (bindingResult.hasErrors()) {
            return "team/update";
        }
        repository.save(team);
        repository.addRoleToTeam(team.getEmail(), "ROLE_STUDENT");

        // Redirect to next step
        return "redirect:/mvc/team/read";
    }

    @GetMapping("/delete/{id}")
    public String teamDelete(@PathVariable("id") long id) {
        repository.delete(id);
        return "redirect:/mvc/team/read";
    }

    @GetMapping("/search")
    public String team() {
        return "team/search";
    }

}