import { Routes } from '@angular/router';

export const ADMIN_ROUTES: Routes = [
    {
        path: 'users',
        loadComponent: () => import('./users/user-list.component').then(m => m.UserListComponent),
        data: { breadcrumb: 'Utilisateurs' }
    },
    {
        path: 'users/new',
        loadComponent: () => import('./users/user-form.component').then(m => m.UserFormComponent),
        data: { breadcrumb: 'Nouvel utilisateur' }
    },
    {
        path: 'users/edit/:id',
        loadComponent: () => import('./users/user-form.component').then(m => m.UserFormComponent),
        data: { breadcrumb: 'Modifier utilisateur' }
    },
    {
        path: 'roles',
        loadComponent: () => import('./roles/role-list.component').then(m => m.RoleListComponent),
        data: { breadcrumb: 'Rôles' }
    },
    {
        path: 'roles/new',
        loadComponent: () => import('./roles/role-form.component').then(m => m.RoleFormComponent),
        data: { breadcrumb: 'Nouveau rôle' }
    },
    {
        path: 'roles/edit/:id',
        loadComponent: () => import('./roles/role-form.component').then(m => m.RoleFormComponent),
        data: { breadcrumb: 'Modifier rôle' }
    },
    {
        path: 'settings',
        loadComponent: () => import('./settings/settings.component').then(m => m.SettingsComponent),
        data: { breadcrumb: 'Paramètres système' }
    }
];
