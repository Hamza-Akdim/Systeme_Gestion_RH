import { Routes } from '@angular/router';
import { AppLayout } from '@/layout/components/app.layout';

export const appRoutes: Routes = [
    {
        path: '',
        component: AppLayout,
        children: [
            {
                path: '',
                data: { breadcrumb: 'Job offers' },
                loadChildren: () => import('@/pages/offres/offer.routes').then((c) => c.OFFER_ROUTES)
            },
            {
                path: 'hr/job-offer',
                data: { breadcrumb: 'Offer management' },
                loadComponent: () => import('@/pages/job-offer/JobOfferHrDashboard').then((c) => c.JobOfferHrDashboard)
            },
            {
                path: 'admin/job-offer',
                data: { breadcrumb: 'Offer management' },
                loadComponent: () => import('@/pages/job-offer/JobOfferHrDashboard').then((c) => c.JobOfferHrDashboard)
            },
            {
                path: 'hr/job-offer/createOffer',
                data: { breadcrumb: 'Offer management / Create' },
                loadComponent: () => import('@/pages/job-offer/components/job-offer-form/JobOfferFormComponent').then((c) => c.JobOfferFormComponent)
            },
            {
                path: 'profile',
                data: { breadcrumb: 'User Management' },
                loadChildren: () => import('@/pages/usermanagement/usermanagement.routes')
            },
            {
                path: 'pages',
                data: { breadcrumb: 'Pages' },
                loadChildren: () => import('@/pages/pages.routes')
            }
            // {
            //     path: 'uikit',
            //     data: { breadcrumb: 'UI Kit' },
            //     loadChildren: () => import('@/pages/uikit/uikit.routes')
            // },

            // {
            //     path: 'apps',
            //     data: { breadcrumb: 'Apps' },
            //     loadChildren: () => import('./app/apps/apps.routes')
            // },
            // {
            //     path: 'blocks',
            //     data: { breadcrumb: 'Prime Blocks' },
            //     loadChildren: () => import('@/pages/blocks/blocks.routes')
            // },
        ]
    },
    { path: 'auth', loadChildren: () => import('@/pages/auth/auth.routes') },

    {
        path: 'apply',
        loadComponent: () => import('@/pages/profile-cv/profile-cv').then((c) => c.ProfileCvComponent)
    },
    {
        path: 'landing',
        loadComponent: () => import('@/pages/landing/landing').then((c) => c.Landing)
    },
    {
        path: 'notfound',
        loadComponent: () => import('@/pages/notfound/notfound').then((c) => c.Notfound)
    },
    { path: '**', redirectTo: '/notfound' }
];
