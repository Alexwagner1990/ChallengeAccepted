import { Skill } from './../models/skill';
import { Router } from '@angular/router';
import { AuthService } from './../auth.service';
import { Component, OnInit } from '@angular/core';
import { User } from '../models/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User = new User();

  login(user) {
    this.authService.login(user.username, user.password).subscribe(
      data => {
        this.router.navigateByUrl('/home');
      },
      err => console.log(err)
    );
  }

  constructor(private authService: AuthService,
              private router: Router) { }

  ngOnInit() {
  }

}
