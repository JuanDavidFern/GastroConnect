import { Component, SimpleChanges } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RecipeToCard } from '../../interfaces/RecipeToCard';
import { CookieService } from 'ngx-cookie-service';
import { NavigationService } from '../../services/navigation.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  recipes: RecipeToCard[] = [];
  searchValue = '';
  difficulty = '';
  token: string = '';

  constructor(
    private http: HttpClient,
    private navigationService: NavigationService,
    cookieService: CookieService
  ) {
    this.token = cookieService.get('token');
  }

  goCreateRecipe() {
    this.navigationService.goCreateRecipe();
  }

  search() {
    this.getRecipes();
  }

  onSearchValueChange() {
    this.getRecipes();
  }

  changeDifficulty(e: Event) {
    if (this.difficulty == '') {
      this.difficulty = 'all';
    }
    document
      .getElementById(this.difficulty)
      ?.classList.remove('ring-amber-400');
    this.difficulty = (<HTMLInputElement>e.target).value;
    document.getElementById(this.difficulty)?.classList.add('ring-amber-400');
    this.getRecipes();
  }

  ngOnInit() {
    this.getRecipes();
  }

  getRecipes() {
    this.http
      .get<RecipeToCard[]>(
        `http://localhost:8080/recipes/getRecipesByTitleAndDifficulty?difficulty=${this.difficulty}&title=${this.searchValue}`
      )
      .subscribe((data) => {
        this.recipes = data;
      });
  }
}
