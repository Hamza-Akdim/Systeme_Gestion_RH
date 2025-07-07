import { Routes } from '@angular/router';

export const GESTION_CV_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./gestion-cv.component').then((m) => m.GestionCvComponent),
    data: { breadcrumb: 'CV' }
  }
];
