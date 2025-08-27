import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OfferCardComponent } from './components/OfferCardComponent';
import { OffresFilterBarComponent } from './components/OffersFilterBarComponent';

@Component({
    selector: 'app-offres-dashboard',
    standalone: true,
    imports: [CommonModule, OfferCardComponent, OffresFilterBarComponent],
    template: `
        <div class="p-6 space-y-6">
            <offres-filter-bar (filtersChanged)="onFiltersChanged($event)"></offres-filter-bar>

            <div class="grid grid-cols-1 2xl:grid-cols-2 gap-6">
                <offer-card *ngFor="let offre of filteredOffres; let i = index" [offre]="offre" [index]="i" />
            </div>
        </div>
    `
})
export class OffresDashboard {
    offres = [
        {
            id: 1,
            title: 'DevOps Engineer',
            description:
                'As a DevOps Engineer, you will be responsible for designing, implementing, and optimizing automation processes to enable fast, reliable, and repeatable deployments. You will play a key role in managing and evolving our cloud infrastructure to ensure the availability, performance, and security of our services. Your mission will involve close collaboration with development, QA, and operations teams to integrate DevOps best practices throughout the software lifecycle, from development to production deployment. You will set up and monitor advanced monitoring systems, manage incidents, improve architecture resilience, and optimize hosting costs. You will actively participate in evaluating and integrating new technologies to maintain a cutting-edge technical environment aligned with business needs.',
            contrat: 'CDI',
            secteur: 'SOFTWARE_DEVELOPMENT',
            hardSkills: [
                { id: 1, title: 'Docker', level: 'Intermediate', description: 'Creation and management of images, optimization of containers for production.' },
                { id: 2, title: 'Kubernetes', level: 'Advanced', description: 'Cluster management, service and deployment configuration, auto-scaling, and resource management.' },
                { id: 3, title: 'CI/CD', level: 'Advanced', description: 'Implementation of automated pipelines with Jenkins, GitLab CI, or GitHub Actions, managing tests and continuous deployments.' },
                { id: 4, title: 'Infrastructure as Code (IaC)', level: 'Intermediate', description: 'Using Terraform or Ansible to automate configuration and provisioning of cloud resources.' },
                { id: 5, title: 'Cloud Platforms', level: 'Intermediate', description: 'Experience with AWS, Azure, or GCP including network configuration, security, and managed services.' },
                { id: 6, title: 'Monitoring & Logging', level: 'Advanced', description: 'Implementation and optimization of tools such as Prometheus, Grafana, and ELK Stack for supervision and analysis.' },
                { id: 7, title: 'Security Best Practices', level: 'Intermediate', description: 'Applying security measures in cloud environments and CI/CD pipelines.' }
            ],
            status: 'Open',
            closingDate: '2025-09-15',
            taskMissions: [
                { id: 1, title: 'Design, implement, and maintain continuous integration and continuous deployment pipelines.' },
                { id: 2, title: 'Supervise and optimize production and pre-production environments.' },
                { id: 3, title: 'Ensure high availability and fault tolerance of critical systems.' },
                { id: 4, title: 'Implement proactive monitoring and alerting strategies to anticipate and resolve incidents.' },
                { id: 5, title: 'Collaborate with developers to integrate DevOps best practices from the design phase.' },
                { id: 6, title: 'Optimize cloud infrastructure for performance, security, and cost control.' },
                { id: 7, title: 'Contribute to the automation of manual processes to improve operational efficiency.' }
            ],
            createdAt: '2025-07-10T09:00:00Z',
            updatedAt: '2025-07-10T09:00:00Z'
        },
        {
            id: 2,
            title: 'Java Developer',
            description: 'What opportunity are we offering? ...',
            contrat: 'Permanent',
            secteur: 'SOFTWARE_DEVELOPMENT',
            hardSkills: [
                { id: 8, title: 'Murex', level: 'Advanced' },
                { id: 9, title: 'Java', level: 'Intermediate' }
            ],
            status: 'Open',
            closingDate: '2025-08-31',
            taskMissions: [{ id: 8, title: 'Development of Murex modules' }],
            createdAt: '2025-07-15T10:00:00Z',
            updatedAt: '2025-07-15T10:00:00Z'
        },
        {
            id: 3,
            title: 'IT Project Manager – Telecommunications',
            description: 'Responsible for managing telecom projects for a major operator.',
            contrat: 'CDI',
            secteur: 'TELECOMMUNICATIONS',
            hardSkills: [
                { id: 10, title: 'Project Management', level: 'Advanced' },
                { id: 11, title: 'Telecom', level: 'Intermediate' }
            ],
            status: 'Open',
            closingDate: '2025-09-15',
            taskMissions: [
                { id: 9, title: 'Project oversight' },
                { id: 10, title: 'Team coordination' }
            ],
            createdAt: '2025-07-20T09:00:00Z',
            updatedAt: '2025-07-20T09:00:00Z'
        },
        {
            id: 4,
            title: 'Data Analyst – Energy Sector',
            description: 'Analysis of energy data and reporting for a major industry player.',
            contrat: 'Fixed-term',
            secteur: 'ENERGY_MANAGEMENT',
            hardSkills: [
                { id: 12, title: 'Python', level: 'Advanced' },
                { id: 13, title: 'DataViz', level: 'Intermediate' }
            ],
            status: 'Closed',
            closingDate: '2025-07-31',
            taskMissions: [
                { id: 11, title: 'Data extraction' },
                { id: 12, title: 'Dashboard creation' }
            ],
            createdAt: '2025-06-10T14:00:00Z',
            updatedAt: '2025-07-01T10:00:00Z'
        },
        {
            id: 5,
            title: 'IT Support Technician',
            description: 'Technical assistance and user support for a large company.',
            contrat: 'Internship',
            secteur: 'IT_SUPPORT',
            hardSkills: [
                { id: 14, title: 'IT Support', level: 'Beginner' },
                { id: 15, title: 'Windows', level: 'Beginner' }
            ],
            status: 'Open',
            closingDate: '2025-10-01',
            taskMissions: [{ id: 13, title: 'Ticket management' }],
            createdAt: '2025-08-01T08:00:00Z',
            updatedAt: '2025-08-01T08:00:00Z'
        },
        {
            id: 6,
            title: 'Data Solutions Consultant',
            description: 'Supporting clients in implementing data solutions.',
            contrat: 'Freelance',
            secteur: 'DATA_SOLUTIONS',
            hardSkills: [
                { id: 16, title: 'SQL', level: 'Advanced' },
                { id: 17, title: 'Data Engineering', level: 'Advanced' }
            ],
            status: 'Open',
            closingDate: '2025-09-30',
            taskMissions: [{ id: 14, title: 'Consulting and expertise' }],
            createdAt: '2025-07-25T11:00:00Z',
            updatedAt: '2025-07-25T11:00:00Z'
        }
    ];

    filteredOffres = [...this.offres];

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

                if (!searchableText.includes(filters.keyword)) {
                    return false;
                }
            }

            if (filters.contrat && offre.contrat !== filters.contrat) {
                return false;
            }

            if (filters.secteur && offre.secteur !== filters.secteur) {
                return false;
            }

            if (filters.status && offre.status !== filters.status) {
                return false;
            }

            return true;
        });
    }
}
