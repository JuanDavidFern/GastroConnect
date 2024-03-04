export interface UpdateRecipe {
  id: number;
  title: string;
  description: string;
  ingredients: string;
  preparation_steps: string;
  preparation_time: number;
  difficulty: string;
  photo: String;
  editable: boolean;
}
