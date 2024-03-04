import { Component, ElementRef, ViewChild } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { MyUserProfileSettings } from '../../interfaces/MyUserProfileSettings';
import { CookieService } from 'ngx-cookie-service';
import { RecipeToCard } from '../../interfaces/RecipeToCard';
import { HttpClient } from '@angular/common/http';
import { NavigationService } from '../../services/navigation.service';

@Component({
  selector: 'app-user-setting',
  templateUrl: './user-setting.component.html',
  styleUrl: './user-setting.component.css',
})
export class UserSettingComponent {
  @ViewChild('usernameInput') usernameInput!: ElementRef<HTMLInputElement>;
  @ViewChild('emailInput') emailInput!: ElementRef<HTMLInputElement>;
  @ViewChild('oldPassInput') oldPassInput!: ElementRef<HTMLInputElement>;
  @ViewChild('newPassInput') newPassInput!: ElementRef<HTMLInputElement>;
  @ViewChild('newRepeatPassInput')
  newRepeatPassInput!: ElementRef<HTMLInputElement>;
  @ViewChild('usernameToDelete')
  usernameToDelete!: ElementRef<HTMLInputElement>;
  @ViewChild('passToDelete') passToDelete!: ElementRef<HTMLInputElement>;
  profile!: MyUserProfileSettings;
  loading: boolean = true; // Variable para controlar la carga
  token: string = '';
  recipes: RecipeToCard[] = [];
  isEditing = false;
  isChangingPassword = false;
  validEmail = true;
  validName = true;
  validChanges = true;
  samePass = true;
  newPassShort = false;
  sameOldPass = true;
  isDeleting = false;
  failedToDelete = false;
  private cookies: CookieService;
  oldName = '';
  oldMail = '';
  oldAvatar: String = '';

  constructor(
    protected auth: AuthService,
    private http: HttpClient,
    cookieService: CookieService,
    private navigationService: NavigationService
  ) {
    this.cookies = cookieService;
    this.token = this.cookies.get('token');
  }

  ngOnInit() {
    this.getMiProfileProfile();
    this.getRecipes();
  }
  delete() {
    if (this.isDeleting) this.deleteAccount();
    setTimeout(() => {
      if (!this.failedToDelete) this.isDeleting = !this.isDeleting;
    }, 100);
  }

  dontDelete() {
    this.isDeleting = !this.isDeleting;
  }

  changePassword() {
    if (this.isChangingPassword) {
      if (
        this.newPassInput.nativeElement.value ==
        this.newRepeatPassInput.nativeElement.value
      ) {
        this.samePass = true;
        if (this.newPassInput.nativeElement.value.length >= 3) {
          this.newPassShort = false;
          this.sendNewPass();
        } else {
          this.newPassShort = true;
        }
      } else {
        this.samePass = false;
      }
    }
    setTimeout(() => {
      if (this.sameOldPass && this.samePass && !this.newPassShort)
        this.isChangingPassword = !this.isChangingPassword;
    }, 100);
  }

  dontChangePass() {
    this.isChangingPassword = !this.isChangingPassword;
  }

  stopEdit() {
    this.emailInput.nativeElement.value = this.oldMail;
    this.usernameInput.nativeElement.value = this.oldName;
    this.isEditing = !this.isEditing;
    this.profile.avatar = this.oldAvatar;
    this.usernameInput.nativeElement.readOnly = true;
    this.emailInput.nativeElement.readOnly = true;
  }

  stopDelete() {
    this.isDeleting = !this.isDeleting;
  }

  stopChangePass() {
    this.isChangingPassword = !this.isChangingPassword;
  }

  getMiProfileProfile() {
    this.auth.getMyProfile(this.token).subscribe(
      (data: MyUserProfileSettings) => {
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

  public formatTimestamp(timestamp: string) {
    const date = new Date(parseInt(timestamp));
    // Utilizar toLocaleDateString() en lugar de toLocaleString()
    return date.toLocaleDateString();
  }

  getRecipes() {
    this.http
      .get<RecipeToCard[]>(
        `http://localhost:8080/recipes/getMyRecipes/Bearer ${this.token}`
      )
      .subscribe((data) => {
        this.recipes = data;
      });
  }

  changeProfile() {
    this.oldAvatar = this.profile.avatar;
    this.oldMail = this.profile.email;
    this.oldName = this.profile.username;
    if (this.isEditing) {
      if (this.usernameInput.nativeElement.value.length < 3) {
        this.validName = false;
        return;
      }
      if (!this.validateMail(this.emailInput.nativeElement.value)) {
        this.validEmail = false;
        return;
      }
      this.validEmail = true;
      this.validName = true;

      this.sendProfile();

      this.usernameInput.nativeElement.readOnly = true;
      this.emailInput.nativeElement.readOnly = true;
    } else {
      // Cambiar el input a modo de edición
      this.usernameInput.nativeElement.readOnly = false;
      this.emailInput.nativeElement.readOnly = false;
    }
    setTimeout(() => {
      if (this.validChanges) this.isEditing = !this.isEditing;
      else {
        // Cambiar el input a modo de edición
        this.usernameInput.nativeElement.readOnly = false;
        this.emailInput.nativeElement.readOnly = false;
      }
    }, 100);
    // Cambiar el estado de edición
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

  sendProfile() {
    this.auth
      .sendProfile(
        `Bearer ${this.token}`,
        this.usernameInput.nativeElement.value,
        this.emailInput.nativeElement.value,
        this.profile.avatar
      )
      .subscribe(
        (data) => {
          if (data) {
            window.location.reload();
          }
        },
        (error) => {
          if ((error.status = 409)) {
            this.validChanges = false;
          }
        }
      );
  }

  sendNewPass() {
    this.auth
      .sendPass(
        `Bearer ${this.token}`,
        this.oldPassInput.nativeElement.value,
        this.newPassInput.nativeElement.value
      )
      .subscribe(
        (data) => {
          this.sameOldPass = true;
        },
        (error) => {
          if ((error.status = 409)) {
            this.sameOldPass = false;
          }
        }
      );
  }

  deleteAccount() {
    this.auth
      .deleteAccount(
        this.usernameToDelete.nativeElement.value,
        this.passToDelete.nativeElement.value
      )
      .subscribe(
        (data) => {
          this.cookies.delete('token');
          setTimeout(() => {
            this.goLogIn();
          }, 100);
        },
        (error) => {
          if ((error.status = 409)) {
            this.failedToDelete = true;
          }
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
            this.profile.avatar = base64Data;
          }
        }
      };
    }
  }

  goLogIn() {
    this.navigationService.goLogIn();
  }
}
