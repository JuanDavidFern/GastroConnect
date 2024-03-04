import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { UpdateRecipe } from '../../interfaces/UpdateRecipe';
import { NavigationService } from '../../services/navigation.service';

@Component({
  selector: 'app-show-recipe',
  templateUrl: './show-recipe.component.html',
  styleUrl: './show-recipe.component.css',
})
export class ShowRecipeComponent {
  recipe!: UpdateRecipe;
  recipeId!: any;
  token: string = '';
  images: string[] = [];
  ingredients: string[] = [''];
  recipePhoto!: String;
  nameShort = true;
  descriptionShort = true;
  timeShort = true;
  ingredientsShort = true;
  stepsShort = true;
  showConfirmationPopup: boolean = false;
  loading: boolean = true;

  constructor(
    private http: HttpClient,
    cookieService: CookieService,
    private navigationService: NavigationService
  ) {
    this.token = cookieService.get('token');
  }

  ngOnInit() {
    this.recipeId = history.state.recipeId;
    this.http
      .get<UpdateRecipe>(
        `http://localhost:8080/recipes/getRecipeById/${this.recipeId}?token=Bearer ${this.token}`
      )
      .subscribe((data) => {
        this.recipe = data;
        this.recipePhoto = this.recipe.photo;
        this.setDiff();
        this.setIngredients();
        this.loading = false;
      });
  }

  showPopUp() {
    this.showConfirmationPopup = true;
  }
  definitiveDelete() {
    this.http
      .delete<any>(
        `http://localhost:8080/recipes/deleteRecipe/${this.recipeId}/Bearer ${this.token}`
      )
      .subscribe((data) => {
        setTimeout(() => {
          this.navigationService.goHome();
        }, 100);
      });
  }
  cancelDelete() {
    this.showConfirmationPopup = false;
  }

  setIngredients() {
    this.ingredients = this.recipe.ingredients.split('$');
  }
  saveRecipe() {
    const ingre = this.ingredients.filter((ing) => ing !== '').join('$');

    // Reiniciar todos los indicadores de validez
    this.nameShort = true;
    this.descriptionShort = true;
    this.timeShort = true;
    this.ingredientsShort = true;
    this.stepsShort = true;

    // Validar nombre
    if (
      typeof this.recipe.title !== 'string' ||
      this.recipe.title.trim().length <= 10
    ) {
      this.nameShort = false;
    }

    // Validar descripción
    if (
      typeof this.recipe.description !== 'string' ||
      this.recipe.description.trim().length <= 85
    ) {
      this.descriptionShort = false;
    }

    // Validar tiempo
    if (
      typeof this.recipe.preparation_time !== 'number' ||
      this.recipe.preparation_time <= 0
    ) {
      this.timeShort = false;
    }

    // Validar ingredientes
    if (ingre.length === 0) {
      this.ingredientsShort = false;
    }

    // Validar pasos
    if (
      typeof this.recipe.preparation_steps !== 'string' ||
      this.recipe.preparation_steps.trim().length === 0
    ) {
      this.stepsShort = false;
    }

    if (
      (this.nameShort &&
        this.descriptionShort &&
        this.timeShort &&
        this.ingredientsShort &&
        this.stepsShort) == true
    ) {
      this.sendRecipe(ingre);
      this.navigationService.goUserSettings();
    }
  }

  trackByFn(index: number, item: string): number {
    return index;
  }

  // Agregar un nuevo input de ingrediente
  addIngredient(): void {
    this.ingredients.push('');
  }

  // Eliminar un input de ingrediente
  removeIngredient(index: number): void {
    this.ingredients.splice(index, 1);
  }

  setDiff() {
    if (this.recipe.difficulty === 'Medio') {
      this.images = [
        '../../../assets/olla.png',
        '../../../assets/olla.png',
        '../../../assets/olla-modified.png',
      ];
    } else if (this.recipe.difficulty === 'Principiante') {
      this.images = [
        '../../../assets/olla.png',
        '../../../assets/olla-modified.png',
        '../../../assets/olla-modified.png',
      ];
    } else if (this.recipe.difficulty === 'Avanzado') {
      this.images = [
        '../../../assets/olla.png',
        '../../../assets/olla.png',
        '../../../assets/olla.png',
      ];
    }
  }

  onImageClick(index: number): void {
    // Cambiar las imágenes según el índice
    if (index === 1) {
      this.recipe.difficulty = 'Principiante';
      this.images[0] = '../../../assets/olla.png';
      this.images[1] = '../../../assets/olla-modified.png';
      this.images[2] = '../../../assets/olla-modified.png';
    } else if (index === 2) {
      this.recipe.difficulty = 'Medio';
      this.images[0] = '../../../assets/olla.png';
      this.images[1] = '../../../assets/olla.png';
      this.images[2] = '../../../assets/olla-modified.png';
    } else if (index === 3) {
      this.recipe.difficulty = 'Avanzado';
      this.images[0] = '../../../assets/olla.png';
      this.images[1] = '../../../assets/olla.png';
      this.images[2] = '../../../assets/olla.png';
    }
  }
  sendRecipe(ingredients: string) {
    this.http
      .post('http://localhost:8080/recipes/save', {
        id: this.recipeId,
        user_token: `Bearer ${this.token}`,
        title: this.recipe.title,
        description: this.recipe.description,
        ingredients: ingredients,
        preparation_steps: this.recipe.preparation_steps,
        difficulty: this.recipe.difficulty,
        preparation_time: this.recipe.preparation_time,
        photo: this.recipePhoto,
      })
      .subscribe(
        (response) => {},
        (error) => {
          console.error(error);
        }
      );
  }
  convertToBase64(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        const base64 = reader.result as string;
        if (base64 && base64.includes(';base64,')) {
          const base64Data = base64.split(';base64,').pop();
          if (base64Data) {
            this.recipePhoto = base64Data;
          }
        }
      };
    }
  }
}
