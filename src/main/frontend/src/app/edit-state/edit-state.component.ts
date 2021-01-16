import {Component, OnInit} from '@angular/core';
import {State} from "../shared/state";
import {StateService} from "../services/state.service";
import {AutocompleteComponent} from "../autocomplete/autocomplete.component";

@Component({
  selector: 'app-edit-state',
  templateUrl: './edit-state.component.html',
  styleUrls: ['./edit-state.component.css']
})
export class EditStateComponent implements OnInit {
  static state: State;
  static duplicateError=false;

  constructor(private stateService: StateService) { }

  get state() {
    return EditStateComponent.state;
  }
  get duplicateError () {
    return EditStateComponent.duplicateError;
  }

  ngOnInit() {
  }

  onSubmit() {
    this.stateService.modifyState(EditStateComponent.state).subscribe(result => {
      if (result != null) {
        this.stateService.getAllStates().subscribe(data => {
          AutocompleteComponent.states = data;
        });
        EditStateComponent.duplicateError = false;
      } else {
        EditStateComponent.duplicateError = true;
      }
    });
  }
}
