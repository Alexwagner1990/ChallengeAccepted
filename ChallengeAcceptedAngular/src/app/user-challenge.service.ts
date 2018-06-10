import { Challenge } from './models/challenge';
import { UserChallenge } from './models/user-challenge';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { throwError } from 'rxjs';
import {catchError} from 'rxjs/operators';
import { AuthService } from './auth.service';
import { environment } from '../environments/environment';
import { UserSkill } from './models/user-skill';


@Injectable({
  providedIn: 'root'
})
export class UserChallengeService {

  private baseUrl = environment.baseUrl;
  private url = this.baseUrl + 'api';

  acceptingAMarketChallenge(dto) {
    // const headers = new HttpHeaders({'Content-Type': 'application/json'});(don't seem to need this)
    // Get token
    const token = this.authService.getToken();
    // Send token as Authorization header (this is spring security convention for basic auth)
    const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);
    if (this.authService.checkLogin()) {
      return this.http.post<UserChallenge>(`${this.url}/challenges/${dto.challengeId}/accept`, dto, {headers}).pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(err);
        })
      );
    }
  }

  acceptingAPersonalChallenge(dto) {
    // Get token
    const token = this.authService.getToken();
    // Send token as Authorization header (this is spring security convention for basic auth)
    const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);

    if (this.authService.checkLogin()) {
      this.http.patch<UserChallenge>(`${this.url}/challenges/${dto.challengeId}/accept`, dto, {headers}).pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(err);
        })
      );
    }
  }

  hasUserAcceptedChallenge(dto) {
    // Get token
    const token = this.authService.getToken();
    // Send token as Authorization header (this is spring security convention for basic auth)
    const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);

    if (this.authService.checkLogin()) {
      return this.http.get<UserChallenge>(`${this.url}/user/challenges/${dto.challengeId}/user/${dto.acceptorId}/check`, {headers}).pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(err);
        })
      );
    }
  }

  allUserChallengesOfChallenge(challenge) {
    // Get token
    const token = this.authService.getToken();
    // Send token as Authorization header (this is spring security convention for basic auth)
    const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);

    if (this.authService.getToken()) {
      return this.http.get<UserChallenge[]>(`${this.url}/challenges/${challenge.id}/allUserChallenges`, {headers}).pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(err);
        })
      );
    }
  }
  updateUserWinner(cid, uid, challenge) {
    // Get token
    const token = this.authService.getToken();
    // Send token as Authorization header (this is spring security convention for basic auth)
    const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);

    if (this.authService.checkLogin()) {
      return this.http.patch<UserChallenge>(`${this.url}/challenges/${cid}/user/${uid}/`, challenge, {headers}).pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(err);
        })
      );
    }
  }

  getAllPendingAndAcceptedChallenges(cid) {
    // Get token
    const token = this.authService.getToken();
    // Send token as Authorization header (this is spring security convention for basic auth)
    const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);

    if (this.authService.checkLogin()) {
      return this.http.get<UserChallenge[]>(`${this.url}/challenges/${cid}/accept/all`, {headers}).pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(err);
        })
      );
    }
  }

  removeUnacceptedUserChallenges(id) {
    // Get token
    const token = this.authService.getToken();
    // Send token as Authorization header (this is spring security convention for basic auth)
    const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);

    if (this.authService.checkLogin()) {
      return this.http.delete<Boolean>(`${this.url}/challenges/accept/${id}`, {headers}).pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(err);
        })
      );
    }
  }

  tallyUserScores(userChallengeList, skillid) {
    const token = this.authService.getToken();
    // Send token as Authorization header (this is spring security convention for basic auth)
    const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);
    console.log('IN TALLYUSERSCORES');
    console.log(userChallengeList);
    const userChallengeDTOList = [];
    userChallengeList.forEach(element => {
      const ucDTO = {
        skillId: skillid,
        userId: element.user.id,
        accepted: element.accepted,
        won: element.acceptorWon};
        userChallengeDTOList.push(ucDTO);
    });
    console.log(userChallengeDTOList);
    if (this.authService.checkLogin()) {
      return this.http.patch<UserSkill[]>(`${this.url}/skills/${skillid}/tallyResults`, userChallengeDTOList, {headers}).pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(err);
        })
      );
    }
  }

  createUserChallengeWhenUserInvited(dto) {
    const token = this.authService.getToken();
    // Send token as Authorization header (this is spring security convention for basic auth)
    const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);
    if (this.authService.checkLogin()) {
      return this.http.patch<UserChallenge>(`${this.url}/challenges/invite`, dto, {headers}).pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(err);
        })
      );
    }
  }

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }
}
