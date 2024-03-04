import { Component, Input } from '@angular/core';
import { RecipeToCard } from '../../interfaces/RecipeToCard';
import { NavigationService } from '../../services/navigation.service';

@Component({
  selector: 'app-recipe-card',
  templateUrl: './recipe-card.component.html',
  styleUrl: './recipe-card.component.css',
})
export class RecipeCardComponent {
  @Input() recipe!: RecipeToCard;
  diff: number = 1;

  constructor(private navigationService: NavigationService) {}

  ngOnInit() {
    if (this.recipe.difficulty == 'Medio') {
      this.diff = 2;
    }
    if (this.recipe.difficulty == 'Avanzado') {
      this.diff = 3;
    }
  }

  goProfile(userId: any) {
    this.navigationService.goProfile(userId);
  }

  goShowRecipe(recipeId: any) {
    this.navigationService.goShowRecipe(recipeId);
  }

  getDiffFor(num: number): number[] {
    return new Array(num).fill(0).map((_, index) => index);
  }
}
