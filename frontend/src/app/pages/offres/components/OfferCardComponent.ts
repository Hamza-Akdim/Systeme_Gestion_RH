import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
    selector: 'offer-card',
    standalone: true,
    imports: [CommonModule],
    template: `
        <div
            (click)="goToDetail()"
            class="cursor-pointer bg-[#1a2238] text-white rounded-2xl p-6 relative shadow-lg border border-[#28304a] transition-all duration-300 
                   hover:shadow-2xl hover:scale-[1.03] hover:border-[#9daaf2] flex flex-col h-full"
        >
            <div class="flex justify-between items-start mb-4">
                <h3 class="text-2xl font-bold text-white group-hover:text-[#9daaf2] transition-colors duration-300 leading-tight pr-4 flex-1">
                    {{ offre.title }}
                </h3>
                <span
                    class="px-3 py-1 rounded-full text-xs font-semibold shadow flex-shrink-0"
                    [ngClass]="{
                        'bg-green-200 text-green-800': offre.status === 'OUVERTE',
                        'bg-red-200 text-red-800': offre.status !== 'OUVERTE'
                    }"
                >
                    {{ offre.status === 'OUVERTE' ? 'Ouverte' : offre.status === 'FERMEE' ? 'Fermée' : offre.status }}
                </span>
            </div>

            <div class="grid grid-cols-1 sm:grid-cols-3 gap-3 mb-4">
                <div class="flex flex-col">
                    <span class="font-semibold text-[#9daaf2] text-sm mb-1">Secteur</span>
                    <span class="text-white text-base">{{ offre.secteur }}</span>
                </div>
                <div class="flex flex-col">
                    <span class="font-semibold text-[#9daaf2] text-sm mb-1">Contrat</span>
                    <span class="text-white text-base">{{ offre.contrat }}</span>
                </div>
                <div class="flex flex-col">
                    <span class="font-semibold text-[#9daaf2] text-sm mb-1">Clôture</span>
                    <span class="text-white text-base">{{ offre.closingDate | date: 'dd/MM/yyyy' }}</span>
                </div>
            </div>

            <div *ngIf="offre.hardSkills?.length" class="mb-6">
                <span class="font-semibold text-[#9daaf2] text-sm block mb-2">Compétences requises</span>
                <div class="flex flex-wrap gap-2">
                    <ng-container *ngFor="let skill of offre.hardSkills | slice:0:4">
                        <span class="inline-flex items-center gap-2 bg-[#232b47] px-3 py-1 rounded-full text-sm font-medium">
                            <span>{{ skill.title }}</span>
                            <span
                                *ngIf="skill.level"
                                [ngClass]="{
                                    'bg-green-600 text-white': skill.level === 'Avancé',
                                    'bg-yellow-500 text-white': skill.level === 'Intermédiaire',
                                    'bg-blue-500 text-white': skill.level === 'Débutant'
                                }"
                                class="px-2 py-0.5 rounded-full text-xs font-bold"
                            >
                                {{ skill.level }}
                            </span>
                        </span>
                    </ng-container>
                    <span 
                        *ngIf="(offre.hardSkills?.length ?? 0) > 4" 
                        class="inline-flex items-center bg-[#232b47] px-3 py-1 rounded-full text-sm font-medium text-[#9daaf2]"
                    >
                        +{{ (offre.hardSkills?.length ?? 0) - 4 }} autres
                    </span>
                </div>
            </div>

            <div class="h-px bg-gradient-to-r from-transparent via-[#9daaf2] to-transparent mb-4"></div>

            <div class="text-base text-gray-300 leading-relaxed mb-6 line-clamp-3">
                {{ offre.description | slice: 0 : 160 }}...
            </div>

           
            <div class="flex justify-end mt-auto">
                <button
                    class="bg-gradient-to-r from-[#2229A8] to-[#4764F5] hover:from-[#4764F5] hover:to-[#2229A8]
                              text-white py-2.5 px-6 rounded-full font-semibold text-base
                              shadow-lg shadow-blue-500/25 hover:shadow-blue-500/40
                              transform transition-all duration-300 group-hover:scale-105 border-0"
                >
                    <span class="flex items-center space-x-2">
                        <span>Voir les détails</span>
                        <svg class="w-4 h-4 transform group-hover:translate-x-1 transition-transform duration-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7l5 5m0 0l-5 5m5-5H6"></path>
                        </svg>
                    </span>
                </button>
            </div>

            <div
                class="absolute inset-0 bg-gradient-to-r from-transparent via-white/5 to-transparent 
                        transform -skew-x-12 -translate-x-full group-hover:translate-x-full 
                        transition-transform duration-1000 ease-out pointer-events-none rounded-xl"
            ></div>
        </div>
    `,
    styles: [
        `
            .line-clamp-3 {
                display: -webkit-box;
                -webkit-line-clamp: 3;
                -webkit-box-orient: vertical;
                overflow: hidden;
            }
            h3 {
                font-size: 1.5rem;
                line-height: 1.2;
            }
        `
    ]
})
export class OfferCardComponent {
    @Input() offre!: {
        id: number;
        title: string;
        description: string;
        contrat: string;
        secteur: string;
        hardSkills?: { id: number; title: string; level?: string }[];
        status: string;
        closingDate: string;
        taskMissions?: { id: number; title: string }[];
        createdAt?: string;
        updatedAt?: string;
    };

    @Input() index!: number;

    constructor(private router: Router) {}

    goToDetail() {
        this.router.navigate(['/offre', this.offre.id]);
    }
}