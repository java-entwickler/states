import {Component, OnInit} from '@angular/core';
import {State} from "../shared/state";
import {StateService} from "../services/state.service";
import {AppComponent} from "../app.component";
import {EditStateComponent} from "../edit-state/edit-state.component";

@Component({
  selector: 'app-autocomplete',
  templateUrl: './autocomplete.component.html',
  styleUrls: ['./autocomplete.component.css']
})
export class AutocompleteComponent implements OnInit {
  static states: State[] = [];
  selectedState: State;

  query: string = "";

  constructor(private stateService: StateService) {
  }

  get states() {
    return AutocompleteComponent.states;
  }


  ngOnInit() {
    this.stateService.getAllStates().subscribe(data => {
      AutocompleteComponent.states = data;
    });
  }

  refreshStates() {
    if (this.query != "") {
      this.stateService.getStatesStartingWith(this.query).subscribe(data => {
        AutocompleteComponent.states = data;
      });
    } else {
      this.stateService.getAllStates().subscribe(data => {
        AutocompleteComponent.states = data;
      });
    }
  }

  selectState(index: number) {
    if (this.selectedState != null && this.selectedState.stateId == AutocompleteComponent.states[index].stateId) {
      this.selectedState = null;
      AppComponent.selectedState = null;
      EditStateComponent.state = null;
    }
    else if (this.selectedState != null && this.selectedState.stateId != AutocompleteComponent.states[index].stateId) {
      this.selectedState = AutocompleteComponent.states[index];
      AppComponent.selectedState = AutocompleteComponent.states[index];

      EditStateComponent.state = new State();
      EditStateComponent.state.stateId = AutocompleteComponent.states[index].stateId;
      EditStateComponent.state.name = AutocompleteComponent.states[index].name;
      EditStateComponent.duplicateError = false;
    }
    else if (this.selectedState == null) {
      this.selectedState = AutocompleteComponent.states[index];
      AppComponent.selectedState = AutocompleteComponent.states[index];

      EditStateComponent.state = new State();
      EditStateComponent.state.stateId = AutocompleteComponent.states[index].stateId;
      EditStateComponent.state.name = AutocompleteComponent.states[index].name;
      EditStateComponent.duplicateError = false;
    }
  }

}
