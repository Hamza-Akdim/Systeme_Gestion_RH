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
                <offer-card *ngFor="let offre of filteredOffres" [offre]="offre" />
            </div>
        </div>
    `
})
export class OffresDashboard {
    offres = [
        {
            titre: 'Développeur/euse Murex – Anglais intermédiaire',
            localisation: 'Madrid, Espagne',
            domaine: 'Développement',
            secteur: 'Banque',
            categorie: 'Développement',
            contrat: 'CDI',
            description: `Quelle opportunité proposons-nous ? ...`
        },
        {
            titre: 'Architecte Solutions de Paiement – Anglais avancé',
            localisation: 'Madrid, Espagne',
            domaine: 'Architecture',
            secteur: 'Banque',
            categorie: 'Architecture',
            contrat: 'Freelance',
            description: `Nous recherchons un(e) architecte...`
        },
        {
            titre: 'Chef de projet SAP ABAP (S4 HANA)',
            localisation: 'Noida, Inde',
            domaine: 'Gestion de projet',
            secteur: 'IT',
            categorie: 'Gestion de projet',
            contrat: 'CDI',
            description: `Expérience forte avec SAP ABAP...`
        }
    ];

    filteredOffres = [...this.offres];

    onFiltersChanged(filters: any) {
        this.filteredOffres = this.offres.filter((offre) => {
            return (
                (!filters.keyword || offre.titre.toLowerCase().includes(filters.keyword)) &&
                (!filters.secteur || offre.secteur === filters.secteur) &&
                (!filters.categorie || offre.categorie === filters.categorie) &&
                (!filters.contrat || offre.contrat === filters.contrat)
            );
        });
    }
}
