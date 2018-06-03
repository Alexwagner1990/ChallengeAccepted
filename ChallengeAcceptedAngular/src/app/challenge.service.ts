import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Challenge } from './models/challenge';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChallengeService {
  url = 'localhost:8080/api';

  showOneChallenge(id) {
    return this.http.get<Challenge>(`${this.url}`).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  constructor(private http: HttpClient) { }
}
