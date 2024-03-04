import { CanActivateFn } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { NavigationService } from '../services/navigation.service';
import { inject } from '@angular/core';

export const unauthenticatedGuard: CanActivateFn = (route, state) => {
  const cookies = inject(CookieService);
  const navigationService = inject(NavigationService);

  if (cookies.get('token') === '') {
    return true;
  } else {
    navigationService.goHomeGuard();
    return false;
  }
};
