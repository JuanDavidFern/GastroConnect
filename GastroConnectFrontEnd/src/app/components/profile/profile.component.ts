import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { OtherUserProfile } from '../../interfaces/OtherUserProfile';
import { CookieService } from 'ngx-cookie-service';
import { RecipeToCard } from '../../interfaces/RecipeToCard';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
})
export class ProfileComponent {
  userId!: any;
  profile!: OtherUserProfile;
  loading: boolean = true; // Variable para controlar la carga
  token: string = '';
  recipes: RecipeToCard[] = [];
  private cookies: CookieService;

  constructor(
    protected auth: AuthService,
    private http: HttpClient,
    cookieService: CookieService
  ) {
    this.cookies = cookieService;
    this.token = this.cookies.get('token');
  }

  ngOnInit() {
    this.userId = history.state.userId;
    this.getUserProfile();
    this.getRecipes();
  }

  getUserProfile() {
    this.auth.getOtherUserProfile(this.userId, this.token).subscribe(
      (data: OtherUserProfile) => {
        if (data) {
          this.profile = data;
        }
      },
      (error) => {
        console.error('Error fetching followings', error);
      },
      () => {
        this.loading = false; // Marcar la carga como completa cuando la consulta termine
      }
    );
  }

  public formatTimestamp(timestamp: string): string {
    const date = new Date(parseInt(timestamp));
    // Utilizar toLocaleDateString() en lugar de toLocaleString()
    return date.toLocaleDateString();
  }

  changeFollow() {
    this.profile.follow == false
      ? (this.profile.follow = true)
      : (this.profile.follow = false);

    this.auth
      .changeFollow(this.userId, this.token, this.profile.follow)
      .subscribe((data: any) => {
        if (data) {
          this.profile.follow = data.follow;
        }
      });
  }

  getRecipes() {
    this.http
      .get<RecipeToCard[]>(
        `http://localhost:8080/recipes/getRecipesByUserId/${this.userId}`
      )
      .subscribe((data) => {
        this.recipes = data;
      });
  }
}
