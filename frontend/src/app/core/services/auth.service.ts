import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Router } from '@angular/router';

export interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<User | null>;
  public currentUser: Observable<User | null>;
  
  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    const storedUser = localStorage.getItem('currentUser');
    this.currentUserSubject = new BehaviorSubject<User | null>(storedUser ? JSON.parse(storedUser) : null);
    this.currentUser = this.currentUserSubject.asObservable();
  }
  
  public get currentUserValue(): User | null {
    return this.currentUserSubject.value;
  }
  
  login(email: string, password: string): Observable<User> {
    // Simulation d'un appel API - à remplacer par un vrai appel HTTP
    return of({
      id: 1,
      firstName: 'Jean',
      lastName: 'Dupont',
      email: email,
      role: 'ADMIN'
    }).pipe(
      tap(user => {
        // Stocker les détails de l'utilisateur et le token JWT dans le localStorage
        localStorage.setItem('currentUser', JSON.stringify(user));
        localStorage.setItem('auth_token', 'fake-jwt-token');
        this.currentUserSubject.next(user);
        return user;
      })
    );
  }
  
  register(user: any): Observable<any> {
    // Simulation d'un appel API - à remplacer par un vrai appel HTTP
    return of({ success: true, message: 'Inscription réussie' }).pipe(
      catchError(error => throwError(() => new Error('Erreur lors de l\'inscription')))
    );
  }
  
  forgotPassword(email: string): Observable<any> {
    // Simulation d'un appel API - à remplacer par un vrai appel HTTP
    return of({ success: true, message: 'Email de réinitialisation envoyé' }).pipe(
      catchError(error => throwError(() => new Error('Erreur lors de l\'envoi de l\'email')))
    );
  }
  
  resetPassword(token: string, password: string): Observable<any> {
    // Simulation d'un appel API - à remplacer par un vrai appel HTTP
    return of({ success: true, message: 'Mot de passe réinitialisé avec succès' }).pipe(
      catchError(error => throwError(() => new Error('Erreur lors de la réinitialisation du mot de passe')))
    );
  }
  
  updateProfile(userData: any): Observable<any> {
    // Simulation d'un appel API - à remplacer par un vrai appel HTTP
    return of({ success: true, message: 'Profil mis à jour avec succès' }).pipe(
      tap(() => {
        const currentUser = this.currentUserValue;
        if (currentUser) {
          const updatedUser = { ...currentUser, ...userData };
          localStorage.setItem('currentUser', JSON.stringify(updatedUser));
          this.currentUserSubject.next(updatedUser);
        }
      }),
      catchError(error => throwError(() => new Error('Erreur lors de la mise à jour du profil')))
    );
  }
  
  changePassword(currentPassword: string, newPassword: string): Observable<any> {
    // Simulation d'un appel API - à remplacer par un vrai appel HTTP
    return of({ success: true, message: 'Mot de passe changé avec succès' }).pipe(
      catchError(error => throwError(() => new Error('Erreur lors du changement de mot de passe')))
    );
  }
  
  logout(): void {
    // Supprimer l'utilisateur du localStorage
    localStorage.removeItem('currentUser');
    localStorage.removeItem('auth_token');
    this.currentUserSubject.next(null);
    this.router.navigate(['/auth/login']);
  }
  
  isLoggedIn(): boolean {
    return !!this.currentUserValue && !!localStorage.getItem('auth_token');
  }
}
