// import { Injectable } from '@angular/core';
// import { HttpClient, HttpErrorResponse } from '@angular/common/http';
// import { Observable, throwError } from 'rxjs';
// import { catchError, tap } from 'rxjs/operators';
// import { User, AuthResponse } from '../models/auth.model';
// import { UserRole } from '../models/user-role';

// @Injectable({
//   providedIn: 'root'
// })
// export class AuthService {
//   private apiUrl = 'http://localhost:8765/api/auth';

//   constructor(private http: HttpClient) {}

//   signup(user: User): Observable<AuthResponse> {
//     return this.http.post<AuthResponse>(`${this.apiUrl}/signup`, user).pipe(
//       tap(response => this.setSession(response)),
//       catchError(this.handleError)
//     );
//   }

//   login(email: string, password: string): Observable<AuthResponse> {
//     return this.http.post<AuthResponse>(`${this.apiUrl}/login`, { email, password }).pipe(
//       tap(response => this.setSession(response)),
//       catchError(this.handleError)
//     );
//   }

//   private setSession(authResponse: AuthResponse): void {
//     localStorage.setItem('auth', JSON.stringify(authResponse));
//   }

//   getAuthData(): AuthResponse | null {
//     const authData = localStorage.getItem('auth');
//     return authData ? JSON.parse(authData) : null;
//   }

//   logout(): void {
//     localStorage.removeItem('auth');
//   }

//   isLoggedIn(): boolean {
//     return !!this.getAuthData();
//   }

//   getUserRole(): UserRole | null {
//     const authData = this.getAuthData();
//     return authData ? authData.userRole : null;
//   }

//   getToken(): string | null {
//     const authData = this.getAuthData();
//     return authData ? authData.jwt : null;
//   }

//   private handleError(error: HttpErrorResponse): Observable<never> {
//     let errorMessage = 'An error occurred';
//     if (error.error instanceof ErrorEvent) {
//       errorMessage = `Client error: ${error.error.message}`;
//     } else {
//       errorMessage = error.error?.message || `Server error: ${error.status}`;
//     }
//     return throwError(() => new Error(errorMessage));
//   }
// }


import { Injectable, PLATFORM_ID, Inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { User, AuthResponse } from '../models/auth.model';
import { UserRole } from '../models/user-role';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8765/api/auth';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  signup(user: User): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/signup`, user).pipe(
      tap(response => this.setSession(response)),
      catchError(this.handleError)
    );
  }

  login(email: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, { email, password }).pipe(
      tap(response => this.setSession(response)),
      catchError(this.handleError)
    );
  }

  private setSession(authResponse: AuthResponse): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('auth', JSON.stringify(authResponse));
    }
  }

  getAuthData(): AuthResponse | null {
    if (isPlatformBrowser(this.platformId)) {
      const authData = localStorage.getItem('auth');
      return authData ? JSON.parse(authData) : null;
    }
    return null;
  }

  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('auth');
    }
  }

  isLoggedIn(): boolean {
    return isPlatformBrowser(this.platformId) && !!this.getAuthData();
  }

  getUserRole(): UserRole | null {
    const authData = this.getAuthData();
    return authData ? authData.userRole : null;
  }

  getToken(): string | null {
    const authData = this.getAuthData();
    return authData ? authData.jwt : null;
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An error occurred';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Client error: ${error.error.message}`;
    } else {
      errorMessage = error.error?.message || `Server error: ${error.status}`;
    }
    return throwError(() => new Error(errorMessage));
  }
}