import { Routes } from '@angular/router';

export default [
    {
        path: '',
        data: { breadcrumb: 'Candidats' },
        children: [
            {
                path: '',
                loadComponent: () => import('./candidate-list/candidate-list.component').then(m => m.CandidateListComponent),
                data: { breadcrumb: 'Liste des candidats' }
            },
            {
                path: 'new',
                loadComponent: () => import('./candidate-form/candidate-form.component').then(m => m.CandidateFormComponent),
                data: { breadcrumb: 'Nouveau candidat' }
            },
            {
                path: ':id',
                loadComponent: () => import('./candidate-detail/candidate-detail.component').then(m => m.CandidateDetailComponent),
                data: { breadcrumb: 'Détails du candidat' }
            },
            {
                path: ':id/edit',
                loadComponent: () => import('./candidate-form/candidate-form.component').then(m => m.CandidateFormComponent),
                data: { breadcrumb: 'Modifier le candidat' }
            },
            {
                path: 'applications',
                loadComponent: () => import('./application-list/application-list.component').then(m => m.ApplicationListComponent),
                data: { breadcrumb: 'Liste des candidatures' }
            },
            {
                path: 'applications/new',
                loadComponent: () => import('./application-form/application-form.component').then(m => m.ApplicationFormComponent),
                data: { breadcrumb: 'Nouvelle candidature' }
            },
            {
                path: 'applications/:id',
                loadComponent: () => import('./application-detail/application-detail.component').then(m => m.ApplicationDetailComponent),
                data: { breadcrumb: 'Détails de la candidature' }
            },
            {
                path: 'applications/:id/edit',
                loadComponent: () => import('./application-form/application-form.component').then(m => m.ApplicationFormComponent),
                data: { breadcrumb: 'Modifier la candidature' }
            }
        ]
    }
] as Routes;
