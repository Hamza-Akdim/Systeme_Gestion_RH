import { Routes } from '@angular/router';
import { AuthGuard } from '../core/services/auth.guard';

export const APP_ROUTES: Routes = [
  {
    path: '',
    loadChildren: () => import('./pages/offres/offer.routes').then(m => m.OFFER_ROUTES),
    canActivate: [AuthGuard]
  },
  {
    path: 'admin',
    loadChildren: () => import('./pages/admin/admin.routes').then(m => m.ADMIN_ROUTES),
    canActivate: [AuthGuard],
    data: { roles: ['ADMIN'] }
  },
  {
    path: 'auth',
    loadChildren: () => import('./pages/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'profile',
    loadComponent: () => import('./pages/auth/profile/profile.component').then(m => m.ProfileComponent),
    canActivate: [AuthGuard]
  },
  {
    path: '**',
    redirectTo: '/notfound'
  }
];
