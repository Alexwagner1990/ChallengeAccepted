import { Challenge } from './models/challenge';
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

  acceptingAMarketChallenge(dto) {
    // const headers = new HttpHeaders({'Content-Type': 'application/json'});

    return this.http.post<UserChallenge>(`${this.url}/challenges/${dto.challengeId}/accept`, dto).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  acceptingAPersonalChallenge(dto) {
    this.http.patch<UserChallenge>(`${this.url}/challenges/${dto.challengeId}/accept`, dto).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  hasUserAcceptedChallenge(dto) {
    return this.http.get<UserChallenge>(`${this.url}/user/challenges/${dto.challengeId}/user/${dto.acceptorId}/check`).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  updateUserWinner(cid, uid, challenge) {
    return this.http.patch<UserChallenge>(`${this.url}/challenges/${cid}/user/${uid}/`, challenge).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  // calculateSkills(challenge) {
  //   return this.http.patch<UserChallenge>(`${this.url}/challenges/${challenge.id}/userskills`, challenge).pipe(
  //     catchError((err: any) => {
  //       console.log(err);
  //       return throwError(err);
  //     })
  //   );
  // }


  constructor(private http: HttpClient) { }
}
