import { Injectable } from '@angular/core';
import { Candidate } from '../../types/candidate';
import { Application } from '../../types/application';

@Injectable({
    providedIn: 'root'
})
export class CandidateService {
    private candidates: Candidate[] = [];
    private applications: Application[] = [];

    constructor() {
        this.generateMockData();
    }

    getCandidates(): Promise<Candidate[]> {
        return Promise.resolve(this.candidates);
    }

    getCandidate(id: string): Promise<Candidate> {
        const candidate = this.candidates.find(c => c.id === id);
        return Promise.resolve(candidate || {});
    }

    saveCandidate(candidate: Candidate): Promise<Candidate> {
        if (candidate.id) {
            const index = this.candidates.findIndex(c => c.id === candidate.id);
            if (index !== -1) {
                this.candidates[index] = { ...candidate, updatedAt: new Date() };
                return Promise.resolve(this.candidates[index]);
            }
        }
        
        const newCandidate = { 
            ...candidate, 
            id: this.createId(),
            createdAt: new Date(),
            updatedAt: new Date()
        };
        this.candidates.push(newCandidate);
        return Promise.resolve(newCandidate);
    }

    deleteCandidate(id: string): Promise<void> {
        this.candidates = this.candidates.filter(c => c.id !== id);
        return Promise.resolve();
    }

    getApplications(candidateId?: string): Promise<Application[]> {
        if (candidateId) {
            return Promise.resolve(this.applications.filter(a => a.candidateId === candidateId));
        }
        return Promise.resolve(this.applications);
    }

    getApplication(id: string): Promise<Application> {
        const application = this.applications.find(a => a.id === id);
        return Promise.resolve(application || {});
    }

    saveApplication(application: Application): Promise<Application> {
        if (application.id) {
            const index = this.applications.findIndex(a => a.id === application.id);
            if (index !== -1) {
                this.applications[index] = { ...application, lastUpdated: new Date() };
                
                // Add to history
                if (!this.applications[index].history) {
                    this.applications[index].history = [];
                }
                
                this.applications[index].history.push({
                    date: new Date(),
                    status: application.status || 'updated',
                    notes: 'Application updated'
                });
                
                return Promise.resolve(this.applications[index]);
            }
        }
        
        const newApplication = { 
            ...application, 
            id: this.createId(),
            appliedDate: new Date(),
            lastUpdated: new Date(),
            history: [{
                date: new Date(),
                status: application.status || 'applied',
                notes: 'Application submitted'
            }]
        };
        this.applications.push(newApplication);
        return Promise.resolve(newApplication);
    }

    deleteApplication(id: string): Promise<void> {
        this.applications = this.applications.filter(a => a.id !== id);
        return Promise.resolve();
    }

    private createId(): string {
        let id = '';
        const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        for (let i = 0; i < 8; i++) {
            id += chars.charAt(Math.floor(Math.random() * chars.length));
        }
        return id;
    }

    private generateMockData() {
        // Mock candidates
        this.candidates = [
            {
                id: 'CAND001',
                firstName: 'Jean',
                lastName: 'Dupont',
                email: 'jean.dupont@example.com',
                phone: '+33 6 12 34 56 78',
                address: '15 rue de Paris',
                city: 'Lyon',
                country: 'France',
                postalCode: '69000',
                birthDate: new Date(1985, 5, 15),
                skills: ['JavaScript', 'Angular', 'TypeScript', 'HTML', 'CSS'],
                experience: 8,
                education: 'Master en Informatique',
                resumeUrl: 'filecv/Rapport PFE_1.pdf',
                profilePicture: '/images/avatar/circle-big/avatar-m-1.png',
                status: 'available',
                rating: 4,
                notes: 'Excellent candidat avec une forte expérience en développement frontend',
                createdAt: new Date(2025, 1, 15),
                updatedAt: new Date(2025, 3, 20)
            },
            {
                id: 'CAND002',
                firstName: 'Marie',
                lastName: 'Laurent',
                email: 'marie.laurent@example.com',
                phone: '+33 6 98 76 54 32',
                address: '8 avenue Victor Hugo',
                city: 'Paris',
                country: 'France',
                postalCode: '75016',
                birthDate: new Date(1990, 8, 22),
                skills: ['Java', 'Spring', 'Hibernate', 'SQL', 'Docker'],
                experience: 5,
                education: 'École d\'ingénieur',
                resumeUrl: 'assets/demo/resume/resume2.pdf',
                profilePicture: '/images/avatar/circle-big/avatar-m-2.png',
                status: 'interviewing',
                rating: 3,
                notes: 'Bonne connaissance des technologies backend',
                createdAt: new Date(2025, 2, 10),
                updatedAt: new Date(2025, 4, 5)
            },
            {
                id: 'CAND003',
                firstName: 'Thomas',
                lastName: 'Martin',
                email: 'thomas.martin@example.com',
                phone: '+33 6 45 67 89 01',
                address: '25 rue du Commerce',
                city: 'Bordeaux',
                country: 'France',
                postalCode: '33000',
                birthDate: new Date(1988, 3, 10),
                skills: ['Python', 'Django', 'Flask', 'MongoDB', 'AWS'],
                experience: 7,
                education: 'Doctorat en Informatique',
                resumeUrl: 'assets/demo/resume/resume3.pdf',
                profilePicture: '/images/avatar/circle-big/avatar-m-3.png',
                status: 'hired',
                rating: 5,
                notes: 'Excellent profil technique avec une bonne capacité d\'adaptation',
                createdAt: new Date(2025, 0, 5),
                updatedAt: new Date(2025, 5, 15)
            }
        ];

        // Mock applications
        this.applications = [
            {
                id: 'APP001',
                candidateId: 'CAND001',
                jobId: 'JOB001',
                jobTitle: 'Développeur Frontend Senior',
                status: 'interview',
                appliedDate: new Date(2025, 3, 10),
                lastUpdated: new Date(2025, 3, 15),
                interviewDate: new Date(2025, 3, 20),
                interviewNotes: 'Entretien technique réussi, à programmer pour un second entretien',
                feedback: 'Bonnes compétences techniques, communication claire',
                rating: 4,
                history: [
                    {
                        date: new Date(2025, 3, 10),
                        status: 'applied',
                        notes: 'Candidature soumise'
                    },
                    {
                        date: new Date(2025, 3, 15),
                        status: 'screening',
                        notes: 'CV présélectionné'
                    },
                    {
                        date: new Date(2025, 3, 20),
                        status: 'interview',
                        notes: 'Entretien technique programmé'
                    }
                ]
            },
            {
                id: 'APP002',
                candidateId: 'CAND002',
                jobId: 'JOB002',
                jobTitle: 'Développeur Backend Java',
                status: 'offer',
                appliedDate: new Date(2025, 2, 5),
                lastUpdated: new Date(2025, 3, 25),
                interviewDate: new Date(2025, 3, 15),
                interviewNotes: 'Très bon entretien, compétences techniques solides',
                feedback: 'Excellente connaissance de Java et Spring',
                rating: 4.5,
                salary: 55000,
                offerStatus: 'pending',
                offerDate: new Date(2025, 3, 25),
                history: [
                    {
                        date: new Date(2025, 2, 5),
                        status: 'applied',
                        notes: 'Candidature soumise'
                    },
                    {
                        date: new Date(2025, 2, 15),
                        status: 'screening',
                        notes: 'CV présélectionné'
                    },
                    {
                        date: new Date(2025, 3, 15),
                        status: 'interview',
                        notes: 'Entretien technique réalisé'
                    },
                    {
                        date: new Date(2025, 3, 25),
                        status: 'offer',
                        notes: 'Offre envoyée'
                    }
                ]
            },
            {
                id: 'APP003',
                candidateId: 'CAND003',
                jobId: 'JOB003',
                jobTitle: 'Développeur Full Stack',
                status: 'hired',
                appliedDate: new Date(2025, 1, 10),
                lastUpdated: new Date(2025, 3, 5),
                interviewDate: new Date(2025, 2, 1),
                interviewNotes: 'Excellent candidat, parfait pour le poste',
                feedback: 'Très bonnes compétences techniques et soft skills',
                rating: 5,
                salary: 65000,
                offerStatus: 'accepted',
                offerDate: new Date(2025, 2, 15),
                startDate: new Date(2025, 4, 1),
                history: [
                    {
                        date: new Date(2025, 1, 10),
                        status: 'applied',
                        notes: 'Candidature soumise'
                    },
                    {
                        date: new Date(2025, 1, 20),
                        status: 'screening',
                        notes: 'CV présélectionné'
                    },
                    {
                        date: new Date(2025, 2, 1),
                        status: 'interview',
                        notes: 'Entretien technique réalisé'
                    },
                    {
                        date: new Date(2025, 2, 15),
                        status: 'offer',
                        notes: 'Offre envoyée'
                    },
                    {
                        date: new Date(2025, 2, 20),
                        status: 'accepted',
                        notes: 'Offre acceptée'
                    },
                    {
                        date: new Date(2025, 3, 5),
                        status: 'hired',
                        notes: 'Contrat signé, démarrage le 1er mai'
                    }
                ]
            }
        ];
    }
}
