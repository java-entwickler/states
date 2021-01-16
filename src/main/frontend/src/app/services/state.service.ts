import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {State} from "../shared/state";

@Injectable({
  providedIn: 'root'
})
export class StateService {
  private url = "http://localhost:8080/api/states";

  constructor(private http: HttpClient) {
  }

  getAllStates(): Observable<State[]> {
    return this.http.get<State[]>(this.url);
  }

  getStatesStartingWith(query: string): Observable<State[]> {
    return this.http.get<State[]>(this.url + "/query/" + query);
  }

  createState(state: State): Observable<State> {
    return this.http.post<State>(this.url, state);
  }

  public modifyState(state: State): Observable<State> {
    return this.http.put<State>(this.url + '/' + state.stateId, state);
  }

  deleteState(stateId: number): Observable<any> {
    return this.http.delete(this.url + '/' + stateId);
  }
}
