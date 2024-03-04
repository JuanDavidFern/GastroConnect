import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserToLog } from '../interfaces/UserToLog';
import { UserToRegister } from '../interfaces/UserToRegister';
import { NavbarUserData } from '../interfaces/NavbarUserData';
import { FollowCard } from '../interfaces/FollowCard';
import { OtherUserProfile } from '../interfaces/OtherUserProfile';
import { MyUserProfileSettings } from '../interfaces/MyUserProfileSettings';
import { ChangeUserProfile } from '../interfaces/ChangeUserProfile';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<string> {
    const userToLog: UserToLog = { username, password }; // Crea un objeto con las credenciales

    return this.http.post<string>(
      'http://localhost:8080/user/logIn',
      userToLog
    );
  }

  getFollowers(token: string): Observable<FollowCard[]> {
    return this.http.get<FollowCard[]>(
      `http://localhost:8080/follower/getFollowers/Bearer ${token}`
    );
  }

  getFollowings(token: string): Observable<FollowCard[]> {
    return this.http.get<FollowCard[]>(
      `http://localhost:8080/follower/getFolloweds/Bearer ${token}`
    );
  }

  getOtherUserProfile(
    userId: string,
    token: string
  ): Observable<OtherUserProfile> {
    return this.http.get<OtherUserProfile>(
      `http://localhost:8080/user/getProfileById/${userId}?token=Bearer ${token}`
    );
  }

  getMyProfile(token: string): Observable<MyUserProfileSettings> {
    return this.http.get<MyUserProfileSettings>(
      `http://localhost:8080/user/getMyProfile?token=Bearer ${token}`
    );
  }

  changeFollow(userId: string, token: string, follow: boolean) {
    let changeFollow = {
      userId: parseInt(userId),
      token: `Bearer ${token}`,
      follow: follow,
    };
    return this.http.post<any>(
      `http://localhost:8080/user/changeFollow`,
      changeFollow
    );
  }

  register(
    username: string,
    password: string,
    email: string,
    photo: any
  ): Observable<string> {
    const userToRegister: UserToRegister = { username, password, email, photo }; // Crea un objeto con las credenciales
    return this.http.post<string>(
      'http://localhost:8080/user/register',
      userToRegister
    );
  }

  getUserDetails(token: string): Observable<NavbarUserData> {
    // Hacer una llamada al servidor para obtener los detalles del usuario
    return this.http.get<NavbarUserData>(
      `http://localhost:8080/user/getDetailsByToken/Bearer ${token}`
    );
  }

  sendProfile(token: string, username: string, email: string, avatar: String) {
    const changeProfile: ChangeUserProfile = {
      token: token,
      username: username,
      email: email,
      avatar: avatar,
    };
    return this.http.post<any>(
      'http://localhost:8080/user/changeProfile',
      changeProfile
    );
  }

  sendPass(token: string, oldPass: string, newPass: string) {
    const changePass = { token: token, oldPass: oldPass, newPass: newPass };
    return this.http.post<any>(
      'http://localhost:8080/user/changePass',
      changePass
    );
  }

  deleteAccount(username: string, pass: string) {
    return this.http.delete<any>(
      `http://localhost:8080/user/deleteAccount?username=${username}&pass=${pass}`
    );
  }
}
