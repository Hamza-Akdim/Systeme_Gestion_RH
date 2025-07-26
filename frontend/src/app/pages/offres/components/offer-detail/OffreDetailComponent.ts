// offre-detail.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'offre-detail',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="text-white p-6 space-y-6">
      <h2 class="text-2xl font-bold">{{ offre?.titre }}</h2>

      <div class="text-gray-300">
        <p><strong>Localisation:</strong> {{ offre?.localisation }}</p>
        <p><strong>Domaine:</strong> {{ offre?.domaine }}</p>
        <p><strong>Secteur:</strong> {{ offre?.secteur }}</p>
        <p><strong>Catégorie:</strong> {{ offre?.categorie }}</p>
        <p><strong>Contrat:</strong> {{ offre?.contrat }}</p>
      </div>

      <p class="text-gray-200 leading-relaxed">{{ offre?.description }}</p>

      <button class="bg-[#009FE3] text-white px-6 py-3 rounded font-semibold">
        Postuler
      </button>
    </div>
  `
})
export class OffreDetailComponent {
  offre: any;

  constructor(private route: ActivatedRoute) {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    const offres = [
      {
        id: 0,
        titre: 'Développeur/euse Murex – Anglais intermédiaire',
        localisation: 'Madrid, Espagne',
        domaine: 'Développement',
        secteur: 'Banque',
        categorie: 'Développement',
        contrat: 'CDI',
        description: `Quelle opportunité proposons-nous ? ...`
      },
      {
        id: 1,
        titre: 'Architecte Solutions de Paiement – Anglais avancé',
        localisation: 'Madrid, Espagne',
        domaine: 'Architecture',
        secteur: 'Banque',
        categorie: 'Architecture',
        contrat: 'Freelance',
        description: `Nous recherchons un(e) architecte...`
      },
      {
        id: 2,
        titre: 'Chef de projet SAP ABAP (S4 HANA)',
        localisation: 'Noida, Inde',
        domaine: 'Gestion de projet',
        secteur: 'IT',
        categorie: 'Gestion de projet',
        contrat: 'CDI',
        description: `Expérience forte avec SAP ABAP...`
      }
    ];

    this.offre = offres.find(o => o.id === id);
  }
}
