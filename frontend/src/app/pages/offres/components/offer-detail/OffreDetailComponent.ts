import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { JobOfferService } from '../../offer.service';

@Component({
    selector: 'offre-detail',
    standalone: true,
    imports: [CommonModule],
    template: `
        <div class="bg-[#1a2238] text-xl text-white rounded-2xl p-8 shadow-lg border border-[#28304a]  mt-8 space-y-8">
            <div class="flex items-center justify-between gap-2">
                <h2 class="text-3xl font-mono font-bold text-[#9daaf2]">{{ offre?.title }}</h2>
                <span
                    class="px-3 py-1.5 rounded-full text-xs font-semibold shadow-md flex-shrink-0
           transition-all duration-300"
                    [ngClass]="{
                        'bg-green-200 text-green-800': offre?.status === 'OPEN',
                        'bg-red-200 text-red-800': offre?.status === 'CLOSED',
                        'bg-yellow-200 text-yellow-800': offre?.status === 'URGENT'
                    }"
                >
                    {{ offre.status }}
                    <!-- {{ offre?.status === 'OPEN' ? 'OPEN' : offre?.status === 'CLOSED' ? 'CLOSED' : offre?.status === 'URGENT' ? 'URGENT' : offre?.status }} -->
                </span>
            </div>

            <div class="grid grid-cols-1 sm:grid-cols-3 gap-6">
                <div>
                    <span class="font-semibold text-[#9daaf2] font-mono text-lg">Sector</span>
                    <div class="text-white font-mono text-base">{{ offre?.secteur }}</div>
                </div>
                <div>
                    <span class="font-semibold text-[#9daaf2] font-mono text-lg">Contract</span>
                    <div class="text-white font-mono text-base">{{ offre?.contrat }}</div>
                </div>
                <div>
                    <span class="font-semibold text-[#9daaf2] font-mono text-lg">Closing Date</span>
                    <div class="text-white font-mono text-base">{{ offre?.closingDate | date: 'dd/MM/yyyy' }}</div>
                </div>
            </div>

            <div *ngIf="offre?.hardSkills?.length">
                <span class="font-semibold text-[#9daaf2] font-mono text-lg block mb-2">Required skills</span>
                <div class="flex flex-wrap gap-2">
                    <ng-container *ngFor="let skill of offre.hardSkills">
                        <span class="inline-flex items-center gap-2 bg-[#232b47] px-3 py-1 rounded-full text-sm font-medium">
                            <span>{{ skill.title }}</span>
                            <span *ngIf="skill.level">- {{ skill.level }}</span>
                        </span>
                    </ng-container>
                </div>
            </div>

            <div>
                <span class="font-semibold font-mono text-[#9daaf2] text-lg block mb-2">Description</span>
                <p class="text-gray-200 font-mono text-base leading-relaxed">{{ offre?.description }}</p>
            </div>

            <div *ngIf="offre?.taskMissions?.length">
                <span class="font-semibold text-[#9daaf2] font-mono text-lg block mb-2">Main missions</span>
                <ul class="list-disc list-inside font-mono text-base text-gray-200 space-y-1">
                    <li *ngFor="let mission of offre.taskMissions">{{ mission.title }}</li>
                </ul>
            </div>

            <div class="flex justify-end">
                <button
                    (click)="Apply()"
                    class="bg-gradient-to-r from-[#2229A8] to-[#4764F5] hover:from-[#4764F5] hover:to-[#2229A8]
                        text-white font-mono text-base px-6 py-2 rounded-full font-semibold shadow-lg shadow-blue-500/25 hover:shadow-blue-500/40
                        transition-all duration-300"
                    [disabled]="offre.status === 'CLOSED'"
                >
                    Apply
                </button>
            </div>
        </div>
    `
})
export class OffreDetailComponent {
    offre: any;

    constructor(
        private route: ActivatedRoute,
        private offerService: JobOfferService,
        private router: Router
    ) {}

    ngOnInit(): void {
        const id = Number(this.route.snapshot.paramMap.get('id'));
        if (id) {
            this.offerService.getOfferById(id).subscribe({
                next: (data) => (this.offre = data),
                error: (err) => console.error('Error fetching offer:', err)
            });
        }
    }

    Apply() {
        this.router.navigate(['job-offer/apply', this.offre?.id]);
    }
}
