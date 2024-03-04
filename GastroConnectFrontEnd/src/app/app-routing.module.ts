import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { CreateRecipeComponent } from './components/create-recipe/create-recipe.component';
import { LoginComponent } from './components/login/login.component';
import { FollowerPageComponent } from './components/follower-page/follower-page.component';
import { FollowingPageComponent } from './components/following-page/following-page.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ShowRecipeComponent } from './components/show-recipe/show-recipe.component';
import { UserSettingComponent } from './components/user-setting/user-setting.component';
import { authGuard } from '../app/guards/auth.guard';
import { unauthenticatedGuard } from '../app/guards/unauthenticated.guard';

const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'create-recipe',
    canActivate: [authGuard],
    component: CreateRecipeComponent,
  },
  {
    path: 'logIn',
    canActivate: [unauthenticatedGuard],
    component: LoginComponent,
  },
  {
    path: 'followers',
    canActivate: [authGuard],
    component: FollowerPageComponent,
  },
  {
    path: 'following',
    canActivate: [authGuard],
    component: FollowingPageComponent,
  },
  { path: 'profile', component: ProfileComponent },
  {
    path: 'user-settings',
    canActivate: [authGuard],
    component: UserSettingComponent,
  },
  { path: 'show-recipe', component: ShowRecipeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
