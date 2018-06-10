import { UserChallengeService } from './../user-challenge.service';
import { UserService } from './../user.service';
import { AuthService } from './../auth.service';
import { SkillService } from './../skill.service';
import { TagsService } from './../tags.service';
import { Tag } from './../models/tag';
import { Router } from '@angular/router';
import { ChallengeViewComponent } from './../challenge-view/challenge-view.component';
import { Challenge } from './../models/challenge';
import { ChallengeService } from './../challenge.service';
import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import { Skill } from '../models/skill';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  challenge: Challenge = new Challenge();
  tags: Tag[] = [];
  skills: Skill[] = [];
  invitedUser: String;
  notValidUser = false;

  getTags() {
    this.tagService.getAllTags().subscribe(
      data => {
        this.tags = data;
      },
      error => {
        this.tags = null;
      }
    );
  }

  getSkills() {
    this.skillService.getAllSkills().subscribe(
      data => {
        this.skills = data;
      },
      error => {
        this.skills = null;
      }
    );
  }

  createChallenge(challenge) {
    console.log(challenge);
    if (!this.invitedUser) {
      this.userService.findUserByUsername(this.authService.getLoggedInUserName()).subscribe(
        data => {
          this.challengeService.create(challenge, data.id).subscribe(
          userData => {
            this.router.navigateByUrl('challview/mychallenge/' + userData.id);
            this.invitedUser = null;
          },
          err => throwError(err)
          );
        },
          error => {
            console.log(error);
          }
      );
    } else {
        this.userService.isUserValid(this.invitedUser).subscribe(
          userExists => {
            if (userExists) {
            console.log('USER EXISTS!');
            console.log(userExists);
              this.notValidUser = false;
              this.userService.findUserByUsername(this.authService.getLoggedInUserName()).subscribe(
                data => {
                  this.challengeService.create(challenge, data.id).subscribe(
                  userData => {
                    const ucDTO = {
                      challengeId: userData.id,
                      acceptorId: userExists.id
                    };
                    console.log(ucDTO);
                    this.userChallengeService.createUserChallengeWhenUserInvited(ucDTO).subscribe(
                      completeChallenge => {
                        this.router.navigateByUrl('challview/mychallenge/' + userData.id);
                        this.invitedUser = null;
                      },
                      notCompleteChallenge => {
                        console.log(notCompleteChallenge);
                        this.invitedUser = null;
                      }
                    );
                  },
                  err => throwError(err)
                  );
                },
                  error => {
                    console.log(error);
                  }
              );
            } else {
              console.log('USER DOESNT EXIST!');
              this.invitedUser = null;
              this.notValidUser = true;
            }

          },
          error => {
            console.log(error);
          }
        );
      }

  }

  checkLogin() {
    return this.authService.checkLogin();
  }

  userProfile() {
    const token = this.authService.getToken();
    const unpw = atob(token);
    const un = unpw.split(':')[0];
    this.router.navigateByUrl(`userprofile/myprofile/${un}`);
  }

  userInbox() {
    const token = this.authService.getToken();
    const unpw = atob(token);
    const un = unpw.split(':')[0];
    this.router.navigateByUrl(`messages/${un}`);

  }

  constructor(private challengeService: ChallengeService,
              private router: Router,
              private tagService: TagsService,
              private skillService: SkillService,
              private authService: AuthService,
              private userService: UserService,
              private userChallengeService: UserChallengeService) { }

  ngOnInit() {
    this.getSkills();
    this.getTags();
  }

}
