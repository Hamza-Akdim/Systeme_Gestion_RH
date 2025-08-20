// src/app/pages/profile-cv/profile-cv.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { MyInformationComponent } from './components/my-information.component';
import { MyExperienceComponent } from './components/my-experience.component';
import { ApplicationQuestionsComponent } from './components/application-questions.component';
import { VoluntaryDisclosuresComponent } from "./components/voluntary-disclosures.component";
import { ReviewComponent } from "./components/review.component";

@Component({
    selector: 'app-profile-cv',
    standalone: true,
    imports: [CommonModule, FormsModule, MyInformationComponent, MyExperienceComponent, ApplicationQuestionsComponent, VoluntaryDisclosuresComponent, ReviewComponent],
    template: `
        <header class="bg-cardbg border-b border-white/10 sticky top-0 z-40">
            <div class="max-w-7xl mx-auto px-4 sm:px-6">
                <div class="h-20 flex items-center justify-between text-white relative">
                    <!-- Left: Go Back button -->
                    <a href="#" class="absolute left-0 flex items-center gap-2 bg-[#0d6efd] hover:bg-blue-600 text-white font-medium px-3 py-2 rounded-full shadow-md transition-all duration-200">
                        <span class="">&larr;</span>
                        <span>Go Back</span>
                    </a>

                    <!-- Center: Logo -->
                    <div class="mx-auto">
                        <img src="/images/logo-quanta.png" class="h-14" />
                    </div>
                </div>
            </div>
        </header>

        <!-- Main -->
        <main class="min-h-screen bg-sitebg text-white">
            <div class="max-w-7xl mx-auto px-4 sm:px-6 py-10">
                <div class="w-full lg:max-w-6xl sm:max-w-3xl mx-auto rounded-2xl bg-cardbg p-8 shadow-xl">
                    <h1 class="text-2xl font-bold mt-3">DEVELOPPEUR BACK-END (F/H)</h1>

                    <ol class="mt-6 flex items-center w-full">
                        <li *ngFor="let step of steps; let i = index" class="flex flex-col items-center flex-1">
                            <div class="flex items-center w-full">
                                <div class="flex-1 h-[2px]" [class.bg-transparent]="i === 0" [class.bg-[#0088CE]]="i < currentStep - 1" [class.bg-gray-400]="i >= currentStep - 1 && i !== 0"></div>
                                <div class="flex items-center justify-center w-6 h-6 rounded-full border-2" [class.border-[#0088CE]]="i + 1 <= currentStep" [class.border-gray-400]="i + 1 > currentStep">
                                    <div class="w-3 h-3 rounded-full" [class.bg-[#0088CE]]="i + 1 <= currentStep" [class.bg-gray-400]="i + 1 > currentStep"></div>
                                </div>
                                <div class="flex-1 h-[2px]" [class.bg-transparent]="i === steps.length - 1" [class.bg-[#0088CE]]="i + 1 < currentStep" [class.bg-gray-400]="i + 1 >= currentStep && i !== steps.length - 1"></div>
                            </div>
                            <label class="mt-2 text-sm" [class.font-semibold]="i + 1 === currentStep">{{ step }}</label>
                        </li>
                    </ol>

                    <div class="mx-4 px-24">
                        <h2 class="text-2xl font-bold mt-8 text-center">{{ steps[currentStep - 1] }}</h2>
                        <p class="text-sm mt-2"><span class="text-red-400">*</span> Indicates a required field</p>

                        <form #candidateForm="ngForm" novalidate>
                            <div *ngIf="currentStep === 1">
                                <app-my-information [(candidate)]="candidate"></app-my-information>
                            </div>

                            <div *ngIf="currentStep === 2">
                                <app-my-experience [(experience)]="experienceData"></app-my-experience>
                            </div>

                            <div *ngIf="currentStep === 3">
                                <app-application-questions [questions]="applicationQuestions"></app-application-questions>
                            </div>

                            <div *ngIf="currentStep === 4">
                                <app-voluntary-disclosures [(accepted)]="voluntaryAccepted"></app-voluntary-disclosures>
                            </div>

                            <div *ngIf="currentStep === 5">
                                <app-review ></app-review>
                            </div>

                            <div class="mt-8 flex justify-between">
                                <button type="button" (click)="prevStep()" [disabled]="currentStep === 1" class="bg-gray-500 hover:bg-gray-600 text-white font-semibold px-6 py-2 rounded disabled:opacity-50">Previous</button>
                                <button *ngIf="currentStep < steps.length" type="button" (click)="nextStep(candidateForm)" class="bg-[#0d6efd] hover:bg-blue-600 text-white font-semibold px-6 py-2 rounded">Next</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>
    `
})
export class ProfileCvComponent {
    currentStep = 1;
    steps = ['My Information', 'My Experience', 'Application Questions', 'Voluntary Disclosures', 'Review'];

    candidate = {
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: ''
    };

    experienceData = {
        diplomasDTO: [
            {
                titre: '',
                niveau: '',
                organismeDelivred: '',
                dateDebut: '',
                dateFin: '',
                categorieDiplome: ''
            }
        ],
        skillDTOs: [
            {
                title: '',
                category: ''
            }
        ],
        langueConsultants: [
            {
                level: '',
                langue: ''
            }
        ],
        missionProjectDTOs: [
            {
                titleMission: '',
                startDate: '',
                endDate: '',
                description: '',
                techniqueTools: ''
            }
        ]
    };

    applicationQuestions = [
        {
            id: 52,
            question: 'How long have you been using DevOps tools ?',
            responseType: 'TEXT',
            important: 'Y',
            answer: ''
        },
        {
            id: 53,
            question: 'Are you allowed to work in France ?',
            responseType: 'SELECT',
            important: 'Y',
            selectOptions: ['Yes', 'No'],
            answer: ''
        },
        {
            id: 54,
            question: 'How much do you expect your salary to be ?',
            responseType: 'TEXT',
            important: 'N',
            answer: ''
        }
    ];

    voluntaryAccepted = false;


    nextStep(form: NgForm) {
        if (form.valid) {
            this.currentStep++;
        } else {
            form.control.markAllAsTouched();
        }
    }

    prevStep() {
        if (this.currentStep > 1) {
            this.currentStep--;
        }
    }

    submitForm() {
        console.log('Candidate DTO:', this.candidate);
        // here you send to backend via HTTP POST
    }
}
