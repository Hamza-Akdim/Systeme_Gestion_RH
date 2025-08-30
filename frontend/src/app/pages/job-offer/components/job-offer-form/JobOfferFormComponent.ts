import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JobOfferService } from '../../job-offer-service';

@Component({
    selector: 'app-job-offer-form',
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule],
    template: `
        <div class="bg-[#1a2238] text-white p-8 rounded-xl shadow-lg space-y-8">
            <!-- Heading -->
            <h2 class="text-2xl font-semibold text-center mb-6 pb-2 border-b-2 border-gray-700">Create New Offer</h2>

            <!--Displays after submit  -->
            <div
                *ngIf="message"
                [ngClass]="{
                    'bg-green-600 text-white': messageType === 'success',
                    'bg-red-600 text-white': messageType === 'error'
                }"
                class="p-3 rounded-lg text-center font-semibold shadow-md"
            >
                {{ message }}
            </div>

            <!-- Form -->
            <form [formGroup]="form" (ngSubmit)="onSubmit()" class="space-y-8">
                <!-- Title -->
                <div>
                    <label class="block text-sm font-medium mb-1">Job Title</label>
                    <input formControlName="title" class="input-field" />
                </div>

                <!-- Description -->
                <div>
                    <label class="block text-sm font-medium mb-1">Description</label>
                    <textarea formControlName="description" rows="3" class="input-field"></textarea>
                </div>

                <!-- Contract / Sector / Status -->
                <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                    <div>
                        <label class="block text-sm font-medium mb-1">Contract</label>
                        <select formControlName="contrat" class="input-field">
                            <option *ngFor="let c of contrats" [value]="c">{{ c }}</option>
                        </select>
                    </div>
                    <div>
                        <label class="block text-sm font-medium mb-1">Sector</label>
                        <select formControlName="secteur" class="input-field">
                            <option *ngFor="let s of secteurs" [value]="s">{{ s }}</option>
                        </select>
                    </div>
                    <div>
                        <label class="block text-sm font-medium mb-1">Status</label>
                        <select formControlName="status" class="input-field">
                            <option *ngFor="let st of statuses" [value]="st">{{ st }}</option>
                        </select>
                    </div>
                </div>

                <!-- Closing Date -->
                <div>
                    <label class="block text-sm font-medium mb-1">Closing Date</label>
                    <input type="date" formControlName="closingDate" class="input-field" />
                </div>

                <!-- Missions -->
                <div class="section-box">
                    <h3 class="section-title">Missions</h3>
                    <div formArrayName="taskMissions" class="space-y-2">
                        <div *ngFor="let mission of taskMissions.controls; let i = index" [formGroupName]="i" class="flex gap-2">
                            <input formControlName="title" placeholder="Mission title" class="input-field flex-1" />
                        </div>
                    </div>
                    <div class="flex justify-end gap-2 mt-3">
                        <button type="button" (click)="addMission()" class="btn-circle">+</button>
                        <button type="button" (click)="removeMission(taskMissions.length - 1)" class="btn-circle">−</button>
                    </div>
                </div>

                <!-- Skills -->
                <div class="section-box">
                    <h3 class="section-title">Hard Skills</h3>
                    <div formArrayName="hardSkills" class="space-y-2">
                        <div *ngFor="let skill of hardSkills.controls; let i = index" [formGroupName]="i" class="grid grid-cols-2 gap-2">
                            <input formControlName="title" placeholder="Skill name" class="input-field" />
                            <input formControlName="level" placeholder="Level" class="input-field" />
                        </div>
                    </div>
                    <div class="flex justify-end gap-2 mt-3">
                        <button type="button" (click)="addSkill()" class="btn-circle">+</button>
                        <button type="button" (click)="removeSkill(hardSkills.length - 1)" class="btn-circle">−</button>
                    </div>
                </div>

                <!-- Questions -->
                <div class="section-box">
                    <h3 class="section-title">Application Questions</h3>
                    <div formArrayName="jobQuestionDTOS" class="space-y-3">
                        <div *ngFor="let q of jobQuestionDTOS.controls; let i = index" [formGroupName]="i" class="bg-[#232b47] p-3 rounded-lg">
                            <input formControlName="question" placeholder="Question" class="input-field mb-2" />
                            <select formControlName="responseType" class="input-field mb-2">
                                <option value="TEXT">Text</option>
                                <option value="SELECT">Select</option>
                            </select>
                            <select formControlName="important" class="input-field mb-2">
                                <option value="Y">Important</option>
                                <option value="N">Optional</option>
                            </select>
                            <div *ngIf="q.get('responseType')?.value === 'SELECT'">
                                <label class="block text-xs text-gray-400 mb-1">Options (comma separated)</label>
                                <input formControlName="selectOptions" placeholder="Yes,No" class="input-field" />
                            </div>
                        </div>
                    </div>
                    <div class="flex justify-end gap-2 mt-3">
                        <button type="button" (click)="addQuestion()" class="btn-circle">+</button>
                        <button type="button" (click)="removeQuestion(jobQuestionDTOS.length - 1)" class="btn-circle">−</button>
                    </div>
                </div>

                <!-- Actions -->
                <div class="flex py-2 justify-center gap-5">
                    <button (click)="cancel()" type="button" class="flex items-center gap-2 border-2 border-gray-400 text-gray-300 font-semibold text-lg p-1 px-3 rounded-md shadow-md hover:bg-gray-500 hover:border-gray-500 hover:text-white">
                        Cancel
                    </button>
                    <button type="submit" class="flex items-center gap-2 border-2 border-[#0d6efd] text-[#0d6efd] font-semibold text-lg p-1 px-5 rounded-md shadow-md hover:bg-[#0d6efd] hover:text-white">Save</button>
                </div>
            </form>
        </div>
    `,
    styles: [
        `
            .input-field {
                @apply w-full px-3 py-2 rounded bg-[#232b47] border border-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500 transition;
            }
            .section-box {
                @apply border-t border-gray-700 pt-5;
            }
            .section-title {
                @apply text-lg font-semibold mb-3 text-gray-200;
            }
            .btn-secondary {
                @apply bg-blue-600 hover:bg-blue-700 text-white px-3 py-2 rounded-lg text-sm;
            }
            .btn-success {
                @apply bg-green-600 hover:bg-green-700 text-white px-3 py-2 rounded-lg text-sm;
            }
            .btn-circle {
                @apply inline-flex items-center justify-center w-8 h-8 rounded-full bg-gray-700 text-white hover:bg-gray-600 transition font-bold;
            }
        `
    ]
})
export class JobOfferFormComponent implements OnInit {
    form!: FormGroup;

    message: string | null = null;
    messageType: 'success' | 'error' | null = null;

    contrats = ['CDI', 'CDD', 'Intern', 'FREELANCE'];
    secteurs = ['SOFTWARE_DEVELOPMENT', 'IT_MANAGEMENT', 'DATA_SOLUTIONS', 'POWER_GENERATION', 'TELECOMMUNICATIONS', 'IT_SUPPORT', 'ENERGY_AUDITS', 'STAFFING', 'POWER_SYSTEMS', 'ENERGY_MANAGEMENT'];
    statuses = ['OPEN', 'FILLED', 'CLOSED', 'ON_HOLD'];

    constructor(
        private fb: FormBuilder,
        private router: Router,
        private jobOfferService: JobOfferService
    ) {}

    ngOnInit(): void {
        this.form = this.fb.group({
            title: ['', Validators.required],
            description: [''],
            contrat: ['CDI'],
            secteur: ['SOFTWARE_DEVELOPMENT'],
            status: ['OPEN'],
            closingDate: [''],
            taskMissions: this.fb.array([this.fb.group({ title: [''] })]),
            hardSkills: this.fb.array([this.fb.group({ title: [''], level: [''] })]),
            jobQuestionDTOS: this.fb.array([this.fb.group({ question: [''], responseType: ['TEXT'], important: ['N'], selectOptions: [''] })])
        });
    }

    get taskMissions() {
        return this.form.get('taskMissions') as FormArray;
    }
    get hardSkills() {
        return this.form.get('hardSkills') as FormArray;
    }
    get jobQuestionDTOS() {
        return this.form.get('jobQuestionDTOS') as FormArray;
    }

    addMission() {
        this.taskMissions.push(this.fb.group({ title: [''] }));
    }
    removeMission(i: number) {
        this.taskMissions.removeAt(i);
    }

    addSkill() {
        this.hardSkills.push(this.fb.group({ title: [''], level: [''] }));
    }
    removeSkill(i: number) {
        this.hardSkills.removeAt(i);
    }

    addQuestion() {
        this.jobQuestionDTOS.push(this.fb.group({ question: [''], responseType: ['TEXT'], important: ['N'], selectOptions: [''] }));
    }
    removeQuestion(i: number) {
        this.jobQuestionDTOS.removeAt(i);
    }

    onSubmit() {
        if (this.form.invalid) {
            this.form.markAllAsTouched();
            return;
        }

        const formValue = { ...this.form.value };

        formValue.jobQuestionDTOS = formValue.jobQuestionDTOS.map((q: any) => ({
            ...q,
            selectOptions: q.responseType === 'SELECT' && q.selectOptions ? q.selectOptions.split(',').map((s: string) => s.trim()) : []
        }));

        this.jobOfferService.createJobOffer(formValue).subscribe({
            next: (res) => {
                console.log('Offer created:', res);
                this.message = 'Offer created successfully!';
                this.messageType = 'success';

                setTimeout(() => this.router.navigate(['/hr/job-offer']), 2000);
            },
            error: (err) => {
                console.error('Error creating offer:', err);
                this.message = 'Failed to create offer. Please try again.';
                this.messageType = 'error';
                setTimeout(() => {
                    this.message = '';
                    this.messageType = null;
                }, 2000);
            }
        });
    }

    cancel() {
        this.router.navigate(['/hr/job-offer']);
    }
}
