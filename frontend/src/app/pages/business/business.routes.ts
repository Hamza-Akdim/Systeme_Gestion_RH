import { Routes } from '@angular/router';

export default [
    {
        path: '',
        data: { breadcrumb: 'Business' },
        children: [
            {
                path: '',
                loadComponent: () => import('./prospect-list/prospect-list.component').then(m => m.ProspectListComponent),
                data: { breadcrumb: 'Prospects' }
            },
            {
                path: 'new',
                loadComponent: () => import('./prospect-form/prospect-form.component').then(m => m.ProspectFormComponent),
                data: { breadcrumb: 'Nouveau prospect' }
            },
            {
                path: ':id',
                loadComponent: () => import('./prospect-detail/prospect-detail.component').then(m => m.ProspectDetailComponent),
                data: { breadcrumb: 'Détails du prospect' }
            },
            {
                path: ':id/edit',
                loadComponent: () => import('./prospect-form/prospect-form.component').then(m => m.ProspectFormComponent),
                data: { breadcrumb: 'Modifier le prospect' }
            },
            {
                path: 'surveys',
                loadComponent: () => import('./business-survey-list/business-survey-list.component').then(m => m.BusinessSurveyListComponent),
                data: { breadcrumb: 'Enquêtes commerciales' }
            },
            {
                path: 'surveys/new',
                loadComponent: () => import('./business-survey-form/business-survey-form.component').then(m => m.BusinessSurveyFormComponent),
                data: { breadcrumb: 'Nouvelle enquête' }
            },
            {
                path: 'surveys/:id',
                loadComponent: () => import('./business-survey-detail/business-survey-detail.component').then(m => m.BusinessSurveyDetailComponent),
                data: { breadcrumb: 'Détails de l\'enquête' }
            },
            {
                path: 'surveys/:id/edit',
                loadComponent: () => import('./business-survey-form/business-survey-form.component').then(m => m.BusinessSurveyFormComponent),
                data: { breadcrumb: 'Modifier l\'enquête' }
            }
        ]
    }
] as Routes;
