import { TagsService } from './../tags.service';
import { SearchService } from './../search.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Tag } from '../models/tag';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  searchChallenges = [];
  searchUsers = [];
  searchedYetChallenges;
  searchedYetUsers;
  tags: Tag[] = [];
  selectedTags: Tag[] = [];
  username: String;


  getIcons(name) {
    name = name.split(' ').join('');
    return 'assets/img/icon/' + name + '.png';
  }

  searchChallengesByTags() {
    console.log(this.selectedTags);
    this.searchService.getChallengesByTag(this.selectedTags).subscribe(
      data => {
        this.searchChallenges = data;
        this.searchedYetChallenges = true;
        this.getFormTags();
      },
      error => {
        console.log(error);
        this.searchedYetChallenges = true;
        this.getFormTags();
      }
    );
  }

  searchUsersByUsername() {
    this.searchService.getUsersByUsername(this.username).subscribe(
      data => {
        console.log(data);
        this.searchUsers = data;
        this.searchedYetUsers = true;
      },
      error => {
        console.log(error);
        this.searchedYetUsers = true;
      }
    );
  }

  viewChallenge(id) {
    this.router.navigateByUrl(`challview/${id}`);
  }

  viewUser(username) {
    this.router.navigateByUrl(`userprofile/${username}`);
  }

  getFormTags() {
    this.tagService.getAllTags().subscribe(
      data => {
        this.tags = data;
      },
      error => {
        this.tags = null;
      }
    );
  }

  constructor(private router: Router,
              private searchService: SearchService,
              private tagService: TagsService) { }

  ngOnInit() {
    this.searchedYetChallenges = false;
    this.searchedYetUsers = false;
    this.getFormTags();
  }

}
