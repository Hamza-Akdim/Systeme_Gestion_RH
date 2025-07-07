import { Component, OnInit, ViewChild, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TagModule } from 'primeng/tag';
import { RatingModule } from 'primeng/rating';
import { FormsModule } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CandidateService } from '../candidate.service';
import { Candidate } from '../../../types/candidate';
import { Table } from 'primeng/table';
import { CandidateStatusFilterComponent } from '../candidate-status-filter/candidate-status-filter.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-candidate-list',
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
    RatingModule,
    FormsModule,
    CandidateStatusFilterComponent
  ],
  providers: [MessageService, ConfirmationService],
  templateUrl: './candidate-list.component.html',
  styleUrls: ['./candidate-list.component.scss'],
  schemas: [CUSTOM_ELEMENTS_SCHEMA] 
})
export class CandidateListComponent implements OnInit {
  candidates: Candidate[] = [];
  selectedCandidates: Candidate[] = [];
  candidate: Candidate = {};
  deleteCandidateDialog: boolean = false;
  deleteCandidatesDialog: boolean = false;
  submitted: boolean = false;
  loading: boolean = true;
  statuses: any[] = [];
  globalFilterValue: string = '';



  
  @ViewChild('dt') dt!: Table;

  constructor(
    private candidateService: CandidateService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router 
  ) {}

  ngOnInit() {
    this.loadCandidates();
    
    this.statuses = [
      { label: 'Disponible', value: 'available' },
      { label: 'En entretien', value: 'interviewing' },
      { label: 'Embauché', value: 'hired' },
      { label: 'Rejeté', value: 'rejected' },
      { label: 'En attente', value: 'pending' }
    ];
  }

  loadCandidates() {
    this.loading = true;
    this.candidateService.getCandidates().then(data => {
      this.candidates = data;
      this.loading = false;
    });
  }

  openNew() {
    this.router.navigate(['/pages/candidate/new']);
    //this.candidate = {}; this.submitted = false;

  }

  deleteSelectedCandidates() {
    this.deleteCandidatesDialog = true;
  }

  editCandidate(candidate: Candidate) {
    // Navigation will be handled by router link in template
  }

  deleteCandidate(candidate: Candidate) {
    this.deleteCandidateDialog = true;
    this.candidate = { ...candidate };
  }

  confirmDeleteSelected() {
    this.deleteCandidatesDialog = false;
    
    for (let candidate of this.selectedCandidates) {
      if (candidate.id) {
        this.candidateService.deleteCandidate(candidate.id);
      }
    }
    
    this.loadCandidates();
    this.selectedCandidates = [];
    
    this.messageService.add({
      severity: 'success',
      summary: 'Succès',
      detail: 'Candidats supprimés',
      life: 3000
    });
  }

  confirmDelete() {
    this.deleteCandidateDialog = false;
    
    if (this.candidate.id) {
      this.candidateService.deleteCandidate(this.candidate.id).then(() => {
        this.loadCandidates();
        
        this.messageService.add({
          severity: 'success',
          summary: 'Succès',
          detail: 'Candidat supprimé',
          life: 3000
        });
      });
    }
    
    this.candidate = {};
  }

  //onGlobalFilter(table: Table, event: Event) {
    //table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  //}

  onGlobalFilter(table: any, value: string | Event): void {
    const searchTerm = typeof value === 'string' ? value : (value.target as HTMLInputElement).value;
    table.filterGlobal(searchTerm, 'contains');
    this.globalFilterValue = searchTerm;}

  getSeverity(status: string) {
    switch (status) {
      case 'available':
        return 'success';
      case 'interviewing':
        return 'info';
      case 'hired':
        return 'warning';
      case 'rejected':
        return 'danger';
      default:
        return 'secondary';
    }
  }

  getStatusLabel(status: string) {
    const statusObj = this.statuses.find(s => s.value === status);
    return statusObj ? statusObj.label : status;
  }
}
