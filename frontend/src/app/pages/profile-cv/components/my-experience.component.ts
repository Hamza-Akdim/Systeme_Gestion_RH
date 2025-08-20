import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-my-experience',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
        <div class="space-y-10 mt-8">
            <!-- Diplomas -->
            <section>
                <h3 class="section-title">Diplomas</h3>
                <div *ngFor="let diploma of experience.diplomasDTO; let i = index" class="form-card">
                    <input type="text" placeholder="Title" [(ngModel)]="diploma.titre" name="titre{{ i }}" class="w-full bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500" />
                    <input type="text" placeholder="Level" [(ngModel)]="diploma.niveau" name="niveau{{ i }}" class="w-full bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500" />
                    <input
                        type="text"
                        placeholder="Institution"
                        [(ngModel)]="diploma.organismeDelivred"
                        name="organisme{{ i }}"
                        class="w-full bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                    <div class="flex gap-4">
                        <input type="date" [(ngModel)]="diploma.dateDebut" name="dateDebut{{ i }}" class="flex-1 bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500" />
                        <input type="date" [(ngModel)]="diploma.dateFin" name="dateFin{{ i }}" class="flex-1 bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500" />
                    </div>
                    <select [(ngModel)]="diploma.categorieDiplome" name="categorie{{ i }}" class="w-full bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500">
                        <option value="" disabled selected>Select diploma</option>

                        <option *ngFor="let cat of diplomaCategories" [value]="cat">{{ cat }}</option>
                    </select>
                </div>
                <div class="flex justify-end gap-2 mt-3">
                    <button type="button" (click)="addDiploma()" class="btn-circle">+</button>
                    <button type="button" (click)="removeDiploma(experience.diplomasDTO.length - 1)" [disabled]="experience.diplomasDTO.length === 0" class="btn-circle disabled:opacity-50">−</button>
                </div>
            </section>

            <!-- Skills -->
            <section>
                <h3 class="section-title">Skills</h3>
                <div *ngFor="let skill of experience.skillDTOs; let i = index" class="form-card">
                    <input type="text" placeholder="Title" [(ngModel)]="skill.title" name="skillTitle{{ i }}" class="w-full bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500" />
                    <select [(ngModel)]="skill.category" name="skillCategory{{ i }}" class="w-full bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500">
                        <option value="" disabled selected>Type of Skill </option>

                        <option *ngFor="let cat of skillCategories" [value]="cat">{{ cat }}</option>
                    </select>
                </div>
                <div class="flex justify-end gap-2 mt-3">
                    <button type="button" (click)="addSkill()" class="btn-circle">+</button>
                    <button type="button" (click)="removeSkill(experience.skillDTOs.length - 1)" [disabled]="experience.skillDTOs.length === 0" class="btn-circle disabled:opacity-50">−</button>
                </div>
            </section>

            <!-- Languages -->
            <section>
                <h3 class="section-title">Languages</h3>
                <div *ngFor="let lang of experience.langueConsultants; let i = index" class="form-card">
                    <select [(ngModel)]="lang.langue" name="langName{{ i }}" class="w-full bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500">
                        <option value=""  disabled selected>language</option>

                        <option *ngFor="let langName of languages" [value]="langName">{{ langName }}</option>
                    </select>
                    <select [(ngModel)]="lang.level" name="langLevel{{ i }}" class="w-full bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500">
                        <option value="" disabled selected>Level</option>

                        <option *ngFor="let lvl of languageLevels" [value]="lvl">{{ lvl }}</option>
                    </select>
                </div>
                <div class="flex justify-end gap-2 mt-3">
                    <button type="button" (click)="addLanguage()" class="btn-circle">+</button>
                    <button type="button" (click)="removeLanguage(experience.langueConsultants.length - 1)" [disabled]="experience.langueConsultants.length === 0" class="btn-circle disabled:opacity-50">−</button>
                </div>
            </section>

            <!-- Missions / Projects -->
            <section>
                <h3 class="section-title">Missions / Projects</h3>
                <div *ngFor="let mission of experience.missionProjectDTOs; let i = index" class="form-card">
                    <input
                        type="text"
                        placeholder="Title"
                        [(ngModel)]="mission.titleMission"
                        name="missionTitle{{ i }}"
                        class="w-full bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                    <div class="flex gap-4">
                        <input type="date" [(ngModel)]="mission.startDate" name="missionStart{{ i }}" class="flex-1 bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500" />
                        <input type="date" [(ngModel)]="mission.endDate" name="missionEnd{{ i }}" class="flex-1 bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500" />
                    </div>
                    <textarea
                        placeholder="Description"
                        [(ngModel)]="mission.description"
                        name="missionDesc{{ i }}"
                        class="w-full bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white resize-none focus:outline-none focus:ring-2 focus:ring-blue-500"
                    ></textarea>
                    <input
                        type="text"
                        placeholder="Tools"
                        [(ngModel)]="mission.techniqueTools"
                        name="missionTools{{ i }}"
                        class="w-full bg-gray-900 border border-gray-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>
                <div class="flex justify-end gap-2 mt-3">
                    <button type="button" (click)="addMission()" class="btn-circle">+</button>
                    <button type="button" (click)="removeMission(experience.missionProjectDTOs.length - 1)" [disabled]="experience.missionProjectDTOs.length === 0" class="btn-circle disabled:opacity-50">−</button>
                </div>
            </section>
        </div>
    `,
    styles: [
        `
            .section-title {
                @apply text-xl font-bold text-blue-300 border-b border-gray-700 pb-2;
            }
            .form-card {
                @apply bg-gray-800 p-4 rounded-lg space-y-3 mt-4 border border-gray-700;
            }
            .btn-circle {
                @apply inline-flex items-center justify-center w-8 h-8 rounded-full bg-gray-700 text-white hover:bg-gray-600 transition;
            }
        `
    ]
})
export class MyExperienceComponent {
    @Input() experience: any;
    @Output() experienceChange = new EventEmitter<any>();

    diplomaCategories = ['CERTIFICATION', 'LICENSE', 'MASTER', 'PHD', 'DIPLOMA'];
    skillCategories = ['TECHNICAL_SKILLS', 'SOFT_SKILLS', 'MANAGEMENT_SKILLS'];
    languages = ['French', 'English', 'Spanish', 'German', 'Italian'];
    languageLevels = ['Beginner', 'Intermediate', 'Advanced', 'Native'];

    ngOnInit() {
        if (!this.experience) {
            this.experience = {
                diplomasDTO: [{ titre: '', niveau: '', organismeDelivred: '', dateDebut: '', dateFin: '', categorieDiplome: '', pathFile: '', qrCode: '' }],
                skillDTOs: [{ title: '', category: '' }],
                langueConsultants: [{ level: '', langue: ' '}],
                missionProjectDTOs: [{ titleMission: '', startDate: '', endDate: '', description: '', techniqueTools: '' }]
            };
        }
    }

    addDiploma() {
        this.experience.diplomasDTO.push({});
        this.emitChange();
    }
    removeDiploma(i: number) {
        this.experience.diplomasDTO.splice(i, 1);
        this.emitChange();
    }

    addSkill() {
        this.experience.skillDTOs.push({});
        this.emitChange();
    }
    removeSkill(i: number) {
        this.experience.skillDTOs.splice(i, 1);
        this.emitChange();
    }

    addLanguage() {
        this.experience.langueConsultants.push({ langue: {} });
        this.emitChange();
    }
    removeLanguage(i: number) {
        this.experience.langueConsultants.splice(i, 1);
        this.emitChange();
    }

    addMission() {
        this.experience.missionProjectDTOs.push({});
        this.emitChange();
    }
    removeMission(i: number) {
        this.experience.missionProjectDTOs.splice(i, 1);
        this.emitChange();
    }

    emitChange() {
        this.experienceChange.emit(this.experience);
    }
}
