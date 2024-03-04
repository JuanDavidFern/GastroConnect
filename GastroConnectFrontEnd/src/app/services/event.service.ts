// event.service.ts
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EventService {
  private reloadNavbarSubject = new Subject<void>();

  reloadNavbar$ = this.reloadNavbarSubject.asObservable();

  triggerNavbarReload() {
    this.reloadNavbarSubject.next();
  }
}
