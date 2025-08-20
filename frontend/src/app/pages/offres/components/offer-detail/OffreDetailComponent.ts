import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'offre-detail',
    standalone: true,
    imports: [CommonModule],
    template: `
        <div class="bg-[#1a2238] text-xl text-white rounded-2xl p-8 shadow-lg border border-[#28304a]  mt-8 space-y-8">
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
        this.offre = this.offre = {
            title: 'DevOps Engineer',
            description:
                "En tant qu'Ingénieur DevOps, vous serez responsable de la conception, de la mise en œuvre et de l’optimisation des processus d’automatisation permettant des déploiements rapides, fiables et reproductibles. Vous jouerez un rôle central dans la gestion et l’évolution de notre infrastructure cloud afin de garantir la disponibilité, la performance et la sécurité de nos services. Votre mission impliquera une collaboration étroite avec les équipes de développement, QA, et opérations pour intégrer les bonnes pratiques DevOps tout au long du cycle de vie logiciel, de la phase de développement à la mise en production. Vous serez amené à mettre en place et surveiller des systèmes de monitoring avancés, gérer les incidents, améliorer la résilience des architectures, et optimiser les coûts d’hébergement. Vous participerez activement à l’évaluation et à l’intégration de nouvelles technologies afin de maintenir un environnement technique à la pointe et adapté aux besoins business.",
            contrat: 'CDI',
            secteur: 'SOFTWARE_DEVELOPMENT',
            hardSkills: [
                { title: 'Docker', level: 'Intermédiaire', description: 'Création et gestion d’images, optimisation des containers pour la production.' },
                { title: 'Kubernetes', level: 'Avancé', description: 'Gestion de clusters, configuration des services et déploiements, scaling automatique et gestion des ressources.' },
                { title: 'CI/CD', level: 'Avancé', description: 'Mise en place de pipelines automatisés avec Jenkins, GitLab CI ou GitHub Actions, gestion des tests et déploiements continus.' },
                { title: 'Infrastructure as Code (IaC)', level: 'Intermédiaire', description: 'Utilisation de Terraform ou Ansible pour automatiser la configuration et le provisionnement des ressources cloud.' },
                { title: 'Cloud Platforms', level: 'Intermédiaire', description: 'Expérience sur AWS, Azure ou GCP incluant la configuration réseau, la sécurité, et les services managés.' },
                { title: 'Monitoring & Logging', level: 'Avancé', description: 'Mise en place et optimisation d’outils tels que Prometheus, Grafana, ELK Stack pour la supervision et l’analyse.' },
                { title: 'Security Best Practices', level: 'Intermédiaire', description: 'Application de mesures de sécurité dans les environnements cloud et pipeline CI/CD.' }
            ],
            status: 'OPEN',
            closingDate: '2025-09-15',
            taskMissions: [
                { title: 'Concevoir, mettre en place et maintenir les pipelines d’intégration et de déploiement continus.' },
                { title: 'Superviser et optimiser les environnements de production et de pré-production.' },
                { title: 'Assurer la haute disponibilité et la tolérance aux pannes des systèmes critiques.' },
                { title: 'Mettre en œuvre des stratégies de monitoring et d’alerting proactives pour anticiper et résoudre les incidents.' },
                { title: 'Collaborer avec les développeurs pour intégrer les meilleures pratiques DevOps dès la phase de conception.' },
                { title: 'Optimiser l’infrastructure cloud pour la performance, la sécurité et la maîtrise des coûts.' },
                { title: 'Participer à l’automatisation des processus manuels pour améliorer l’efficacité opérationnelle.' }
            ]
        };
    }
}
