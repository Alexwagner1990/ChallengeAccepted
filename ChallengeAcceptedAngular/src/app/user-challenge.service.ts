import { UserChallenge } from './models/user-challenge';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { throwError } from 'rxjs';
import {catchError} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class UserChallengeService {

  url = 'http://localhost:8080/api';


  acceptingAMarketChallenge(acceptedChallenge) {
    return this.http.post<UserChallenge>(`${this.url}/challenge/${acceptedChallenge.id}/accept`, acceptedChallenge).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  acceptingAPersonalChallenge(acceptedChallenge) {
    this.http.patch<UserChallenge>(`${this.url}/challenge/${acceptedChallenge.id}/accept`, acceptedChallenge).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  constructor(private http: HttpClient) { }
}
