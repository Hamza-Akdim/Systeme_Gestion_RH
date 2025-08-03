import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OfferCardComponent } from './components/OfferCardComponent';
import { OffresFilterBarComponent } from './components/OffersFilterBarComponent';

@Component({
    selector: 'app-offres-dashboard',
    standalone: true,
    imports: [CommonModule, OfferCardComponent, OffresFilterBarComponent],
    template: `
        <div class="p-6 space-y-6">
            <offres-filter-bar (filtersChanged)="onFiltersChanged($event)"></offres-filter-bar>

            <div class="grid grid-cols-1 2xl:grid-cols-2 gap-6">
                <offer-card *ngFor="let offre of filteredOffres; let i = index" [offre]="offre" [index]="i" />
            </div>
        </div>
    `
})
export class OffresDashboard {
    offres = [
        {
            id: 1,
            title: 'Développeur/euse Murex – Anglais intermédiaire',
            description: 'Quelle opportunité proposons-nous ? ...',
            contrat: 'CDI',
            secteur: 'SOFTWARE_DEVELOPMENT',
            hardSkills: [
                { id: 1, title: 'Murex', level: 'Avancé' },
                { id: 2, title: 'Java', level: 'Intermédiaire' }
            ],
            status: 'OUVERTE',
            closingDate: '2025-08-31',
            taskMissions: [{ id: 1, title: 'Développement de modules Murex' }],
            createdAt: '2025-07-15T10:00:00Z',
            updatedAt: '2025-07-15T10:00:00Z'
        },
        {
            id: 2,
            title: 'Chef de projet IT – Télécommunications',
            description: 'Responsable de la gestion de projets télécoms pour un grand opérateur.',
            contrat: 'CDI',
            secteur: 'TELECOMMUNICATIONS',
            hardSkills: [
                { id: 3, title: 'Gestion de projet', level: 'Avancé' },
                { id: 4, title: 'Télécom', level: 'Intermédiaire' }
            ],
            status: 'OUVERTE',
            closingDate: '2025-09-15',
            taskMissions: [
                { id: 2, title: 'Pilotage de projets' },
                { id: 3, title: "Coordination d'équipes" }
            ],
            createdAt: '2025-07-20T09:00:00Z',
            updatedAt: '2025-07-20T09:00:00Z'
        },
        {
            id: 3,
            title: 'Data Analyst – Secteur énergie',
            description: 'Analyse de données énergétiques et reporting pour un acteur majeur du secteur.',
            contrat: 'CDD',
            secteur: 'ENERGY_MANAGEMENT',
            hardSkills: [
                { id: 5, title: 'Python', level: 'Avancé' },
                { id: 6, title: 'DataViz', level: 'Intermédiaire' }
            ],
            status: 'FERMEE',
            closingDate: '2025-07-31',
            taskMissions: [
                { id: 4, title: 'Extraction de données' },
                { id: 5, title: 'Création de dashboards' }
            ],
            createdAt: '2025-06-10T14:00:00Z',
            updatedAt: '2025-07-01T10:00:00Z'
        },
        {
            id: 4,
            title: 'Technicien support IT',
            description: 'Assistance technique et support utilisateurs pour une grande entreprise.',
            contrat: 'STAGE',
            secteur: 'IT_SUPPORT',
            hardSkills: [
                { id: 7, title: 'Support IT', level: 'Débutant' },
                { id: 8, title: 'Windows', level: 'Débutant' }
            ],
            status: 'OUVERTE',
            closingDate: '2025-10-01',
            taskMissions: [{ id: 6, title: 'Gestion des tickets' }],
            createdAt: '2025-08-01T08:00:00Z',
            updatedAt: '2025-08-01T08:00:00Z'
        },
        {
            id: 5,
            title: 'Consultant(e) Data Solutions',
            description: 'Accompagnement des clients dans la mise en place de solutions data.',
            contrat: 'FREELANCE',
            secteur: 'DATA_SOLUTIONS',
            hardSkills: [
                { id: 9, title: 'SQL', level: 'Avancé' },
                { id: 10, title: 'Data Engineering', level: 'Avancé' }
            ],
            status: 'OUVERTE',
            closingDate: '2025-09-30',
            taskMissions: [{ id: 7, title: 'Conseil et expertise' }],
            createdAt: '2025-07-25T11:00:00Z',
            updatedAt: '2025-07-25T11:00:00Z'
        }
    ];

    filteredOffres = [...this.offres];

    onFiltersChanged(filters: any) {
        this.filteredOffres = this.offres.filter((offre) => {
            if (filters.keyword) {
                const searchableText = [
                    offre.title,
                    offre.description,
                    offre.secteur,
                    offre.contrat,
                    offre.status,
                    ...(offre.hardSkills?.map(skill => skill.title) || []),
                    ...(offre.hardSkills?.map(skill => skill.level) || []),
                    ...(offre.taskMissions?.map(mission => mission.title) || [])
                ].join(' ').toLowerCase();
                
                if (!searchableText.includes(filters.keyword)) {
                    return false;
                }
            }

            if (filters.contrat && offre.contrat !== filters.contrat) {
                return false;
            }
            
            if (filters.secteur && offre.secteur !== filters.secteur) {
                return false;
            }
            
            if (filters.status && offre.status !== filters.status) {
                return false;
            }

            return true;
        });
    }
}