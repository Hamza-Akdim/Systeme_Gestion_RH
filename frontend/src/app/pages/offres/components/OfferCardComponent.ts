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
            class="group cursor-pointer bg-[#1a2238] text-white rounded-2xl p-6 relative shadow-lg border border-[#28304a]
                   transition-all duration-300 hover:shadow-2xl hover:scale-[1.02] hover:border-[#9daaf2]
                   flex flex-col h-full overflow-hidden backdrop-blur-sm"
        >
            <div class="flex justify-between items-start mb-5 relative z-10">
                <h3 class="text-xl font-bold text-white group-hover:text-[#9daaf2] transition-colors duration-300
                          leading-tight pr-4 flex-1 line-height-tight">
                    {{ offre.title }}
                </h3>
                <span
                    class="px-3 py-1.5 rounded-full text-xs font-semibold shadow-md flex-shrink-0
                           transition-all duration-300 group-hover:shadow-lg"
                    [ngClass]="{
                        'bg-green-200 text-green-800 group-hover:bg-green-300': offre.status === 'OUVERTE',
                        'bg-red-200 text-red-800 group-hover:bg-red-300': offre.status !== 'OUVERTE'
                    }"
                >
                    {{ offre.status === 'OUVERTE' ? 'Ouverte' : offre.status === 'FERMEE' ? 'Fermée' : offre.status }}
                </span>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mb-5">
                <div class="flex items-center gap-3 bg-[#232b47]/50 rounded-lg p-3 transition-all duration-300 group-hover:bg-[#232b47]/70">
                    <div class="w-8 h-8 rounded-full bg-[#9daaf2]/20 flex items-center justify-center flex-shrink-0">
                        <svg class="w-4 h-4 text-[#9daaf2]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h1a1 1 0 011 1v5m-4 0h4"></path>
                        </svg>
                    </div>
                    <div class="flex flex-col min-w-0 flex-1">
                        <span class="font-semibold text-[#9daaf2] text-xs mb-1 uppercase tracking-wide">Secteur</span>
                        <span class="text-white text-sm font-medium truncate">{{ offre.secteur }}</span>
                    </div>
                </div>

                <div class="flex items-center gap-3 bg-[#232b47]/50 rounded-lg p-3 transition-all duration-300 group-hover:bg-[#232b47]/70">
                    <div class="w-8 h-8 rounded-full bg-[#9daaf2]/20 flex items-center justify-center flex-shrink-0">
                        <svg class="w-4 h-4 text-[#9daaf2]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
                        </svg>
                    </div>
                    <div class="flex flex-col min-w-0 flex-1">
                        <span class="font-semibold text-[#9daaf2] text-xs mb-1 uppercase tracking-wide">Contrat</span>
                        <span class="text-white text-sm font-medium truncate">{{ offre.contrat }}</span>
                    </div>
                </div>

                <div class="flex items-center gap-3 bg-[#232b47]/50 rounded-lg p-3 transition-all duration-300 group-hover:bg-[#232b47]/70">
                    <div class="w-8 h-8 rounded-full bg-[#9daaf2]/20 flex items-center justify-center flex-shrink-0">
                        <svg class="w-4 h-4 text-[#9daaf2]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                        </svg>
                    </div>
                    <div class="flex flex-col min-w-0 flex-1">
                        <span class="font-semibold text-[#9daaf2] text-xs mb-1 uppercase tracking-wide">Clôture</span>
                        <span class="text-white text-sm font-medium truncate">{{ offre.closingDate | date: 'dd/MM/yyyy' }}</span>
                    </div>
                </div>
            </div>

            <div class="relative mb-5">
                <div class="h-px bg-gradient-to-r from-transparent via-[#9daaf2]/60 to-transparent"></div>
                <div class="absolute inset-0 h-px bg-gradient-to-r from-transparent via-[#9daaf2] to-transparent
                           opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
            </div>

            <div class="flex-1 mb-6">
                <div class="text-sm text-gray-300 leading-relaxed line-clamp-3 group-hover:text-gray-200 transition-colors duration-300">
                    {{ offre.description | slice: 0 : 160 }}...
                </div>
            </div>

            <div class="flex justify-end mt-auto">
                <button
                    class="bg-gradient-to-r from-[#2229A8] to-[#4764F5] hover:from-[#4764F5] hover:to-[#2229A8]
                           text-white py-3 px-8 rounded-full font-semibold text-sm
                           shadow-lg shadow-blue-500/25 hover:shadow-blue-500/40 hover:shadow-xl
                           transform transition-all duration-300 hover:scale-105 border-0
                           relative overflow-hidden group/btn"
                >
                    <span class="flex items-center space-x-2 relative z-10">
                        <span>Voir les détails</span>
                        <svg class="w-4 h-4 transform group-hover:translate-x-1 transition-transform duration-300"
                             fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7l5 5m0 0l-5 5m5-5H6"></path>
                        </svg>
                    </span>
                    <div class="absolute inset-0 bg-gradient-to-r from-transparent via-white/20 to-transparent
                               transform -skew-x-12 -translate-x-full group/btn-hover:translate-x-full
                               transition-transform duration-700 ease-out"></div>
                </button>
            </div>

            <div
                class="absolute inset-0 bg-gradient-to-r from-transparent via-white/5 to-transparent
                       transform -skew-x-12 -translate-x-full group-hover:translate-x-full
                       transition-transform duration-1000 ease-out pointer-events-none rounded-xl"
            ></div>

            <div class="absolute inset-0 rounded-xl bg-gradient-to-r from-[#9daaf2]/0 via-[#9daaf2]/5 to-[#9daaf2]/0
                       opacity-0 group-hover:opacity-100 transition-opacity duration-500 pointer-events-none"></div>
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

            .line-height-tight {
                line-height: 1.2;
            }

            @media (max-width: 768px) {
                .group:hover {
                    transform: scale(1.01) !important;
                }
            }

            .group:focus-within {
                outline: 2px solid #9daaf2;
                outline-offset: 2px;
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
