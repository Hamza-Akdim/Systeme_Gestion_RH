import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Table, TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BusinessSurvey } from '../../../types/business-survey';
import { BusinessService } from '../business.service';

@Component({
  selector: 'app-business-survey-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    TableModule,
    ButtonModule,
    InputTextModule,
    DropdownModule,
    TagModule,
    ToastModule,
    ConfirmDialogModule
  ],
  providers: [MessageService, ConfirmationService],
  templateUrl: './business-survey-list.component.html',
  styleUrls: ['./business-survey-list.component.scss']
})
export class BusinessSurveyListComponent implements OnInit {
  surveys: BusinessSurvey[] = [];
  selectedSurveys: BusinessSurvey[] = [];
  loading: boolean = true;
  totalRecords: number = 0;
  
  filters = {
    status: '',
    search: ''
  };
  
  statusOptions: any[] = [
    { label: 'Tous', value: '' },
    { label: 'Actif', value: 'active' },
    { label: 'Brouillon', value: 'draft' },
    { label: 'Terminé', value: 'completed' },
    { label: 'Archivé', value: 'archived' }
  ];
  
  @ViewChild('dt') dt!: Table;

  constructor(
    private businessService: BusinessService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    this.loadSurveys();
  }

  loadSurveys() {
    this.loading = true;
    this.businessService.getSurveys().then(data => {
      this.surveys = data;
      this.totalRecords = data.length;
      this.loading = false;
    });
  }

  applyFilters() {
    this.dt.filter(this.filters.search, 'global', 'contains');
    
    if (this.filters.status) {
      this.dt.filter(this.filters.status, 'status', 'equals');
    }
  }

  clearFilters() {
    this.filters = {
      status: '',
      search: ''
    };
    this.dt.clear();
  }

  deleteSurvey(survey: BusinessSurvey) {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer cette enquête ?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (survey.id) {
          this.businessService.deleteSurvey(survey.id).then(() => {
            this.surveys = this.surveys.filter(s => s.id !== survey.id);
            this.messageService.add({
              severity: 'success',
              summary: 'Succès',
              detail: 'Enquête supprimée',
              life: 3000
            });
          });
        }
      }
    });
  }

  deleteSelectedSurveys() {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer les enquêtes sélectionnées ?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        const deletePromises = this.selectedSurveys
          .filter(survey => survey.id)
          .map(survey => this.businessService.deleteSurvey(survey.id!));
        
        Promise.all(deletePromises).then(() => {
          const deletedIds = this.selectedSurveys.map(s => s.id);
          this.surveys = this.surveys.filter(s => !deletedIds.includes(s.id));
          this.selectedSurveys = [];
          
          this.messageService.add({
            severity: 'success',
            summary: 'Succès',
            detail: 'Enquêtes supprimées',
            life: 3000
          });
        });
      }
    });
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

  getResponseCount(survey: BusinessSurvey): number {
    return survey.responses?.length || 0;
  }
}
