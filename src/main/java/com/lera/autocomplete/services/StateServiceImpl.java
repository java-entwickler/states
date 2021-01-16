package com.lera.autocomplete.services;

import com.lera.autocomplete.exception.StateAlreadyExistsException;
import com.lera.autocomplete.exception.StateNotFoundException;
import com.lera.autocomplete.model.State;
import com.lera.autocomplete.repository.StateRepository;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    private StateRepository stateRepository;
    Logger logger = Logger.getLogger(StateServiceImpl.class);

    @Autowired
    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public List<State> getAllStates() {
        List<State> states = stateRepository.findAll();
        return capitaliseStateList(states);
    }

    @Override
    public List<State> getAllStatesStartingWith(String query) {
        List<State> states = stateRepository.findAllByNameStartingWith(query);
        return capitaliseStateList(states);
    }

    @Override
    public State getState(Integer stateId) throws Exception{
        State state = stateRepository.findById(stateId).orElseThrow(() -> {
            String message = "State with id " + stateId + " not found";
            logger.warn(message);
            return new StateNotFoundException(message);
        });
        state.setName(capitaliseWord(state.getName()));
        return state;
    }

    @Override
    public State createState(State state) throws StateAlreadyExistsException {
        state.setName(state.getName().toLowerCase().trim());
        if (stateRepository.findByName(state.getName()) == null) {
            return stateRepository.save(state);
        } else {
            String message = "State " + state.getName() + " already exists.";
            logger.info(message);
            throw new StateAlreadyExistsException(message);
        }
    }

    @Override
    public State modifyState(Integer stateId, State state) throws StateAlreadyExistsException {
        State stateToModify = stateRepository.findById(stateId).get();
        stateToModify.setName(state.getName().toLowerCase().trim());
        if (stateRepository.findByName(state.getName()) == null) {
            return stateRepository.save(stateToModify);
        } else {
            String message = "State " + state.getName() + " already exists.";
            logger.info(message);
            throw new StateAlreadyExistsException(message);
        }
    }

    @Override
    public void deleteState(Integer stateId){
        State state = stateRepository.findById(stateId).orElseThrow(() -> {
            String message = "State with id " + stateId + " not found";
            logger.warn(message);
            return new StateNotFoundException(message);
        });
        stateRepository.delete(state);
    }

    private String capitaliseWord(String word) {
        word = word.substring(0, 1).toUpperCase().concat(word.substring(1));
        while (word.indexOf(" ")!=-1) {

        }
        // while get index " " != null
        // word = substring

        return word.substring(0, 1).toUpperCase().concat(word.substring(1));
    }

    private List<State> capitaliseStateList(List<State> states) {
        states.forEach(state -> state.setName(capitaliseWord(state.getName())));
        return states;
    }
}
