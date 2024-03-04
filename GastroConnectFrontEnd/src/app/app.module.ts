import { NgModule } from '@angular/core';
import {
  BrowserModule,
  provideClientHydration,
} from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { RecipeCardComponent } from './components/recipe-card/recipe-card.component';
import { CreateRecipeComponent } from './components/create-recipe/create-recipe.component';
import { NoRecipesComponent } from './components/no-recipes/no-recipes.component';
import { LoginComponent } from './components/login/login.component';
import { FollowerPageComponent } from './components/follower-page/follower-page.component';
import { FollowerCardComponent } from './components/follower-card/follower-card.component';
import { FollowingPageComponent } from './components/following-page/following-page.component';
import { ProfileComponent } from './components/profile/profile.component';
import { UserSettingComponent } from './components/user-setting/user-setting.component';
import { ShowRecipeComponent } from './components/show-recipe/show-recipe.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    RecipeCardComponent,
    CreateRecipeComponent,
    NoRecipesComponent,
    LoginComponent,
    FollowerPageComponent,
    FollowerCardComponent,
    FollowingPageComponent,
    ProfileComponent,
    UserSettingComponent,
    ShowRecipeComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, FormsModule, HttpClientModule],
  providers: [provideClientHydration()],
  bootstrap: [AppComponent],
})
export class AppModule {}
