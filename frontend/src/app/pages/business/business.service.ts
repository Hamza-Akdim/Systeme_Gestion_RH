import { Injectable } from '@angular/core';
import { Prospect } from '../../types/prospect';
import { BusinessSurvey, SurveyResponse } from '../../types/business-survey';

@Injectable({
    providedIn: 'root'
})
export class BusinessService {
    private prospects: Prospect[] = [];
    private surveys: BusinessSurvey[] = [];
    private responses: SurveyResponse[] = [];

    constructor() {
        this.generateMockData();
    }

    // Méthodes pour les prospects
    getProspects(): Promise<Prospect[]> {
        return Promise.resolve(this.prospects);
    }

    getProspect(id: string): Promise<Prospect> {
        const prospect = this.prospects.find(p => p.id === id);
        return Promise.resolve(prospect || {});
    }

    saveProspect(prospect: Prospect): Promise<Prospect> {
        if (prospect.id) {
            const index = this.prospects.findIndex(p => p.id === prospect.id);
            if (index !== -1) {
                this.prospects[index] = { ...prospect, updatedAt: new Date() };
                return Promise.resolve(this.prospects[index]);
            }
        }

        const newProspect = {
            ...prospect,
            id: this.createId(),
            createdAt: new Date(),
            updatedAt: new Date()
        };
        this.prospects.push(newProspect);
        return Promise.resolve(newProspect);
    }

    deleteProspect(id: string): Promise<void> {
        this.prospects = this.prospects.filter(p => p.id !== id);
        return Promise.resolve();
    }

    // Méthodes pour les enquêtes
    getSurveys(): Promise<BusinessSurvey[]> {
        return Promise.resolve(this.surveys);
    }

    getSurvey(id: string): Promise<BusinessSurvey> {
        const survey = this.surveys.find(s => s.id === id);
        return Promise.resolve(survey || {});
    }

    saveSurvey(survey: BusinessSurvey): Promise<BusinessSurvey> {
        if (survey.id) {
            const index = this.surveys.findIndex(s => s.id === survey.id);
            if (index !== -1) {
                this.surveys[index] = { ...survey, updatedAt: new Date() };
                return Promise.resolve(this.surveys[index]);
            }
        }

        const newSurvey = {
            ...survey,
            id: this.createId(),
            createdAt: new Date(),
            updatedAt: new Date()
        };
        this.surveys.push(newSurvey);
        return Promise.resolve(newSurvey);
    }

    deleteSurvey(id: string): Promise<void> {
        this.surveys = this.surveys.filter(s => s.id !== id);
        return Promise.resolve();
    }

    // Méthodes pour les réponses aux enquêtes
    getSurveyResponses(surveyId?: string): Promise<SurveyResponse[]> {
        if (surveyId) {
            return Promise.resolve(this.responses.filter(r => r.surveyId === surveyId));
        }
        return Promise.resolve(this.responses);
    }

    getSurveyResponse(id: string): Promise<SurveyResponse> {
        const response = this.responses.find(r => r.id === id);
        return Promise.resolve(response || {});
    }

    saveSurveyResponse(response: SurveyResponse): Promise<SurveyResponse> {
        if (response.id) {
            const index = this.responses.findIndex(r => r.id === response.id);
            if (index !== -1) {
                this.responses[index] = { ...response };
                return Promise.resolve(this.responses[index]);
            }
        }

        const newResponse = {
            ...response,
            id: this.createId(),
            submittedAt: new Date()
        };
        this.responses.push(newResponse);
        return Promise.resolve(newResponse);
    }

    deleteSurveyResponse(id: string): Promise<void> {
        this.responses = this.responses.filter(r => r.id !== id);
        return Promise.resolve();
    }

    // Méthodes utilitaires
    private createId(): string {
        let id = '';
        const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        for (let i = 0; i < 8; i++) {
            id += chars.charAt(Math.floor(Math.random() * chars.length));
        }
        return id;
    }

    private generateMockData() {
        // Données mockées pour les prospects
        this.prospects = [
            {
                id: 'PROS001',
                firstName: 'Jean',
                lastName: 'Dupont',
                company: 'TechSolutions SA',
                position: 'Directeur Commercial',
                email: 'jean.dupont@techsolutions.fr',
                phone: '+33 6 12 34 56 78',
                address: '15 rue de la Paix',
                city: 'Paris',
                country: 'France',
                postalCode: '75001',
                status: 'active',
                source: 'referral',
                assignedTo: 'Marie Martin',
                notes: 'Intéressé par nos solutions de gestion RH',
                rating: 4,
                lastContact: new Date(2023, 4, 15),
                nextContact: new Date(2023, 5, 1),
                createdAt: new Date(2023, 3, 10),
                updatedAt: new Date(2023, 4, 15),
                tags: ['RH', 'Enterprise', 'Priorité'],
                budget: 50000,
                opportunity: 0.7
            },
            {
                id: 'PROS002',
                firstName: 'Sophie',
                lastName: 'Laurent',
                company: 'InnovateGroup',
                position: 'CEO',
                email: 'sophie.laurent@innovategroup.com',
                phone: '+33 6 98 76 54 32',
                address: '8 avenue des Champs-Élysées',
                city: 'Paris',
                country: 'France',
                postalCode: '75008',
                status: 'new',
                source: 'website',
                assignedTo: 'Thomas Bernard',
                notes: 'Startup en forte croissance, besoin de structurer son département RH',
                rating: 5,
                lastContact: new Date(2023, 4, 20),
                nextContact: new Date(2023, 5, 5),
                createdAt: new Date(2023, 4, 18),
                updatedAt: new Date(2023, 4, 20),
                tags: ['Startup', 'Croissance', 'Premium'],
                budget: 30000,
                opportunity: 0.9
            },
            {
                id: 'PROS003',
                firstName: 'Pierre',
                lastName: 'Martin',
                company: 'ConsultPro',
                position: 'DRH',
                email: 'pierre.martin@consultpro.fr',
                phone: '+33 6 45 67 89 01',
                address: '25 rue du Commerce',
                city: 'Lyon',
                country: 'France',
                postalCode: '69002',
                status: 'inactive',
                source: 'conference',
                assignedTo: 'Julie Dubois',
                notes: 'A participé à notre webinaire sur la digitalisation RH',
                rating: 3,
                lastContact: new Date(2023, 3, 5),
                nextContact: new Date(2023, 6, 10),
                createdAt: new Date(2023, 2, 15),
                updatedAt: new Date(2023, 3, 5),
                tags: ['Consulting', 'Mid-size'],
                budget: 15000,
                opportunity: 0.4
            }
        ];

        // Données mockées pour les enquêtes
        this.surveys = [
            {
                id: 'SURV001',
                title: 'Satisfaction des services RH',
                description: 'Enquête sur la satisfaction des clients concernant nos services de gestion RH',
                startDate: new Date(2023, 3, 1),
                endDate: new Date(2023, 5, 30),
                status: 'active',
                targetAudience: 'Clients actuels',
                questions: [
                    {
                        id: 'Q001',
                        text: 'Comment évaluez-vous la qualité de nos services RH ?',
                        type: 'rating',
                        required: true,
                        order: 1
                    },
                    {
                        id: 'Q002',
                        text: 'Quelles fonctionnalités supplémentaires souhaiteriez-vous voir dans notre plateforme ?',
                        type: 'text',
                        required: false,
                        order: 2
                    },
                    {
                        id: 'Q003',
                        text: 'Recommanderiez-vous nos services à d\'autres entreprises ?',
                        type: 'boolean',
                        required: true,
                        order: 3
                    }
                ],
                createdAt: new Date(2023, 2, 15),
                updatedAt: new Date(2023, 2, 15),
                createdBy: 'Admin',
                isPublic: true,
                tags: ['Satisfaction', 'Services RH']
            },
            {
                id: 'SURV002',
                title: 'Besoins en recrutement 2023',
                description: 'Étude des besoins en recrutement et gestion des talents pour l\'année 2023',
                startDate: new Date(2023, 4, 15),
                endDate: new Date(2023, 6, 15),
                status: 'draft',
                targetAudience: 'Prospects et clients',
                questions: [
                    {
                        id: 'Q004',
                        text: 'Combien de recrutements prévoyez-vous pour 2023 ?',
                        type: 'multiple_choice',
                        required: true,
                        options: ['0-5', '6-10', '11-20', '21-50', '50+'],
                        order: 1
                    },
                    {
                        id: 'Q005',
                        text: 'Quels sont vos principaux défis en matière de recrutement ?',
                        type: 'multiple_choice',
                        required: true,
                        options: ['Attirer les talents', 'Évaluation des compétences', 'Onboarding', 'Rétention', 'Autre'],
                        order: 2
                    },
                    {
                        id: 'Q006',
                        text: 'Utilisez-vous actuellement un logiciel de gestion des recrutements ?',
                        type: 'boolean',
                        required: true,
                        order: 3
                    }
                ],
                createdAt: new Date(2023, 4, 10),
                updatedAt: new Date(2023, 4, 12),
                createdBy: 'Admin',
                isPublic: false,
                tags: ['Recrutement', 'Étude de marché', '2023']
            }
        ];

        // Données mockées pour les réponses aux enquêtes
        this.responses = [
            {
                id: 'RESP001',
                surveyId: 'SURV001',
                respondentName: 'Michel Leroy',
                respondentEmail: 'michel.leroy@company.com',
                respondentCompany: 'Company SAS',
                submittedAt: new Date(2023, 3, 15),
                answers: [
                    {
                        questionId: 'Q001',
                        value: 4
                    },
                    {
                        questionId: 'Q002',
                        value: 'J\'aimerais voir plus d\'intégrations avec les outils de communication.'
                    },
                    {
                        questionId: 'Q003',
                        value: true
                    }
                ],
                satisfactionScore: 4,
                feedback: 'Très satisfait dans l\'ensemble, quelques améliorations possibles.',
                prospectId: 'PROS001'
            },
            {
                id: 'RESP002',
                surveyId: 'SURV001',
                respondentName: 'Isabelle Moreau',
                respondentEmail: 'isabelle.moreau@enterprise.fr',
                respondentCompany: 'Enterprise France',
                submittedAt: new Date(2023, 4, 5),
                answers: [
                    {
                        questionId: 'Q001',
                        value: 5
                    },
                    {
                        questionId: 'Q002',
                        value: 'Des tableaux de bord plus personnalisables seraient appréciés.'
                    },
                    {
                        questionId: 'Q003',
                        value: true
                    }
                ],
                satisfactionScore: 5,
                feedback: 'Excellent service, très réactif et professionnel.',
                prospectId: 'PROS002'
            }
        ];
    }
}
