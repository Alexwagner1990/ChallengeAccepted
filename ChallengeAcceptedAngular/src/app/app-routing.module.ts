import { SearchComponent } from './search/search.component';
import { InboxComponent } from './inbox/inbox.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { ChallengeViewComponent } from './challenge-view/challenge-view.component';
import { Challenge } from './models/challenge';
import { NgModule } from '@angular/core';
import { Routes, RouterModule, Router, NavigationStart, NavigationEnd, NavigationError } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { ChallengeListComponent } from './challenge-list/challenge-list.component';
import { LogoutComponent } from './logout/logout.component';
import { AboutUsComponent } from './about-us/about-us.component';

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'home'},
  {path: 'userprofile/myprofile/:id', component: UserProfileComponent},
  {path: 'challview/mychallenge/:id', component: ChallengeViewComponent},
  {path: 'challview/:id', component: ChallengeViewComponent},
  {path: 'userprofile/:id', component: UserProfileComponent, runGuardsAndResolvers: 'always'},
  {path: 'allchallenges', component: ChallengeListComponent},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'logout', component: LogoutComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'messages/:id', component: InboxComponent},
  {path: 'search', component: SearchComponent},
  {path: 'about', component: AboutUsComponent},
  {path: '**', component: HomeComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
