import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { CookieService } from 'ngx-cookie-service';
import { FollowCard } from '../../interfaces/FollowCard';

@Component({
  selector: 'app-following-page',
  templateUrl: './following-page.component.html',
  styleUrl: './following-page.component.css',
})
export class FollowingPageComponent {
  followings: FollowCard[] = [];
  private cookies: CookieService;
  token: string = '';
  loading: boolean = true; // Variable para controlar la carga

  constructor(cookieService: CookieService, protected auth: AuthService) {
    this.cookies = cookieService;
    this.token = this.cookies.get('token');
  }

  ngOnInit() {
    this.token = this.cookies.get('token');
    this.getFollowers();
  }

  getFollowers() {
    this.auth.getFollowings(this.token).subscribe(
      (data: FollowCard[]) => {
        if (data) {
          this.followings = data;
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
}
