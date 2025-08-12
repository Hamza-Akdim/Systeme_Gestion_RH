import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'offre-detail',
    standalone: true,
    imports: [CommonModule],
    template: `
        <div class="bg-[#1a2238] text-white rounded-2xl p-8 shadow-lg border border-[#28304a]  mt-8 space-y-8">
            <div class="flex items-center justify-between gap-2">
                <h2 class="text-3xl font-bold text-[#9daaf2]">{{ offre?.title }}</h2>
                <span
                    class="px-3 py-1.5 rounded-full text-xs font-semibold shadow-md flex-shrink-0
           transition-all duration-300"
                    [ngClass]="{
                        'bg-green-200 text-green-800': offre?.status === 'OUVERTE' || offre?.status === 'OPEN',
                        'bg-red-200 text-red-800': offre?.status === 'FERMEE' || offre?.status === 'CLOSED',
                        'bg-yellow-200 text-yellow-800': offre?.status === 'URGENT'
                    }"
                >
                    {{ offre?.status === 'OUVERTE' || offre?.status === 'OPEN' ? 'Ouverte' : offre?.status === 'FERMEE' || offre?.status === 'CLOSED' ? 'Fermée' : offre?.status === 'URGENT' ? 'Urgente' : offre?.status }}
                </span>
            </div>

            <div class="grid grid-cols-1 sm:grid-cols-3 gap-6">
                <div>
                    <span class="font-semibold text-[#9daaf2] text-sm">Secteur</span>
                    <div class="text-white text-base">{{ offre?.secteur }}</div>
                </div>
                <div>
                    <span class="font-semibold text-[#9daaf2] text-sm">Contrat</span>
                    <div class="text-white text-base">{{ offre?.contrat }}</div>
                </div>
                <div>
                    <span class="font-semibold text-[#9daaf2] text-sm">Clôture</span>
                    <div class="text-white text-base">{{ offre?.closingDate | date: 'dd/MM/yyyy' }}</div>
                </div>
            </div>

            <div *ngIf="offre?.hardSkills?.length">
                <span class="font-semibold text-[#9daaf2] text-sm block mb-2">Compétences requises</span>
                <div class="flex flex-wrap gap-2">
                    <ng-container *ngFor="let skill of offre.hardSkills">
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
                </div>
            </div>

            <div *ngIf="offre?.taskMissions?.length">
                <span class="font-semibold text-[#9daaf2] text-sm block mb-2">Missions principales</span>
                <ul class="list-disc list-inside text-gray-200 space-y-1">
                    <li *ngFor="let mission of offre.taskMissions">{{ mission.title }}</li>
                </ul>
            </div>

            <div>
                <span class="font-semibold text-[#9daaf2] text-sm block mb-2">Description</span>
                <p class="text-gray-200 leading-relaxed">{{ offre?.description }}</p>
            </div>

            <div class="flex justify-end">
                <button
                    class="bg-gradient-to-r from-[#2229A8] to-[#4764F5] hover:from-[#4764F5] hover:to-[#2229A8]
                        text-white px-8 py-3 rounded-full font-semibold shadow-lg shadow-blue-500/25 hover:shadow-blue-500/40
                        transition-all duration-300"
                >
                    Postuler
                </button>
            </div>
        </div>
    `
})
export class OffreDetailComponent {
    offre: any;

    constructor(private route: ActivatedRoute) {
        this.offre = {
            title: 'DevOps Engineer',
            description:
                "Responsable de l'automatisation des déploiements, de la surveillance des systèmes, et de la garantie de la disponibilité des services cloud. Vous collaborerez étroitement avec les équipes de développement pour intégrer les pratiques DevOps tout au long du cycle de vie logiciel.",
            contrat: 'CDI',
            secteur: 'SOFTWARE_DEVELOPMENT',
            hardSkills: [
                { title: 'Docker', level: 'Intermédiaire' },
                { title: 'Kubernetes', level: 'Avancé' },
                { title: 'CI/CD', level: 'Avancé' }
            ],
            status: 'OPEN',
            closingDate: '2025-09-15',
            taskMissions: [{ title: 'Mettre en place et maintenir les pipelines d’intégration continue' }, { title: 'Superviser les environnements de production' }, { title: "Optimiser l'infrastructure cloud pour les performances et la fiabilité" }]
        };
    }
}
