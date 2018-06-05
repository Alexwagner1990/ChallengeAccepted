import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { UserSkill } from './models/user-skill';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserSkillService {

  url = 'http://localhost:8080/api';

  findUserSkillById(usid) {
    return this.http.get<UserSkill[]>(`${this.url}/users/${usid}`).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  getUserSkills(uid) {
    return this.http.get<UserSkill[]>(`${this.url}/userskills/${uid}`).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  getLevelOfSkill(userSkill: UserSkill) {
    return userSkill.points / 10;
  }

  getUserProgressToNextLevel(userSkill: UserSkill) {
    return userSkill.points % 10;
  }


  constructor(private http: HttpClient) { }
}
