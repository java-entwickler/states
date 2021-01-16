package com.lera.autocomplete.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lera.autocomplete.exception.StateAlreadyExistsException;
import com.lera.autocomplete.exception.StateNotFoundException;
import com.lera.autocomplete.model.State;
import com.lera.autocomplete.services.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class StateControllerTest {

    private MockMvc mvc;

    @Mock
    private StateService stateService;

    @InjectMocks
    private StateController stateController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(stateController).build();
    }

    @Test
    void testGetAllStates() throws Exception {
        // given
        given(stateService.getAllStates())
                .willReturn(Arrays.asList(new State(1, "Alabama"),
                        new State(2, "California")));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/states")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(objectMapper.writeValueAsString(
                        Arrays.asList(new State(1, "Alabama"),
                                new State(2, "California"))));
    }

    @Test
    void getAllStatesStartingWithWhenQueryIsNotEmpty() throws Exception {
        // given
        given(stateService.getAllStatesStartingWith("f"))
                .willReturn(Arrays.asList(new State(1, "Florida")));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/states/query/f").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(objectMapper.writeValueAsString(
                        Arrays.asList(new State(1, "Florida"))));
    }

    @Test
    void getAllStatesStartingWithWhenQueryIsEmpty() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/states/query/").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void getStateWhenIdExists() throws Exception {
        // given
        given(stateService.getState(1))
                .willReturn(new State(1, "Alabama"));
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/states/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                objectMapper.writeValueAsString(new State(1, "Alabama")));
    }

    @Test
    void getStateWhenIdDoesNotExist() throws Exception{
        // given
        given(stateService.getState(100))
                .willThrow(StateNotFoundException.class);
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/states/100").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void testCreateStateWhenStateIsNotDuplicated() throws Exception{
        // given
        State state = new State(2, "South Carolina");
        given(stateService.createState(any())).willReturn(new State(2, "South Carolina"));

        // when
        MockHttpServletResponse response = mvc.perform(
                post("/api/states").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(state)))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void testCreateStateWhenStateIsDuplicated() throws Exception {
        // given
        given(stateService.createState(any()))
                .willThrow(StateAlreadyExistsException.class);
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/api/states").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new State(1, "California"))))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void modifyStateWhenNotDuplicated() throws Exception{
        // given
        State state = new State(32, "Georgia");
        given(stateService.modifyState(any(), any())).willReturn(state);

        // when
        MockHttpServletResponse response = mvc.perform(
                put("/api/states/32").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(state)))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void modifyStateWhenStateIsDuplicated() throws Exception{
        // given
        given(stateService.modifyState(any(), any())).willReturn(null);

        // when
        MockHttpServletResponse response = mvc.perform(
                put("/api/states/32").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new State(23, "Florida"))))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void testDeleteStateWhenIdExists() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                delete("/api/states/1")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
