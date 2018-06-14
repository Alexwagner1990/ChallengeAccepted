import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Challenge } from './models/challenge';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Tag } from './models/tag';
import { environment } from '../environments/environment';
import { User } from './models/user';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private baseUrl = environment.baseUrl;
  private url = this.baseUrl + 'api';

  getChallengesByTag(tag: Tag[]) {
    const token = this.authService.getToken();
    // Send token as Authorization header (this is spring security convention for basic auth)
    const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);
    const tagIds = [];
    tag.forEach(element => {
      tagIds.push(element.id);
    });
    // Can do a check for 0 sized tags here, keep this for now
    console.log(tagIds);

    return this.http.get<Challenge[]>(`${this.url}/search/challenges/${tagIds}`, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  getUsersByUsername(name) {
    const token = this.authService.getToken();
    // Send token as Authorization header (this is spring security convention for basic auth)
    const headers = new HttpHeaders().set('Authorization', `Basic ${token}`);
    // Can do a check for 0 sized tags here, keep this for now
    return this.http.get<User[]>(`${this.url}/search/users/${name}`, {headers}).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  constructor(private http: HttpClient,
              private authService: AuthService) { }
}
