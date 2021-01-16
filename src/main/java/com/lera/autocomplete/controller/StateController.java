package com.lera.autocomplete.controller;

import com.lera.autocomplete.model.State;
import com.lera.autocomplete.services.StateService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
@CrossOrigin("http://localhost:4200")
public class StateController {
    private StateService stateService;
    private Logger logger = Logger.getLogger(StateController.class);

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("")
    public List<State> getAllStates() {
        return stateService.getAllStates();
    }

    @GetMapping("/query/{query}")
    public List<State> getAllStatesStartingWith(@PathVariable String query) {
        return stateService.getAllStatesStartingWith(query);
    }

    @GetMapping("/{stateId}")
    public State getState(@PathVariable Integer stateId) throws Exception {
        return stateService.getState(stateId);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public State createState(@RequestBody State state) {
        return stateService.createState(state);
    }

    @PutMapping("/{stateId}")
    public ResponseEntity<State> modifyState(@PathVariable Integer stateId, @RequestBody State state) {
        State modifiedState = stateService.modifyState(stateId, state);
        if (modifiedState != null) {
            return new ResponseEntity<>(modifiedState, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{stateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteState(@PathVariable Integer stateId) {
        stateService.deleteState(stateId);
    }

}
