import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
    selector: 'offer-card-hr',
    standalone: true,
    imports: [CommonModule],
    template: `
        <div
            class="group bg-[#1a2238] text-white rounded-2xl p-6 relative shadow-lg border border-[#28304a]
             transition-all duration-300 hover:shadow-2xl hover:scale-[1.02] hover:border-[#9daaf2]
             flex flex-col h-full overflow-hidden backdrop-blur-sm"
        >
            <!-- HEADER -->
            <div class="flex justify-between items-start mb-5 relative z-10">
                <h3 class="text-xl font-bold text-white group-hover:text-[#9daaf2] transition-colors duration-300 leading-tight pr-4 flex-1">
                    {{ offer.title }}
                </h3>
                <span
                    class="px-3 py-1.5 rounded-full text-xs font-semibold shadow-md flex-shrink-0
                 transition-all duration-300 group-hover:shadow-lg"
                    [ngClass]="{
                        'bg-green-200 text-green-800 group-hover:bg-green-300': offer.status === 'OPEN',
                        'bg-red-200 text-red-800 group-hover:bg-red-300': offer.status === 'CLOSED',
                        'bg-yellow-200 text-yellow-800 group-hover:bg-yellow-300': offer.status === 'DRAFT'
                    }"
                >
                    {{ offer.status }}
                </span>
            </div>

            <!-- INFO GRID -->
            <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mb-5">
                <div class="info-box">
                    <span class="info-label">Sector</span>
                    <span class="info-value">{{ offer.secteur }}</span>
                </div>
                <div class="info-box">
                    <span class="info-label">Contract</span>

                    <span class="info-value">{{ offer.contrat }}</span>
                </div>
                <div class="info-box">
                    <span class="info-label">Closing Date</span>
                    <span class="info-value">{{ offer.closingDate | date: 'dd/MM/yyyy' }}</span>
                </div>
            </div>

            <!-- DESCRIPTION -->
            <div class="relative mb-5">
                <div class="h-px bg-gradient-to-r from-transparent via-[#9daaf2]/60 to-transparent"></div>
            </div>
            <div class="flex-1 mb-6">
                <p class="text-sm text-gray-300 leading-relaxed line-clamp-3 group-hover:text-gray-200 transition-colors duration-300">{{ offer.description | slice: 0 : 160 }}...</p>
            </div>

            <!-- HR ACTIONS -->
            <div class="flex justify-between items-center mt-auto gap-3">
                <button (click)="goToDetail()" class=" flex items-center gap-2 border-2 border-[#0d6efd] text-[#0d6efd] font-semibold p-2 rounded-full shadow-md hover:bg-[#0d6efd] hover:text-white">
                    <!-- Eye icon -->
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.477 0 8.268 2.943 9.542 7-1.274 4.057-5.065 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                    </svg>
                    View Details
                </button>

                <div class="flex gap-2">
                    <button (click)="editOffer.emit(offer)" class="btn-secondary flex items-center gap-2">
                        <!-- Pencil icon -->
                        <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5h2M12 4v16m7-7H5" />
                        </svg>
                        Edit
                    </button>

                    <button (click)="deleteOffer.emit(offer)" class="btn-danger flex items-center gap-2">
                        <!-- Trash icon -->
                        <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3m4 0H6" />
                        </svg>
                        Delete
                    </button>

                    <button *ngIf="offer.status === 'DRAFT'" (click)="publishOffer.emit(offer)" class="btn-success flex items-center gap-2">
                        <!-- Rocket icon -->
                        <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                stroke-width="2"
                                d="M5 19a1 1 0 001.707.707l2.122-2.121a7.975 7.975 0 01-1.415-1.415L5.293 18.293A1 1 0 005 19zM19 5a1 1 0 00-1.707-.707l-2.122 2.121a7.975 7.975 0 011.415 1.415L18.707 6.707A1 1 0 0019 5zM11 13l9-9M13 11l-9 9"
                            />
                        </svg>
                        Publish
                    </button>

                    <button *ngIf="offer.status === 'OPEN'" (click)="closeOffer.emit(offer)" class="btn-warning flex items-center gap-2">
                        <!-- Lock icon -->
                        <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6-6V9a6 6 0 1112 0v2m-6 4h.01" />
                        </svg>
                        Close
                    </button>
                </div>
            </div>
        </div>
    `,

    styles: [
        `
            .info-box {
                @apply flex flex-col bg-[#232b47]/50 rounded-lg p-3 transition-all duration-300 group-hover:bg-[#232b47]/70;
            }
            .info-label {
                @apply font-semibold text-[#9daaf2] text-xs mb-1 uppercase tracking-wide;
            }
            .info-value {
                @apply text-white text-sm font-medium truncate;
            }
            .btn-base {
                @apply flex items-center gap-2 px-3 py-1.5 rounded-md text-sm font-medium
             transition-colors duration-200 border border-transparent;
            }

            .btn-secondary {
                @apply btn-base bg-[#2d3652] text-gray-200 hover:bg-[#3a4468] hover:text-white;
            }

            .btn-danger {
                @apply btn-base bg-[#2d3652] text-red-400 hover:bg-red-500 hover:text-white;
            }

            .btn-success {
                @apply btn-base bg-[#2d3652] text-emerald-400 hover:bg-emerald-500 hover:text-white;
            }

            .btn-warning {
                @apply btn-base bg-[#2d3652] text-amber-400 hover:bg-amber-500 hover:text-white;
            }
        `
    ]
})
export class OfferCardHrComponent {
    @Input() offer!: {
        id: number;
        title: string;
        description: string;
        contrat: string;
        secteur: string;
        status: string;
        closingDate: string;
    };

    @Output() editOffer = new EventEmitter<any>();
    @Output() deleteOffer = new EventEmitter<any>();
    @Output() publishOffer = new EventEmitter<any>();
    @Output() closeOffer = new EventEmitter<any>();

    constructor(private router: Router) {}

    goToDetail() {
        this.router.navigate(['/offer-details', this.offer.id]);
    }
}
