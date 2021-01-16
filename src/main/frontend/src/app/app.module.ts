import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AutocompleteComponent} from './autocomplete/autocomplete.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AddStateComponent} from './add-state/add-state.component';
import {EditStateComponent} from './edit-state/edit-state.component';

@NgModule({
  declarations: [
    AppComponent,
    AutocompleteComponent,
    AddStateComponent,
    EditStateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
