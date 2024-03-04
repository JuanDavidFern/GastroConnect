import { AuthService } from '../../services/auth.service';
import { CookieService } from 'ngx-cookie-service';
import { NgForm } from '@angular/forms';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { first } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { NavigationService } from '../../services/navigation.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  private cookies: CookieService;
  photo: Observable<string>;
  logIn: any = {};
  reg: any = {};
  validations: any = {
    logIn: {},
    register: {},
  };
  @ViewChild('userInput') userInput!: ElementRef;

  constructor(
    protected auth: AuthService,
    cookieService: CookieService,
    private navigationService: NavigationService
  ) {
    this.cookies = cookieService;
    this.photo = this.sendImage('../../../assets/genericImg.jpg');
  }

  login(form: NgForm) {
    this.validations.logIn.exist = true;
    if (this.logIn.username.length < 3) {
      this.validations.logIn.username = false;
      return;
    } else {
      this.validations.logIn.username = true;
    }

    if (this.logIn.password.length < 3) {
      this.validations.logIn.password = false;
      return;
    } else {
      this.validations.logIn.password = true;
    }

    this.auth
      .login(this.logIn.username, this.logIn.password)
      .pipe(first())
      .subscribe(
        (data: any) => {
          if (data) {
            this.cookies.set('token', data.token);
            this.navigationService.goHome();
          }
        },
        (error: any) => {
          console.log(error);
          if (error.status == 404) {
            this.validations.logIn.exist = false;
          }
        }
      );
  }

  register(form: NgForm) {
    this.validations.register.exist = true;
    if (this.reg.username.length < 3) {
      this.validations.register.username = false;
      return;
    } else {
      this.validations.register.username = true;
    }

    if (!this.validateMail(this.reg.email)) {
      this.validations.register.email = false;
      return;
    } else {
      this.validations.register.email = true;
    }

    if (this.reg.password.length < 3) {
      this.validations.register.password = false;
      return;
    } else {
      this.validations.register.password = true;
    }

    this.photo.subscribe((photo) => {
      this.auth
        .register(this.reg.username, this.reg.password, this.reg.email, photo)
        .pipe(first())
        .subscribe((data: any) => {
          if (data) {
            this.cookies.set('token', data.token);
            this.navigationService.goHome();
          }
        });
    });
  }

  ngAfterViewInit() {
    // Verificar si la referencia es válida antes de darle el foco
    if (this.userInput && this.userInput.nativeElement) {
      // Darle el foco al elemento
      this.userInput.nativeElement.focus();
    }
  }

  showRegister() {
    const container = document.getElementById('container');
    if (container != null) {
      container.classList.add('active');
    }
  }

  showLogIn() {
    const container = document.getElementById('container');
    if (container != null) {
      container.classList.remove('active');
    }
  }

  sendImage(imagePath: string): Observable<string> {
    return new Observable<string>((observer) => {
      this.readFile(imagePath, (base64Image: string) => {
        const base64Data = base64Image.split(';base64,').pop();
        observer.next(base64Data);
        observer.complete();
      });
    });
  }

  readFile(filePath: string, callback: (result: string) => void): void {
    const xhr = new XMLHttpRequest();
    xhr.onload = function () {
      const reader = new FileReader();
      reader.onloadend = function () {
        callback(reader.result as string);
      };
      reader.readAsDataURL(xhr.response);
    };
    xhr.open('GET', filePath);
    xhr.responseType = 'blob';
    xhr.send();
  }

  validateMail(mail: string) {
    var pattronMail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (pattronMail.test(mail)) {
      if (mail.indexOf('@') !== -1 && mail.indexOf('.') !== -1) {
        var part = mail.split('@');
        if (
          part[part.length - 1].indexOf('.') - part[part.length - 1].length !==
          -1
        ) {
          return true;
        }
      }
    }

    return false;
  }
}
