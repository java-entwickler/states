import {Component} from '@angular/core';
import {StateService} from "./services/state.service";
import {State} from "./shared/state";
import {AutocompleteComponent} from "./autocomplete/autocomplete.component";
import {EditStateComponent} from "./edit-state/edit-state.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  static selectedState: State = null;

  constructor(private stateService: StateService) {
  }

  get selectedState() {
    return AppComponent.selectedState;
  }

  deleteState() {
    console.log("Deleting state");
    this.stateService.deleteState(AppComponent.selectedState.stateId).subscribe(result => {
      // refresh list
      this.stateService.getAllStates().subscribe(data => {
        AutocompleteComponent.states = data;
      });
      // set selected to null in ALL CLASSES
      AppComponent.selectedState = null;
      EditStateComponent.state = null;
    });
  }
}
