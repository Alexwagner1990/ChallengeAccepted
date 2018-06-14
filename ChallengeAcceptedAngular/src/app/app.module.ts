import { HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { DatePipe } from '@angular/common';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ChallengeListComponent } from './challenge-list/challenge-list.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { RegisterComponent } from './register/register.component';
import { NavbarComponent } from './navbar/navbar.component';
import { FallbackComponent } from './fallback/fallback.component';
import { ChallengeViewComponent } from './challenge-view/challenge-view.component';
import { HomeComponent } from './home/home.component';
import { CompletedStatusPipe } from './pipes/completed-status.pipe';
import { TopSkillsPipe } from './pipes/top-skills.pipe';
import { TestingComponent } from './testing/testing.component';
import { ChallengesAcceptedPipe } from './pipes/challenges-accepted.pipe';
import { ChallengesCreatedPipe } from './pipes/challenges-created.pipe';
import { MatButtonModule, MatCheckboxModule, MatProgressBarModule, MatFormFieldModule, MatInputModule } from '@angular/material';
import { MatSelectModule } from '@angular/material';
import { UserChallengeService } from './user-challenge.service';
import { UserService } from './user.service';
import { AuthService } from './auth.service';
import { ChallengeService } from './challenge.service';
import { SkillService } from './skill.service';
import { UserSkillService } from './user-skill.service';
import { InboxComponent } from './inbox/inbox.component';
import { InboxService } from './inbox.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AboutUsComponent } from './about-us/about-us.component';
import { FooterComponent } from './footer/footer.component';
import { SearchComponent } from './search/search.component';
import { SearchService } from './search.service';

@NgModule({
  declarations: [
    AppComponent,
    ChallengeListComponent,
    UserProfileComponent,
    LoginComponent,
    LogoutComponent,
    TopSkillsPipe,
    RegisterComponent,
    NavbarComponent,
    FallbackComponent,
    ChallengeViewComponent,
    HomeComponent,
    TestingComponent,
    InboxComponent,
    AboutUsComponent,
    FooterComponent,
    CompletedStatusPipe,
    ChallengesAcceptedPipe,
    CompletedStatusPipe,
    ChallengesCreatedPipe,
    SearchComponent
    ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    MatButtonModule,
    MatCheckboxModule,
    MatProgressBarModule,
    MatFormFieldModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatSelectModule
  ],
  providers: [CompletedStatusPipe,
    ChallengesAcceptedPipe,
    TopSkillsPipe,
    CompletedStatusPipe,
    ChallengesCreatedPipe,
    UserChallengeService,
    ChallengeService,
    SkillService,
    UserSkillService,
    AuthService,
    UserService,
    InboxService,
    SearchService],
  bootstrap: [AppComponent]
})
export class AppModule { }
