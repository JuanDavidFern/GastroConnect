import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class NavigationService {
  constructor(private router: Router) {}

  goHome() {
    setTimeout(() => {
      this.router.navigate(['']);
    }, 100);
  }

  goHomeGuard() {
    this.router.navigate(['']);
  }

  goLogIn() {
    setTimeout(() => {
      this.router.navigate(['logIn']);
    }, 100);
  }

  goCreateRecipe() {
    this.router.navigate(['create-recipe']);
  }

  goFollowing() {
    this.router.navigate(['following']);
  }

  goFollowers() {
    this.router.navigate(['followers']);
  }

  goUserSettings() {
    this.router.navigate(['user-settings']);
  }

  goProfile(userId: any) {
    this.router.navigate(['profile'], { state: { userId } });
  }

  goShowRecipe(recipeId: any) {
    this.router.navigate(['show-recipe'], { state: { recipeId } });
  }
}
