// src/app/pages/profile-cv/components/application-questions.component.ts
import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-application-questions',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="space-y-6 mt-6">
      <div *ngFor="let q of questions; let i = index" class="mb-4">

        <!-- Label -->
        <label class="block text-sm font-medium mb-2">
          {{ q.question }}
          <span *ngIf="q.important === 'Y'" class="text-red-400">*</span>
        </label>

        <!-- Input TEXT -->
        <input *ngIf="q.responseType === 'TEXT'"
               type="text"
               [(ngModel)]="q.answer"
               name="question-{{q.id}}"
               class="w-full rounded-md border border-gray-500 bg-transparent px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500" />

        <!-- SELECT -->
        <select *ngIf="q.responseType === 'SELECT'"
                [(ngModel)]="q.answer"
                name="question-{{q.id}}"
                class="w-full rounded-md border border-gray-500 bg-sitebg px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500">
          <option value="" disabled selected>-- Select an option --</option>
          <option *ngFor="let opt of q.selectOptions" [value]="opt">{{ opt }}</option>
        </select>
      </div>
    </div>
  `
})
export class ApplicationQuestionsComponent {
  @Input() questions: any[] = [];
}
