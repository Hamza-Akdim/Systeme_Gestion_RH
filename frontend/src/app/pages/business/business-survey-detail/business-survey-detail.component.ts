import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TabViewModule } from 'primeng/tabview';
import { TagModule } from 'primeng/tag';
import { TableModule } from 'primeng/table';
import { ChartModule } from 'primeng/chart';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BusinessSurvey, SurveyResponse } from '../../../types/business-survey';
import { BusinessService } from '../business.service';
//import { SurveyResponseViewerComponent } from '../survey-response-viewer/survey-response-viewer.component';
import { SatisfactionScoreChartComponent } from '../satisfaction-score-chart/satisfaction-score-chart.component';

@Component({
  selector: 'app-business-survey-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ButtonModule,
    CardModule,
    TabViewModule,
    TagModule,
    TableModule,
    ChartModule,
    ToastModule,
    ConfirmDialogModule,
    //SurveyResponseViewerComponent,
    SatisfactionScoreChartComponent
  ],
  providers: [MessageService, ConfirmationService],
  templateUrl: './business-survey-detail.component.html',
  styleUrls: ['./business-survey-detail.component.scss']
})
export class BusinessSurveyDetailComponent implements OnInit {
  survey: BusinessSurvey = {};
  responses: SurveyResponse[] = [];
  loading: boolean = true;

  constructor(
    private businessService: BusinessService,
    private route: ActivatedRoute,
    private router: Router,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.loadSurvey(id);
      }
    });
  }

  loadSurvey(id: string) {
    this.loading = true;
    this.businessService.getSurvey(id).then(data => {
      this.survey = data;
      this.loadResponses(id);
    });
  }

  loadResponses(surveyId: string) {
    this.businessService.getSurveyResponses(surveyId).then(data => {
      this.responses = data;
      this.loading = false;
    });
  }

  deleteSurvey() {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer cette enquête ?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (this.survey.id) {
          this.businessService.deleteSurvey(this.survey.id).then(() => {
            this.messageService.add({
              severity: 'success',
              summary: 'Succès',
              detail: 'Enquête supprimée',
              life: 3000
            });

            setTimeout(() => {
              this.router.navigate(['/pages/business/surveys']);
            }, 1000);
          });
        }
      }
    });
  }

  viewResponse(response: SurveyResponse) {
    this.router.navigate(['/pages/business/surveys', this.survey.id, 'responses', response.id]);
  }

  getStatusSeverity(status: string): string {
    switch (status) {
      case 'active':
        return 'success';
      case 'draft':
        return 'warning';
      case 'completed':
        return 'info';
      case 'archived':
        return 'secondary';
      default:
        return 'secondary';
    }
  }

  getStatusLabel(status: string): string {
    switch (status) {
      case 'active':
        return 'Actif';
      case 'draft':
        return 'Brouillon';
      case 'completed':
        return 'Terminé';
      case 'archived':
        return 'Archivé';
      default:
        return status;
    }
  }

  getQuestionTypeLabel(type: string): string {
    switch (type) {
      case 'multiple_choice':
        return 'Choix multiple';
      case 'rating':
        return 'Évaluation';
      case 'text':
        return 'Texte';
      case 'boolean':
        return 'Oui/Non';
      default:
        return type;
    }
  }

  getAverageSatisfactionScore(): number {
    if (!this.responses || this.responses.length === 0) {
      return 0;
    }

    const totalScore = this.responses.reduce((sum, response) => {
      return sum + (response.satisfactionScore || 0);
    }, 0);

    return totalScore / this.responses.length;
  }

  getResponseCount(): number {
    return this.responses.length;
  }
}
