package com.lera.autocomplete.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StateTest {
    State state = new State();

    @Test
    void getStateId() {
        Integer stateId = 48;
        state.setStateId(stateId);
        assertEquals(stateId, state.getStateId());
    }

    @Test
    void getName() {
        String name = "Detroit";
        state.setName(name);
        assertEquals(name, state.getName());
    }
}
