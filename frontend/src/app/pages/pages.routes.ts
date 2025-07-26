import { Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './auth/profile/profile.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

export default [
    // Ajout de cette ligne pour charger le module candidate
    {
        path: 'candidate',
        data: { breadcrumb: 'Candidats' },
        loadChildren: () => import('./candidate/candidate.routes')
    },
    {
        path: 'business',
        data: { breadcrumb: 'Business' },
        loadChildren: () => import('./business/business.routes')
    },

    { path: 'profile', data: { breadcrumb: 'Profile' }, component: ProfileComponent },
    { path: '**', redirectTo: '/notfound' }
] as Routes;
