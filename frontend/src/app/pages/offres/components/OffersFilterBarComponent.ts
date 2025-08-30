import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'offres-filter-bar',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
        <div class="bg-gradient-to-br from-slate-800 to-slate-900 text-white rounded-xl shadow-2xl border border-slate-700/50 backdrop-blur-sm">
            <div class="px-6 py-4 border-b border-slate-700/50">
                <div class="flex items-center justify-between">
                    <h2 class="text-lg font-semibold text-white flex items-center gap-2">
                        <svg class="w-5 h-5 text-blue-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                stroke-width="2"
                                d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2.586a1 1 0 01-.293.707l-6.414 6.414a1 1 0 00-.293.707V17l-4 4v-6.586a1 1 0 00-.293-.707L3.293 7.207A1 1 0 013 6.5V4z"
                            ></path>
                        </svg>
                        Filter offers
                    </h2>
                    <button
                        (click)="resetFilters()"
                        class="text-slate-400 hover:text-red-400 flex items-center gap-2 text-sm font-medium
                               transition-colors duration-300 cursor-pointer px-3 py-1.5 rounded-lg hover:bg-slate-700/30"
                        [class.opacity-50]="!hasActiveFilters()"
                        [disabled]="!hasActiveFilters()"
                    >
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
                        </svg>
                        Reset
                    </button>
                </div>
            </div>

            <div class="p-6 space-y-6">
                <div class="relative">
                    <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
                        <svg class="h-5 w-5 text-slate-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                        </svg>
                    </div>
                    <input
                        [(ngModel)]="keyword"
                        (keyup.enter)="applyFilters()"
                        placeholder="Search by title, description, sector, contract, skills..."
                        class="w-full bg-slate-700/50 border border-slate-600/50 rounded-lg pl-12 pr-4 py-3.5
                               text-white placeholder-slate-400 outline-none
                               focus:border-blue-400/50 focus:ring-2 focus:ring-blue-400/20 focus:bg-slate-700/70
                               transition-all duration-300"
                    />
                    <div class="absolute inset-y-0 right-0 pr-2 flex items-center">
                        <button
                            (click)="applyFilters()"
                            class="bg-gradient-to-r from-[#2229A8] to-[#4764F5] hover:from-[#4764F5] hover:to-[#2229A8]
                                   text-white px-6 py-2 rounded-lg font-medium text-sm
                                   shadow-lg shadow-blue-500/25 hover:shadow-blue-500/40
                                   transform transition-all duration-300 hover:scale-105
                                   border border-[#2229A8] hover:border-blue-[#2229A8]"
                        >
                            Search
                        </button>
                    </div>
                </div>

                <div class="space-y-4">
                    <h3 class="text-sm font-medium text-slate-300 uppercase tracking-wide">Advanced filters</h3>

                    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                        <div class="space-y-2">
                            <label class="block text-xs font-medium text-slate-400 uppercase tracking-wide"> Type of contract </label>
                            <div class="relative">
                                <select
                                    [(ngModel)]="contrat"
                                    (change)="applyFilters()"
                                    class="w-full bg-slate-700/50 border border-slate-600/50 rounded-lg px-4 py-3
                                           text-white appearance-none cursor-pointer outline-none
                                           focus:border-blue-400/50 focus:ring-2 focus:ring-blue-400/20 focus:bg-slate-700/70
                                           transition-all duration-300"
                                >
                                    <option value="">All contracts</option>
                                    <option value="CDI">CDI</option>
                                    <option value="CDD">CDD</option>
                                    <option value="INTERN">Internship</option>
                                    <option value="FREELANCE">Freelance</option>
                                </select>
                                <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
                                    <svg class="h-4 w-4 text-slate-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path>
                                    </svg>
                                </div>
                                <div *ngIf="contrat" class="absolute top-2 right-8 w-2 h-2 bg-blue-400 rounded-full"></div>
                            </div>
                        </div>

                        <div class="space-y-2">
                            <label class="block text-xs font-medium text-slate-400 uppercase tracking-wide"> Sector </label>
                            <div class="relative">
                                <select
                                    [(ngModel)]="secteur"
                                    (change)="applyFilters()"
                                    class="w-full bg-slate-700/50 border border-slate-600/50 rounded-lg px-4 py-3
                                           text-white appearance-none cursor-pointer outline-none
                                           focus:border-blue-400/50 focus:ring-2 focus:ring-blue-400/20 focus:bg-slate-700/70
                                           transition-all duration-300"
                                >
                                    <option value="">All sectors</option>
                                    <option value="SOFTWARE_DEVELOPMENT">SOFTWARE_DEVELOPMENT</option>
                                    <option value="IT_MANAGEMENT">IT_MANAGEMENT</option>
                                    <option value="DATA_SOLUTIONS">DATA_SOLUTIONS</option>
                                    <option value="POWER_GENERATION">POWER_GENERATION</option>
                                    <option value="TELECOMMUNICATIONS">TELECOMMUNICATIONS</option>
                                    <option value="IT_SUPPORT">IT_SUPPORT</option>
                                    <option value="ENERGY_AUDITS">ENERGY_AUDITS</option>
                                    <option value="STAFFING">STAFFING</option>
                                    <option value="POWER_SYSTEMS">POWER_SYSTEMS</option>
                                    <option value="ENERGY_MANAGEMENT">ENERGY_MANAGEMENT</option>
                                </select>
                                <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
                                    <svg class="h-4 w-4 text-slate-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path>
                                    </svg>
                                </div>
                                <div *ngIf="secteur" class="absolute top-2 right-8 w-2 h-2 bg-green-400 rounded-full"></div>
                            </div>
                        </div>

                        <div class="space-y-2">
                            <label class="block text-xs font-medium text-slate-400 uppercase tracking-wide"> Offer status </label>
                            <div class="relative">
                                <select
                                    [(ngModel)]="status"
                                    (change)="applyFilters()"
                                    class="w-full bg-slate-700/50 border border-slate-600/50 rounded-lg px-4 py-3
                                           text-white appearance-none cursor-pointer outline-none
                                           focus:border-blue-400/50 focus:ring-2 focus:ring-blue-400/20 focus:bg-slate-700/70
                                           transition-all duration-300"
                                >
                                    <option value="">All statuses</option>
                                    <option value="OPEN">Open</option>
                                    <option value="FERMEE">Closed</option>
                                    <option value="ON_HOLD">On Hold</option>
                                </select>
                                <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
                                    <svg class="h-4 w-4 text-slate-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path>
                                    </svg>
                                </div>
                                <div *ngIf="status" class="absolute top-2 right-8 w-2 h-2 bg-cyan-400 rounded-full"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div *ngIf="hasActiveFilters()" class="space-y-2">
                    <h4 class="text-xs font-medium text-slate-400 uppercase tracking-wide">Active filters</h4>
                    <div class="flex flex-wrap gap-2">
                        <span
                            *ngIf="keyword"
                            class="inline-flex items-center gap-1 px-3 py-1 bg-blue-500/20 text-blue-300
                                     rounded-full text-xs font-medium border border-blue-500/30"
                        >
                            "{{ keyword }}"
                            <button (click)="clearKeyword()" class="hover:text-blue-100 transition-colors">
                                <svg class="w-3 h-3" fill="currentColor" viewBox="0 0 20 20">
                                    <path
                                        fill-rule="evenodd"
                                        d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                                        clip-rule="evenodd"
                                    ></path>
                                </svg>
                            </button>
                        </span>
                        <span
                            *ngIf="contrat"
                            class="inline-flex items-center gap-1 px-3 py-1 bg-purple-500/20 text-purple-300
                                     rounded-full text-xs font-medium border border-purple-500/30"
                        >
                            {{ contrat }}
                            <button (click)="clearContrat()" class="hover:text-purple-100 transition-colors">
                                <svg class="w-3 h-3" fill="currentColor" viewBox="0 0 20 20">
                                    <path
                                        fill-rule="evenodd"
                                        d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                                        clip-rule="evenodd"
                                    ></path>
                                </svg>
                            </button>
                        </span>
                        <span
                            *ngIf="secteur"
                            class="inline-flex items-center gap-1 px-3 py-1 bg-green-500/20 text-green-300
                                     rounded-full text-xs font-medium border border-green-500/30"
                        >
                            {{ secteur }}
                            <button (click)="clearSecteur()" class="hover:text-green-100 transition-colors">
                                <svg class="w-3 h-3" fill="currentColor" viewBox="0 0 20 20">
                                    <path
                                        fill-rule="evenodd"
                                        d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                                        clip-rule="evenodd"
                                    ></path>
                                </svg>
                            </button>
                        </span>
                        <span
                            *ngIf="status"
                            class="inline-flex items-center gap-1 px-3 py-1 bg-amber-500/20 text-amber-300
                                     rounded-full text-xs font-medium border border-amber-500/30"
                        >
                            {{ status }}
                            <button (click)="clearStatus()" class="hover:text-amber-100 transition-colors">
                                <svg class="w-3 h-3" fill="currentColor" viewBox="0 0 20 20">
                                    <path
                                        fill-rule="evenodd"
                                        d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                                        clip-rule="evenodd"
                                    ></path>
                                </svg>
                            </button>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    `
})
export class OffresFilterBarComponent {
    keyword = '';
    contrat = '';
    secteur = '';
    status = '';

    @Output() filtersChanged = new EventEmitter<any>();

    applyFilters() {
        this.filtersChanged.emit({
            keyword: this.keyword.toLowerCase().trim(),
            contrat: this.contrat,
            secteur: this.secteur,
            status: this.status
        });
    }

    resetFilters() {
        this.keyword = '';
        this.contrat = '';
        this.secteur = '';
        this.status = '';
        this.applyFilters();
    }

    hasActiveFilters(): boolean {
        return !!(this.keyword || this.contrat || this.secteur || this.status);
    }

    clearKeyword() {
        this.keyword = '';
        this.applyFilters();
    }

    clearContrat() {
        this.contrat = '';
        this.applyFilters();
    }

    clearSecteur() {
        this.secteur = '';
        this.applyFilters();
    }

    clearStatus() {
        this.status = '';
        this.applyFilters();
    }
}
