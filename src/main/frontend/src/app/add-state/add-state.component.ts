import {Component} from '@angular/core';
import {StateService} from "../services/state.service";
import {State} from "../shared/state";
import {AutocompleteComponent} from '../autocomplete/autocomplete.component';

@Component({
  selector: 'app-add-state',
  templateUrl: './add-state.component.html',
  styleUrls: ['./add-state.component.css']
})
export class AddStateComponent {
  state: State = new State();
  duplicateError = false;

  constructor(private stateService: StateService) {
  }

  onSubmit() {
    this.stateService.createState(this.state).subscribe(data => {
      if (data != null) {
        this.stateService.getAllStates().subscribe(data => {
          AutocompleteComponent.states = data;
        });
        this.state.name = "";
        this.duplicateError=false;
      } else {
        this.duplicateError = true;
      }
    });
  }
}
