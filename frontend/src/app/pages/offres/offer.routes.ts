import { Routes } from '@angular/router';

export const OFFER_ROUTES: Routes = [
    {
        path: '',
        data: { breadcrumb: "Offres d'emploi" },
        children: [
            {
                path: '',
                loadComponent: () => import('./OffresDashboard').then((m) => m.OffresDashboard),
                data: { breadcrumb: "Offres d'emploi" }
            },
            {
                path: 'offre-details/:id',
                loadComponent: () => import('./components/offer-detail/OffreDetailComponent').then((m) => m.OffreDetailComponent),
                data: { breadcrumb: "Détails de l'offre" }
            }
        ]
    }
];
