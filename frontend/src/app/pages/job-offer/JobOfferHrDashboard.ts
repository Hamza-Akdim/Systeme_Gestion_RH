import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { OfferCardHrComponent } from './components/job-offer-card/OfferCardHrComponent';
import { Router } from '@angular/router';
import { OffresFilterBarComponent } from '../offres/components/OffersFilterBarComponent';
import { JobOffer, JobOfferService } from '../offres/offer.service';

@Component({
    selector: 'app-job-offer-hr-dashboard',
    standalone: true,
    imports: [CommonModule, OfferCardHrComponent, OffresFilterBarComponent],
    template: `
        <div class="space-y-4 py-5">
            <!-- <button class="flex items-center mt-3 gap-2 px-2 py-2 bg-gradient-to-r from-[#2229A8] to-[#4764F5] text-white rounded-lg shadow-md font-semibold hover:scale-105 hover:shadow-lg transition" (click)="createOffer()">
                <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                </svg>
                New Offer
            </button> -->
            <offres-filter-bar (filtersChanged)="onFiltersChanged($event)"></offres-filter-bar>

            <div *ngIf="offres.length; else noOffers" class="flex flex-col gap-5 py-4">
                <offer-card-hr *ngFor="let offer of offres" [offer]="offer" (edit)="editOffer($event)" (delete)="deleteOffer($event)" (publish)="publishOffer($event)"> </offer-card-hr>
            </div>

            <ng-template #noOffers>
                <p class="text-gray-500">No job offers yet.</p>
            </ng-template>
        </div>
    `
})
export class JobOfferHrDashboard {
    offres: JobOffer[] = [];

    filteredOffres: JobOffer[] = [];

    loading = false;

    constructor(
        private jobOfferService: JobOfferService,
        private router: Router
    ) {}

    ngOnInit() {
        this.fetchJobOffers();
    }

    fetchJobOffers() {
        this.loading = true;
        this.jobOfferService.getAllJobOffers().subscribe({
            next: (data) => {
                this.offres = data;
                this.filteredOffres = [...this.offres];
                this.loading = false;
            },
            error: (err) => {
                console.error('Error fetching job offers:', err);
                this.loading = false;
            }
        });
    }

    createOffer() {
        this.router.navigate(['/hr/job-offer/createOffer']);
    }
    editOffer(offer: any) {
        /* open form with offer preloaded */
    }
    deleteOffer(offer: any) {
        /* call API */
    }
    publishOffer(offer: any) {
        /* update status to PUBLISHED */
    }

    onFiltersChanged(filters: any) {
        this.filteredOffres = this.offres.filter((offre) => {
            if (filters.keyword) {
                const searchableText = [
                    offre.title,
                    offre.description,
                    offre.secteur,
                    offre.contrat,
                    offre.status,
                    ...(offre.hardSkills?.map((skill) => skill.title) || []),
                    ...(offre.hardSkills?.map((skill) => skill.level) || []),
                    ...(offre.taskMissions?.map((mission) => mission.title) || [])
                ]
                    .join(' ')
                    .toLowerCase();

                if (!searchableText.includes(filters.keyword.toLowerCase())) {
                    return false;
                }
            }
            if (filters.contrat && offre.contrat !== filters.contrat) return false;
            if (filters.secteur && offre.secteur !== filters.secteur) return false;
            if (filters.status && offre.status !== filters.status) return false;

            return true;
        });
    }
}
