import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-review',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="space-y-8 mt-4">

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

      <!-- Application Questions -->
      <section>
        <h3 class="section-title">Application Questions</h3>
        <div *ngIf="questions?.length; else noQuestions">
          <div *ngFor="let q of questions" class="review-card">
            <p><span class="label">{{ q.question }}</span></p>
            <p>{{ q.answer || 'No Response' }}</p>
          </div>
        </div>
        <ng-template #noQuestions><p class="text-gray-500">No questions answered.</p></ng-template>
      </section>

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
  @Input() questions: any[] = [];   // <-- new input for ApplicationQuestions
}
