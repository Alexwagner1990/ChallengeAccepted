import { AuthService } from './../auth.service';
import { UserSkill } from './../models/user-skill';
import { SkillService } from './../skill.service';
import { UserSkillService } from './../user-skill.service';
import { UserService } from './../user.service';
import { ChallengeService } from './../challenge.service';
import { ChallengesAcceptedPipe } from './../pipes/challenges-accepted.pipe';
import { Challenge } from './../models/challenge';
import { TopSkillsPipe } from './../pipes/top-skills.pipe';
import { UserChallengeService } from './../user-challenge.service';
import { CompletedStatusPipe } from './../pipes/completed-status.pipe';
import { Component, OnInit } from '@angular/core';
import { User } from '../models/user';
import { ChallengesCreatedPipe } from '../pipes/challenges-created.pipe';
import { userInfo } from 'os';
import { ActivatedRoute, Router } from '@angular/router';
import { Skill } from '../models/skill';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  title = 'Profile of {{ user.username }}';
  test = '';
  loadedUser: User;
  userSkills: UserSkill[] = [];
  pendingChallenges = [];
  activeChallenges = [];
  challengesUserHasCompleted = [];
  loggedInUser: User;

  getNumCompletedChallenges = function(id) {
    return this.completedChallenges.transform(this.user.challenges).length;
  };

  getLocation = function(id) {
    return this.user.location;
  };

  getChallengesCreated = function(id) {
    this.user = this.userService.findUserById();
    return this.challengesCreated.transform(this.user.challenges, this.user);
  };

  getChallengesAccepted = function(id) {
    this.user = this.userService.findUserById();
    return this.challengesAccepted.transform(this.user.challenges, this.user.userChallenges, this.user);
  };

  getUserLevelOfSkill(id) {
    return this.userSkillService.findUserSkillById(id).subscribe(
      data => this.userSkills = data,
      error => console.log(error)
      );
  }

  getSkillNameById(id) {
    this.skillService.getSkillById(id).subscribe(
      data => data,
      errror => null);
  }

  getUserSkills() {
    this.userSkillService.getUserSkills(this.route.snapshot.paramMap.get('id')).subscribe(
      data => {this.userSkills = data;
      console.log(data); },
      error => console.log('oh no')
    );
  }

  // getUserProgressToNextLevel = function(skillId, userId) {
  //   const userSkill: UserSkill = this.userService.findUserById(id);
  //   // The above needs to be a method that finds a user_skill by user_id and skill_id
  //   return this.userSkillService.getUserProgressToNextLevel(userSkill);
  // };

  getCurrentLevel(points) {
    if (points > 0 && points < 10) {
      points = 10;
    }
    const level = Math.floor(points / 10);
    return level;
  }

  getUserData() {
    this.userService.findUserByUsername(this.route.snapshot.paramMap.get('id')).subscribe(
      data => {
        console.log(data);
        this.loadedUser = data;
        this.getUserSkills();
        this.getChallengesUserHasAccepted();
        // this.getChallengesUserHasCompleted();
      }, error => console.log(error + '*************************************************')
    );
  }

  getChallengesUserHasAccepted() {
    this.challengeService.getChallengesOfUserAndStatus(this.loadedUser.username, 1).subscribe(
      data => {
        console.log(data);
        this.pendingChallenges = data;
        this.getChallengesUserHasActive();
        this.getChallengesUserHasCompleted();
      },
      error => {
        console.log(error);
      }
    );
  }

  getChallengesUserHasActive() {
    this.challengeService.getChallengesOfUserAndStatus(this.loadedUser.username, 2).subscribe(
      data => {
        console.log(data);
        this.activeChallenges = data;
      },
      error => {
        console.log(error);
      }
    );
  }

  getChallengesUserHasCompleted() {
    this.challengeService.getChallengesOfUserAndStatus(this.loadedUser.username, 3).subscribe(
      data => {
        console.log(data);
        this.challengesUserHasCompleted = data;
      },
      error => {
        console.log(error);
      }
    );
  }

  viewChallenge(id) {
    this.router.navigateByUrl(`challview/${id}`);
  }

  constructor(private userChallengeService: UserChallengeService,
    private completedChallenges: CompletedStatusPipe,
    private topSkills: TopSkillsPipe,
    private challengesCreated: ChallengesCreatedPipe,
    private challengeService: ChallengeService,
    private route: ActivatedRoute,
    private userService: UserService,
    private userSkillService: UserSkillService,
    private skillService: SkillService,
    private router: Router,
    private authService: AuthService ) { }

    ngOnInit() {
      if (!this.authService.checkLogin()) {
        this.router.navigateByUrl('/home');
      }
      this.userService.findUserByUsername(this.authService.getLoggedInUserName()).subscribe(
        data => {
          this.loggedInUser = data;
        },
        error => {
          this.router.navigateByUrl('/home');
        }
      );
      this.getUserData();
  }
}
