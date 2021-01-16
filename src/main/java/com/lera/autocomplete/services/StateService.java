package com.lera.autocomplete.services;

import com.lera.autocomplete.exception.StateAlreadyExistsException;
import com.lera.autocomplete.model.State;

import java.util.List;

public interface StateService {
    List<State> getAllStates();
    List<State> getAllStatesStartingWith(String query);
    State getState(Integer stateId) throws Exception;
    State createState(State state) throws StateAlreadyExistsException;
    State modifyState(Integer stateId, State state);
    void deleteState(Integer stateId);
}
