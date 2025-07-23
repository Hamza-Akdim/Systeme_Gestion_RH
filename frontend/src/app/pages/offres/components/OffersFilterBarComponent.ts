// offres-filter-bar.component.ts
import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'offres-filter-bar',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
        <div class="bg-[#1E182D] text-white p-6 rounded-xl shadow-md space-y-4">

            <div class="flex flex-wrap items-center gap-4">
                <div class="flex items-center border border-[#2D2A3F] rounded px-3 py-3 bg-[#12101C] w-full sm:w-auto flex-1 ">
                    <input [(ngModel)]="keyword" placeholder="Métier, mots-clés…" class="bg-transparent outline-none text-white text-lg w-full placeholder-gray-400" />
                </div>

                <button (click)="applyFilters()" class="bg-[#0d6efd] text-lg text-white font-bold py-2 px-6 rounded shadow hover:bg-[#3885f9] transition">Rechercher</button>
            </div>

            <!-- Bottom row: dropdowns -->
            <div class="flex flex-wrap gap-4">
                <select [(ngModel)]="secteur" class="bg-[#12101C] text-lg p-2 rounded text-white border py-2 px-5 border-[#2D2A3F]">
                    <option value="">Secteur</option>
                    <option value="Banque">Banque</option>
                    <option value="IT">IT</option>
                    <option value="Industrie">Industrie</option>
                </select>

                <select [(ngModel)]="categorie" class="bg-[#12101C] text-lg p-2 rounded text-white border py-2 px-5 border-[#2D2A3F]">
                    <option value="">Catégorie</option>
                    <option value="Développement">Développement</option>
                    <option value="Architecture">Architecture</option>
                    <option value="Gestion de projet">Gestion de projet</option>
                </select>

                <select [(ngModel)]="contrat" class="bg-[#12101C] text-lg p-2 rounded text-white border py-2 px-5 border-[#2D2A3F]">
                    <option value="">Contrats</option>
                    <option value="CDI">CDI</option>
                    <option value="CDD">CDD</option>
                    <option value="Freelance">Freelance</option>
                    <option value="Stage">Stage</option>
                </select>

                <button (click)="resetFilters()" class="text-gray-400 hover:text-white flex items-center gap-1">Réinitialiser</button>
            </div>
        </div>
    `
})
export class OffresFilterBarComponent {
    keyword = '';
    secteur = '';
    categorie = '';
    contrat = '';

    @Output() filtersChanged = new EventEmitter<any>();

    applyFilters() {
        this.filtersChanged.emit({
            keyword: this.keyword.toLowerCase(),
            secteur: this.secteur,
            categorie: this.categorie,
            contrat: this.contrat
        });
    }

    resetFilters() {
        this.keyword = '';
        this.secteur = '';
        this.categorie = '';
        this.contrat = '';
        this.applyFilters();
    }
}
