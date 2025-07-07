import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const currentUser = this.authService.currentUserValue;
    
    if (currentUser) {
      // Vérifier si la route a des restrictions de rôle
      if (route.data['roles'] && !route.data['roles'].includes(currentUser.role)) {
        // Rediriger vers la page d'accueil si l'utilisateur n'a pas le rôle requis
        this.router.navigate(['/']);
        return false;
      }
      
      // Utilisateur connecté et autorisé
      return true;
    }
    
    // Utilisateur non connecté, rediriger vers la page de connexion
    this.router.navigate(['/auth/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }
}
