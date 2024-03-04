import { Component } from '@angular/core';
import { NavigationService } from '../../services/navigation.service';
import { CookieService } from 'ngx-cookie-service';
import { Router, NavigationStart } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { NavbarUserData } from '../../interfaces/NavbarUserData';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  private cookies: CookieService;
  token: string = '';
  userData!: NavbarUserData;

  constructor(
    private router: Router,
    private navigationService: NavigationService,
    cookieService: CookieService,
    protected auth: AuthService
  ) {
    this.cookies = cookieService;
    this.token = this.cookies.get('token');
  }

  ngOnInit() {
    // this.token = this.cookies.get('token');
    // this.loadUserDetails();
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        this.token = this.cookies.get('token');
        this.loadUserDetails();
      }
    });
  }

  goHome() {
    this.navigationService.goHome();
  }

  goLogIn() {
    this.navigationService.goLogIn();
  }

  goFollowers() {
    this.navigationService.goFollowers();
  }

  goFollowing() {
    this.navigationService.goFollowing();
  }

  goUserSettings() {
    this.navigationService.goUserSettings();
  }

  logOut() {
    this.cookies.delete('token');
    // let dorpdown = document.getElementById('user-dropdown');
    // if (dorpdown != null) {
    //   dorpdown.style.display = 'none';
    // }
    setTimeout(() => {
      this.goLogIn();
    }, 100);
  }

  loadUserDetails() {
    if (this.token) {
      this.auth.getUserDetails(this.token).subscribe(
        (data: any) => {
          this.userData = data;
        },
        (error) => {
          console.error('Error al cargar detalles del usuario', error);
        }
      );
    }
  }
}
