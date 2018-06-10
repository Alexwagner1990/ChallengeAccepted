import { AuthService } from './../auth.service';
import { TagsService } from './../tags.service';
import { UserService } from './../user.service';
import { HttpHeaders } from '@angular/common/http';
import { UserChallenge } from './../models/user-challenge';
import { Challenge } from './../models/challenge';
import { ChallengeService } from './../challenge.service';
import { Component, OnInit } from '@angular/core';
import { UserChallengeService } from '../user-challenge.service';
import { User } from '../models/user';
import { Router, ActivatedRoute } from '@angular/router';
import { Tag } from '../models/tag';


@Component({
  selector: 'app-challenge-view',
  templateUrl: './challenge-view.component.html',
  styleUrls: ['./challenge-view.component.css']
})
export class ChallengeViewComponent implements OnInit {

  displayChallenge: Challenge;
  flag = false;
  testUser: User; // testUser is the logged in user now #dealwithit
  tags: Tag[] = [];
  selectedTags: Tag[] = [];
  userIdList: number[] = [];
  challengers: UserChallenge[] = [];
  showAlreadyAcceptError = false;
  noWinners = false;
  allAcceptorsOfAChallenge = [];
  hideAcceptButton = false;


  acceptChallenge() {

    const dto = {'challengeId': this.displayChallenge.id,
    'acceptorId': this.testUser.id};
    this.userChallengeService.hasUserAcceptedChallenge(dto).subscribe(
      data => {
        console.log(data);
        this.getChallengeData();
        this.hideAcceptButton = true;
      },
      error => {
        this.userChallengeService.acceptingAMarketChallenge(dto).subscribe(
          data2 => {
            this.getChallengeData();
            this.hideAcceptButton = true;
          },
          error2 => {
            console.log(error2);
          }
          );
      }
    );
  }

  setChallengeToActive(id) {
    this.challengeService.setChallengeToActive(id).subscribe(
      data => {
        this.getChallengeData();
      },
      error => {
        console.log(error);
      }
    );
  }

  setChallengeToExpired(id) {
    this.challengeService.setChallengeToExpired(id).subscribe(
      data => {
        this.getChallengeData();
      },
      error => {
        console.log(error);
      }
    );
  }

  setChallengeToCompleted(id) {
    this.challengeService.setChallengeToCompleted(id).subscribe(
      data => {
        this.getChallengeData();
      },
      error => {
        console.log(error);
      }
    );
  }

  getAcceptedData(id) {
    this.userChallengeService.getAllPendingAndAcceptedChallenges(id).subscribe(
      data => {
        this.challengers = data;
      },
      error => {
        console.log(error);
      }
    );
  }

  getChallengeData() {
    this.challengeService.showOneChallenge(this.route.snapshot.paramMap.get('id')).subscribe(
      data => {
        console.log(data);
        this.displayChallenge = data;
        this.getAcceptedData(data.id);
      },
      error => {
        this.displayChallenge = null;
      }
    );
  }

  getTags() {
    this.tagService.getAllTags().subscribe(
      data => {
        console.log(data);
        this.tags = data;
      },
      error => {
        this.tags = null;
        console.log(error);

      }
    );
  }

  updateUserIdList(id: number) {
    console.log(this.userIdList.indexOf(id));

    if (this.userIdList.includes(id)) {
      this.userIdList.splice(this.userIdList.indexOf(id) , 1);
    } else {
      this.userIdList.push(id);
    }
    console.log(this.userIdList);

  }

  updateWholeUserList(id, challenge) {
    console.log(challenge);
    let count = 0;
  if (this.userIdList.length > 0) {
    for (let index = 0; index < this.userIdList.length; index++) {
      this.userChallengeService.updateUserWinner(id, this.userIdList[index], this.displayChallenge).subscribe(
        data => {
          count++;
          if (count = this.userIdList.length) {
            this.setChallengeToCompleted(this.displayChallenge.id);
            this.getUserChallengesOfChallenge();
          }
        },
        error => {
          console.log(error);
        }
      );
    }
   } else {
     this.noWinners = true;
   }
  }

  getUserChallengesOfChallenge() {
    this.userChallengeService.allUserChallengesOfChallenge(this.displayChallenge).subscribe(
      data => {
        this.allAcceptorsOfAChallenge = data;
        this.userChallengeService.tallyUserScores(this.allAcceptorsOfAChallenge, this.displayChallenge.skill.id).subscribe(
          data2 => {
            this.allAcceptorsOfAChallenge = [];
            this.getChallengeData();
          },
          error => {
            console.log(error);
          }
        );
      },
      error => {
        console.log(error);
      }
    );
  }

  getLoggedInUser() {
    const un = this.authService.getLoggedInUserName();
    this.userService.findUserByUsername(un).subscribe(
      data => {
        this.testUser = data;
        this.getChallengeData();
      },
      error => {
        this.router.navigateByUrl('/home');
      }
    );
  }

  constructor(private challengeService: ChallengeService,
  private userChallengeService: UserChallengeService,
  private route: ActivatedRoute,
  private tagService: TagsService,
  private authService: AuthService,
  private userService: UserService,
  private router: Router) { }

  ngOnInit() {
    // this.getChallengeData();
    this.getLoggedInUser();
  }

}
