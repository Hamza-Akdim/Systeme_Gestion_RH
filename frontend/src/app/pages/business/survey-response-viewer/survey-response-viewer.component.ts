import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { AccordionModule } from 'primeng/accordion';
import { SurveyResponse, SurveyQuestion } from '../../../types/business-survey';

@Component({
  selector: 'app-survey-response-viewer',
  standalone: true,
  imports: [
    CommonModule,
    CardModule,
    AccordionModule
  ],
  templateUrl: './survey-response-viewer.component.html',
  styleUrls: ['./survey-response-viewer.component.scss']
})
export class SurveyResponseViewerComponent {
  @Input() response: SurveyResponse | null = null;
  @Input() questions: SurveyQuestion[] = [];
  
  getQuestionText(questionId: string): string {
    const question = this.questions.find(q => q.id === questionId);
    return question ? question.text || '' : 'Question inconnue';
  }
  
  getQuestionType(questionId: string): string {
    const question = this.questions.find(q => q.id === questionId);
    return question ? question.type || 'text' : 'text';
  }
  
  formatAnswerValue(value: any, type: string): string {
    if (value === undefined || value === null) {
      return 'Pas de réponse';
    }
    
    switch (type) {
      case 'boolean':
        return value ? 'Oui' : 'Non';
      case 'rating':
        return `${value}/5`;
      default:
        return value.toString();
    }
  }
}
