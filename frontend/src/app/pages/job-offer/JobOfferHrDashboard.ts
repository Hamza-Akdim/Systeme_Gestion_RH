import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { OfferCardHrComponent } from './components/job-offer-card/OfferCardHrComponent';
import { Router } from '@angular/router';

@Component({
    selector: 'app-job-offer-hr-dashboard',
    standalone: true,
    imports: [CommonModule, OfferCardHrComponent],
    template: `
        <div class="space-y-4 py-5">
            <!-- <button class="flex items-center mt-3 gap-2 px-2 py-2 bg-gradient-to-r from-[#2229A8] to-[#4764F5] text-white rounded-lg shadow-md font-semibold hover:scale-105 hover:shadow-lg transition" (click)="createOffer()">
                <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                </svg>
                New Offer
            </button> -->

            <div *ngIf="offers.length; else noOffers" class="flex flex-col gap-5 py-4">
                <offer-card-hr *ngFor="let offer of offers" [offer]="offer" (edit)="editOffer($event)" (delete)="deleteOffer($event)" (publish)="publishOffer($event)"> </offer-card-hr>
            </div>

            <ng-template #noOffers>
                <p class="text-gray-500">No job offers yet.</p>
            </ng-template>
        </div>
    `
})
export class JobOfferHrDashboard {
    offers = [
        {
            id: 1,
            title: 'Full Stack Developer (Angular/Node.js)',
            description: `We are looking for a passionate full stack developer capable of working on high-impact projects. You will be involved in designing, developing, and deploying modern web applications.`,
            contrat: 'Permanent',
            secteur: 'Software Development',
            status: 'OPEN',
            closingDate: '2025-09-15'
        },
        {
            id: 2,
            title: 'Digital Marketing Manager',
            description: `The Digital Marketing Manager will lead SEO/SEA campaigns, manage social media channels, and drive customer acquisition strategies. A solid understanding of marketing KPIs is essential.`,
            contrat: 'Fixed-term',
            secteur: 'Marketing',
            status: 'DRAFT',
            closingDate: '2025-10-01'
        },
        {
            id: 3,
            title: 'Cloud DevOps Engineer',
            description: `You will be responsible for automating CI/CD pipelines, monitoring cloud infrastructures (AWS/GCP), and optimizing costs.`,
            contrat: 'Permanent',
            secteur: 'Infrastructure',
            status: 'OPEN',
            closingDate: '2025-09-30'
        },
        {
            id: 4,
            title: 'IT Recruiter',
            description: `You will handle the full recruitment cycle: sourcing, interviews, onboarding, and candidate follow-up. Experience in recruiting technical profiles is highly appreciated.`,
            contrat: 'Internship',
            secteur: 'Human Resources',
            status: 'CLOSED',
            closingDate: '2025-08-01'
        },
        {
            id: 5,
            title: 'UX/UI Designer',
            description: `You will work on optimizing user journeys and designing modern, user-friendly interfaces. A strong sense of mobile-first design is a plus.`,
            contrat: 'Freelance',
            secteur: 'Design',
            status: 'OPEN',
            closingDate: '2025-09-20'
        }
    ];

    constructor(private router: Router){};


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
}
