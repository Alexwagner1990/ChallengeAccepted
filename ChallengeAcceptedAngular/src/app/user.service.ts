import { AuthService } from './auth.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './models/user';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = environment.baseUrl;
  private url = this.baseUrl + 'api';

  findUserById(id) {
    return this.http.get<User>(`${this.url}/users/${id}`).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  findCreatorByChallengeId(id) {
    return this.http.get<User>(`${this.url}/users/challenge/${id}`).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  findUserByUsername(username) {
    const token = this.authService.getToken();
    // Send token as Authorization header (this is spring security convention for basic auth)
    const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);

    return this.http.get<User>(`${this.url}/users/${username}`, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  isUserValid(username) {
    const token = this.authService.getToken();
    // Send token as Authorization header (this is spring security convention for basic auth)
    const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);
    if (this.authService.checkLogin()) {
      return this.http.get<User>(`${this.url}/users/${username}/valid`, {headers}).pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(err);
        })
      );
    }
  }

  constructor(private http: HttpClient,
              private authService: AuthService) { }


}
