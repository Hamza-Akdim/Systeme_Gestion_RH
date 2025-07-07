import { Routes } from '@angular/router';

const routes: Routes = [
    {
        path: 'login',
        loadComponent: () => import('./login/login.component').then(m => m.LoginComponent),
        data: { breadcrumb: 'Connexion' }
    },
    {
        path: 'register',
        loadComponent: () => import('./register/register.component').then(m => m.RegisterComponent),
        data: { breadcrumb: 'Inscription' }
    },
    {
        path: 'forgotpassword',
        loadComponent: () => import('./forgot-password/forgot-password.component').then(m => m.ForgotPasswordComponent),
        data: { breadcrumb: 'Mot de passe oublié' }
    },
    {
        path: 'resetpassword',
        loadComponent: () => import('./reset-password/reset-password.component').then(m => m.ResetPasswordComponent),
        data: { breadcrumb: 'Réinitialisation du mot de passe' }
    },
    {
        path: 'profile',
        loadComponent: () => import('./profile/profile.component').then(m => m.ProfileComponent),
        data: { breadcrumb: 'Profil' }
    }
];

export default routes;
