import { Component, OnInit, ViewChild,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Table, TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { TagModule } from 'primeng/tag';
import { ToolbarModule } from 'primeng/toolbar';
import { RatingModule } from 'primeng/rating';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { Prospect } from '../../../types/prospect';
import { BusinessService } from '../business.service';
import { ProspectStatusFilterComponent } from '../prospect-status-filter/prospect-status-filter.component';

@Component({
  selector: 'app-prospect-list',
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
    RatingModule,
    ToastModule,
    ConfirmDialogModule,
    ProspectStatusFilterComponent,
    ToolbarModule
  ],
  providers: [MessageService, ConfirmationService],
  templateUrl: './prospect-list.component.html',
  styleUrls: ['./prospect-list.component.scss'],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProspectListComponent implements OnInit {
  prospects: Prospect[] = [];
  selectedProspects: Prospect[] = [];
  loading: boolean = true;
  totalRecords: number = 0;

  filters = {
    status: '',
    search: '',
    assignedTo: ''
  };

  assignedToOptions: any[] = [];

  @ViewChild('dt') dt!: Table;

  constructor(
    private businessService: BusinessService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    this.loadProspects();
    this.initFilters();
  }

  loadProspects() {
    this.loading = true;
    this.businessService.getProspects().then(data => {
      this.prospects = data;
      this.totalRecords = data.length;
      this.loading = false;
    });
  }

  initFilters() {
    // Extraire les assignedTo uniques pour le filtre
    this.businessService.getProspects().then(data => {
      const assignedToSet = new Set<string>();
      data.forEach(prospect => {
        if (prospect.assignedTo) {
          assignedToSet.add(prospect.assignedTo);
        }
      });

      this.assignedToOptions = Array.from(assignedToSet).map(name => ({
        label: name,
        value: name
      }));
    });
  }

  onStatusChange(status: string) {
    this.filters.status = status;
    this.applyFilters();
  }

  applyFilters() {
    this.dt.filter(this.filters.search, 'global', 'contains');

    if (this.filters.status) {
      this.dt.filter(this.filters.status, 'status', 'equals');
    }

    if (this.filters.assignedTo) {
      this.dt.filter(this.filters.assignedTo, 'assignedTo', 'equals');
    }
  }

  clearFilters() {
    this.filters = {
      status: '',
      search: '',
      assignedTo: ''
    };
    this.dt.clear();
  }

  deleteProspect(prospect: Prospect) {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer ce prospect ?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (prospect.id) {
          this.businessService.deleteProspect(prospect.id).then(() => {
            this.prospects = this.prospects.filter(p => p.id !== prospect.id);
            this.messageService.add({
              severity: 'success',
              summary: 'Succès',
              detail: 'Prospect supprimé',
              life: 3000
            });
          });
        }
      }
    });
  }

  deleteSelectedProspects() {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer les prospects sélectionnés ?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        const deletePromises = this.selectedProspects
          .filter(prospect => prospect.id)
          .map(prospect => this.businessService.deleteProspect(prospect.id!));

        Promise.all(deletePromises).then(() => {
          const deletedIds = this.selectedProspects.map(p => p.id);
          this.prospects = this.prospects.filter(p => !deletedIds.includes(p.id));
          this.selectedProspects = [];

          this.messageService.add({
            severity: 'success',
            summary: 'Succès',
            detail: 'Prospects supprimés',
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
      case 'new':
        return 'info';
      case 'inactive':
        return 'warning';
      default:
        return 'secondary';
    }
  }

  getStatusLabel(status: string): string {
    switch (status) {
      case 'active':
        return 'Actif';
      case 'new':
        return 'Nouveau';
      case 'inactive':
        return 'Inactif';
      default:
        return status;
    }
  }

  getSourceLabel(source: string): string {
    switch (source) {
      case 'website':
        return 'Site web';
      case 'referral':
        return 'Recommandation';
      case 'conference':
        return 'Conférence';
      case 'social':
        return 'Réseaux sociaux';
      case 'email':
        return 'Email';
      case 'other':
        return 'Autre';
      default:
        return source;
    }
  }
}
