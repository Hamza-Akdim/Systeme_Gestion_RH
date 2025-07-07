import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TagModule } from 'primeng/tag';
import { FormsModule } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CandidateService } from '../candidate.service';
import { Application } from '../../../types/application';
import { Table } from 'primeng/table';

@Component({
  selector: 'app-application-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ButtonModule,
    TableModule,
    InputTextModule,
    ToastModule,
    ToolbarModule,
    DialogModule,
    ConfirmDialogModule,
    TagModule,
    FormsModule
  ],
  providers: [MessageService, ConfirmationService],
  templateUrl: './application-list.component.html',
  styleUrls: ['./application-list.component.scss']
})
export class ApplicationListComponent implements OnInit {
  applications: Application[] = [];
  selectedApplications: Application[] = [];
  application: Application = {};
  deleteApplicationDialog: boolean = false;
  deleteApplicationsDialog: boolean = false;
  submitted: boolean = false;
  loading: boolean = true;
  statuses: any[] = [];
  candidateId: string | null = null;
  
  @ViewChild('dt') dt!: Table;

  constructor(
    private candidateService: CandidateService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.candidateId = params['candidateId'] || null;
      this.loadApplications();
    });
    
    this.statuses = [
      { label: 'Candidature soumise', value: 'applied' },
      { label: 'Présélection', value: 'screening' },
      { label: 'Entretien', value: 'interview' },
      { label: 'Offre', value: 'offer' },
      { label: 'Acceptée', value: 'accepted' },
      { label: 'Rejetée', value: 'rejected' },
      { label: 'Embauché', value: 'hired' }
    ];
  }

  loadApplications() {
    this.loading = true;
    this.candidateService.getApplications(this.candidateId || undefined).then(data => {
      this.applications = data;
      this.loading = false;
    });
  }

  openNew() {
    this.application = {};
    this.submitted = false;
  }

  deleteSelectedApplications() {
    this.deleteApplicationsDialog = true;
  }

  editApplication(application: Application) {
    // Navigation will be handled by router link in template
  }

  deleteApplication(application: Application) {
    this.deleteApplicationDialog = true;
    this.application = { ...application };
  }

  confirmDeleteSelected() {
    this.deleteApplicationsDialog = false;
    
    for (let application of this.selectedApplications) {
      if (application.id) {
        this.candidateService.deleteApplication(application.id);
      }
    }
    
    this.loadApplications();
    this.selectedApplications = [];
    
    this.messageService.add({
      severity: 'success',
      summary: 'Succès',
      detail: 'Candidatures supprimées',
      life: 3000
    });
  }

  confirmDelete() {
    this.deleteApplicationDialog = false;
    
    if (this.application.id) {
      this.candidateService.deleteApplication(this.application.id).then(() => {
        this.loadApplications();
        
        this.messageService.add({
          severity: 'success',
          summary: 'Succès',
          detail: 'Candidature supprimée',
          life: 3000
        });
      });
    }
    
    this.application = {};
  }

  onGlobalFilter(table: Table, event: Event) {
    table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }

  getSeverity(status: string) {
    switch (status) {
      case 'applied':
        return 'info';
      case 'screening':
        return 'primary';
      case 'interview':
        return 'warning';
      case 'offer':
        return 'success';
      case 'accepted':
        return 'success';
      case 'rejected':
        return 'danger';
      case 'hired':
        return 'success';
      default:
        return 'secondary';
    }
  }
  
  getStatusLabel(status: string) {
    const statusObj = this.statuses.find(s => s.value === status);
    return statusObj ? statusObj.label : status;
  }
}
