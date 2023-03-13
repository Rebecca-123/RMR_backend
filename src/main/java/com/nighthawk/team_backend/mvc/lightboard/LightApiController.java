package com.nighthawk.team_backend.mvc.lightboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/light")
public class LightApiController {
    @GetMapping("/random")
    public ResponseEntity<JsonNode> getLight() throws JsonMappingException, JsonProcessingException {
        Light light = new Light();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(light.toString()); // this requires exception handling
        return ResponseEntity.ok(json);  // JSON response, see ExceptionHandlerAdvice for throws
    }
    @GetMapping("/custom/board/{rows}/{cols}") // user inputted
    public ResponseEntity<JsonNode> getCustomBoard(@PathVariable int rows, @PathVariable int cols) throws JsonMappingException, JsonProcessingException {
        LightBoard lightBoard = new LightBoard(rows, cols);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(lightBoard.toString()); 
        return ResponseEntity.ok(json);
    }
    @GetMapping("/checkerboard/{rows}")
    public ResponseEntity<JsonNode> getCustomBoard(@PathVariable int rows) throws JsonMappingException, JsonProcessingException {
        CheckerBoard checkerBoard = new CheckerBoard(rows);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(checkerBoard.toString());
        return ResponseEntity.ok(json);
    }    
    @GetMapping("/board")
    public ResponseEntity<JsonNode> getBoard() throws JsonMappingException, JsonProcessingException {
        LightBoard lightBoard = new LightBoard(10, 10);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(lightBoard.toString());
        return ResponseEntity.ok(json);
    }
}
