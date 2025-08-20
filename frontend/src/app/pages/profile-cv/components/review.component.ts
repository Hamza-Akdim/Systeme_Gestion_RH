import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-review',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="space-y-8">
      <!-- Header -->
      <h2 class="text-2xl font-bold text-blue-400">Review Your Application</h2>
      <p class="text-gray-400">Please make sure all your information is correct before submitting.</p>

      <!-- Personal Info -->
      <section>
        <h3 class="section-title">Personal Information</h3>
        <ul class="review-list">
          <li><span class="label">First Name:</span> {{ candidate?.firstName }}</li>
          <li><span class="label">Last Name:</span> {{ candidate?.lastName }}</li>
          <li><span class="label">Email:</span> {{ candidate?.email }}</li>
          <li><span class="label">Phone:</span> {{ candidate?.phoneNumber }}</li>
        </ul>
      </section>

      <!-- Diplomas -->
      <section>
        <h3 class="section-title">Diplomas</h3>
        <div *ngIf="experience?.diplomasDTO?.length; else noDiplomas">
          <div *ngFor="let d of experience.diplomasDTO" class="review-card">
            <p><span class="label">Title:</span> {{ d.titre }}</p>
            <p><span class="label">Level:</span> {{ d.niveau }}</p>
            <p><span class="label">Institution:</span> {{ d.organismeDelivred }}</p>
            <p><span class="label">Dates:</span> {{ d.dateDebut }} → {{ d.dateFin }}</p>
            <p><span class="label">Category:</span> {{ d.categorieDiplome }}</p>
          </div>
        </div>
        <ng-template #noDiplomas><p class="text-gray-500">No diplomas added.</p></ng-template>
      </section>

      <!-- Skills -->
      <section>
        <h3 class="section-title">Skills</h3>
        <ul *ngIf="experience?.skillDTOs?.length; else noSkills" class="list-disc ml-6 text-white">
          <li *ngFor="let s of experience.skillDTOs">{{ s.title }} ({{ s.category }})</li>
        </ul>
        <ng-template #noSkills><p class="text-gray-500">No skills added.</p></ng-template>
      </section>

      <!-- Languages -->
      <section>
        <h3 class="section-title">Languages</h3>
        <ul *ngIf="experience?.langueConsultants?.length; else noLangs" class="list-disc ml-6 text-white">
          <li *ngFor="let l of experience.langueConsultants">{{ l.langue }} - {{ l.level }}</li>
        </ul>
        <ng-template #noLangs><p class="text-gray-500">No languages added.</p></ng-template>
      </section>

      <!-- Missions / Projects -->
      <section>
        <h3 class="section-title">Missions / Projects</h3>
        <div *ngIf="experience?.missionProjectDTOs?.length; else noMissions">
          <div *ngFor="let m of experience.missionProjectDTOs" class="review-card">
            <p><span class="label">Title:</span> {{ m.titleMission }}</p>
            <p><span class="label">Dates:</span> {{ m.startDate }} → {{ m.endDate }}</p>
            <p><span class="label">Tools:</span> {{ m.techniqueTools }}</p>
            <p><span class="label">Description:</span> {{ m.description }}</p>
          </div>
        </div>
        <ng-template #noMissions><p class="text-gray-500">No missions/projects added.</p></ng-template>
      </section>

      <!-- Actions -->
      <div class="flex justify-end gap-4 mt-8">
        <button type="button" (click)="goBack.emit()" class="px-4 py-2 rounded bg-gray-700 hover:bg-gray-600 text-white">Back</button>
        <button type="button" (click)="submit.emit()" class="px-6 py-2 rounded bg-blue-600 hover:bg-blue-500 text-white font-semibold">Submit Application</button>
      </div>
    </div>
  `,
  styles: [`
    .section-title { @apply text-xl font-bold text-blue-300 border-b border-gray-700 pb-2 mb-3; }
    .review-list { @apply space-y-2 text-white; }
    .review-card { @apply bg-gray-800 p-4 rounded-lg border border-gray-700 space-y-1 text-white mt-3; }
    .label { @apply font-semibold text-gray-300; }
  `]
})
export class ReviewComponent {
  @Input() candidate: any;
  @Input() experience: any;

  @Output() goBack = new EventEmitter<void>();
  @Output() submit = new EventEmitter<void>();
}
