package com.nighthawk.team_backend.mvc.calculator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// Calculator api, endpoint: /api/calculator/
@RestController
@RequestMapping("/api/calculator")
public class CalculatorApiController {

    /** GET calculate endpoint
     * ObjectMapper throws exceptions on bad JSON
     *  @throws JsonProcessingException
     *  @throws JsonMappingException
     */
    @GetMapping("/calculate/{expression}")
    public ResponseEntity<JsonNode> calculate(@PathVariable String expression) throws JsonMappingException, JsonProcessingException {
    
        System.out.println("Expression: " + expression);
        Calculator calc = new Calculator(expression);

        ObjectMapper mapper = new ObjectMapper(); 
        JsonNode json = mapper.readTree(calc.toString());

        return ResponseEntity.ok(json);
    }
}

