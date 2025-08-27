import { Routes } from '@angular/router';

export const OFFER_ROUTES: Routes = [
    {
        path: '',
        children: [
            {
                path: '',
                loadComponent: () => import('./OffresDashboard').then((m) => m.OffresDashboard)
            },
            {
                path: 'offer-details/:id',
                loadComponent: () => import('./components/offer-detail/OffreDetailComponent').then((m) => m.OffreDetailComponent),
                data: { breadcrumb: 'Offer Details' }
            }
        ]
    }
];
