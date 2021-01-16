package com.lera.autocomplete.services;

import com.lera.autocomplete.model.State;
import com.lera.autocomplete.repository.StateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StateServiceTest {
    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private StateServiceImpl stateService;

    @Test
    void getAllStates() {
        List<State> states = Arrays.asList(new State(1, "Detroit"), new State(2, "Michigan"));
        given(stateRepository.findAll()).willReturn(states);
        assertThat(stateService.getAllStates(), hasSize(2));
        verify(stateRepository, times(1)).findAll();
    }

    @Test
    void getAllStatesStartingWith() {
        List<State> states = Arrays.asList(new State(1, "Florida"));
        given(stateRepository.findAllByNameStartingWith("f")).willReturn(states);
        assertThat(stateService.getAllStatesStartingWith("f"), hasSize(1));
        verify(stateRepository, times(1)).findAllByNameStartingWith("f");
    }

    @Test
    void createStateWhenNotDuplicated() {
        State state = new State(40, "montana");
        given(stateRepository.save(ArgumentMatchers.any())).willReturn(state);
        assertThat(stateService.createState(new State(40, " MoNtana ")).getName(), equalTo("montana"));
    }

    @Test
    void deleteStateWhenStateExists() {
        State state = new State(26, "Missouri");
        given(stateRepository.findById(26)).willReturn(Optional.of(state));
        stateService.deleteState(26);
        verify(stateRepository, times(1)).delete(state);
    }

}
