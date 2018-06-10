import { UserService } from './../user.service';
import { Message } from './../models/message';
import { Router, ActivatedRoute } from '@angular/router';
import { InboxService } from './../inbox.service';
import { Component, OnInit } from '@angular/core';
import { User } from '../models/user';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css']
})
export class InboxComponent implements OnInit {

  allMessages: Message[] = [];
  threadMessages: Message[] = [];
  reply = new Message();
  freshThread = new Message();
  receiver = new User();
  errorMessage = false;
  loggedInUser: User;
  showingThreadMessages = [];

  getAllMessageHeadsOfUser() {
    this.inboxService.allMessageHeads(this.route.snapshot.paramMap.get('id')).subscribe(
      data => {
        console.log(data);
        this.allMessages = data;
        for (let index = 0; index < this.allMessages.length; index++) {
          const hold = {id: this.allMessages[index].id, show: false, number: 0};
          this.showingThreadMessages.push(hold);
        }
      },
      error => {
        console.log(error);
      }
    );

  }

  getThreadMessages(id) {
    this.inboxService.threadMessages(id).subscribe(
      data => {
        this.threadMessages = data;
        this.reply.threadId = data[0].threadId;
        console.log(this.threadMessages);
      },
      error => {
        console.log(error);
      }
    );
  }

  sendReply() {                                          // Changed this
      if (this.threadMessages[0].sender.username !== this.loggedInUser.username) {
        this.reply.receiver = new User();
        this.reply.receiver.username = this.threadMessages[0].sender.username;
      } else {
        this.reply.receiver = new User();
        this.reply.receiver.username = this.threadMessages[0].receiver.username;
      }
    this.reply.sender = new User();
    this.reply.sender.username = this.loggedInUser.username; // changed this from AlexTheDestroyer
    this.inboxService.submitReply(this.reply).subscribe(
      data => {
        this.getThreadMessages(this.reply.threadId);
        this.reply = new Message();
      },
      error => {
        console.log(error);
      }
    );
  }

  sendMessage() {
    this.freshThread.receiver = this.receiver;
    const sender = new User();
    sender.username = this.loggedInUser.username;
    this.freshThread.sender = sender;
    console.log(this.freshThread);
    this.inboxService.submitReply(this.freshThread).subscribe(
      data => {
        this.errorMessage = false;
        this.freshThread = new Message();
        this.receiver = new User();
        this.getAllMessageHeadsOfUser();
      },
      error => {
        console.log(error);
        this.freshThread = new Message();
        this.receiver = new User();
        this.errorMessage = true;
      }
    );
  }

  deleteMessage(id) {
    this.inboxService.deleteMessage(id).subscribe(
      data => {
        this.threadMessages = [];
        this.getAllMessageHeadsOfUser();
      },
      error => {
        console.log(error);
      }
    );
  }

  getMessagesInThread(threadId, index) {
    this.inboxService.threadMessages(threadId).subscribe(
      data => {
        console.log(data);
        this.setMNumber(data, threadId, index);
      },
      error => {
        console.log(error);
      }
    );
  }

  setMNumber(data, threadId, index) {
    console.log(this.showingThreadMessages.indexOf({id: threadId}));
    this.showingThreadMessages[index].number = data.length;
  }

  showNumberOfMessages(id) {
    for (let index = 0; index < this.showingThreadMessages.length; index++) {
      if (this.showingThreadMessages[index].id === id) {
        const mNumber = this.getMessagesInThread(id, index);
        this.showingThreadMessages[index].number = mNumber;
        this.showingThreadMessages[index].show = true;
        console.log(mNumber);

      }
    }
  }

  constructor(private inboxService: InboxService,
    private route: ActivatedRoute,
    private authService: AuthService,
    private router: Router,
    private userService: UserService) { }

  ngOnInit() {
    if (!this.authService.checkLogin()) {
      this.router.navigateByUrl('/home');
    }
    this.userService.findUserByUsername(this.authService.getLoggedInUserName()).subscribe(
      data => {
        this.loggedInUser = data;
        this.getAllMessageHeadsOfUser();
      },
      error => {
        this.router.navigateByUrl('/home');
      }
    );
    this.reply = new Message();
    this.errorMessage = false;

  }

}
