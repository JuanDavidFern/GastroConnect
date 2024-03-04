import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { CookieService } from 'ngx-cookie-service';
import { FollowCard } from '../../interfaces/FollowCard';

@Component({
  selector: 'app-follower-page',
  templateUrl: './follower-page.component.html',
  styleUrls: ['./follower-page.component.css'],
})
export class FollowerPageComponent {
  followers: FollowCard[] = [];
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
    this.auth.getFollowers(this.token).subscribe(
      (data: FollowCard[]) => {
        if (data) {
          this.followers = data;
        }
      },
      (error) => {
        console.error('Error fetching followers', error);
      },
      () => {
        this.loading = false; // Marcar la carga como completa cuando la consulta termine
      }
    );
  }
}
