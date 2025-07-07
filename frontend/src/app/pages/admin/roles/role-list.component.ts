import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { InputTextModule } from 'primeng/inputtext';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService, MessageService } from 'primeng/api';

interface Role {
  id?: number;
  name: string;
  description: string;
  permissions: string[];
  createdAt?: Date;
}

@Component({
  selector: 'app-role-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ButtonModule,
    TableModule,
    ToastModule,
    ToolbarModule,
    InputTextModule,
    ConfirmDialogModule
  ],
  providers: [MessageService, ConfirmationService],
  template: `
    <div class="card">
      <p-toast></p-toast>
      <p-confirmDialog [style]="{width: '450px'}"></p-confirmDialog>
      
      <div class="card">
        <h5>Gestion des Rôles</h5>
        <p-toolbar styleClass="mb-4">
          <ng-template pTemplate="left">
            <div class="my-2">
              <button pButton pRipple label="Nouveau Rôle" icon="pi pi-plus" class="p-button-success mr-2" routerLink="new"></button>
              <button pButton pRipple label="Supprimer" icon="pi pi-trash" class="p-button-danger" (click)="deleteSelectedRoles()" [disabled]="!selectedRoles || !selectedRoles.length"></button>
            </div>
          </ng-template>

          <ng-template pTemplate="right">
            <div class="flex flex-wrap gap-2">
              <span class="p-input-icon-left">
                <i class="pi pi-search"></i>
                <input pInputText type="text" placeholder="Rechercher..." (input)="onGlobalFilter($event)" />
              </span>
            </div>
          </ng-template>
        </p-toolbar>

        <p-table 
          #dt 
          [value]="roles" 
          [rows]="10" 
          [paginator]="true" 
          [globalFilterFields]="['name', 'description']"
          [tableStyle]="{'min-width': '75rem'}"
          [(selection)]="selectedRoles" 
          [rowHover]="true" 
          dataKey="id"
          [showCurrentPageReport]="true" 
          [rowsPerPageOptions]="[10, 25, 50]"
          currentPageReportTemplate="Affichage de {first} à {last} sur {totalRecords} rôles">
          
          <ng-template pTemplate="header">
            <tr>
              <th style="width: 4rem">
                <p-tableHeaderCheckbox></p-tableHeaderCheckbox>
              </th>
              <th pSortableColumn="name">Nom <p-sortIcon field="name"></p-sortIcon></th>
              <th pSortableColumn="description">Description <p-sortIcon field="description"></p-sortIcon></th>
              <th>Permissions</th>
              <th></th>
            </tr>
          </ng-template>
          <ng-template pTemplate="body" let-role>
            <tr>
              <td>
                <p-tableCheckbox [value]="role"></p-tableCheckbox>
              </td>
              <td>{{role.name}}</td>
              <td>{{role.description}}</td>
              <td>
                <div class="flex flex-wrap">
                  <span *ngFor="let permission of role.permissions" class="permission-badge mr-2 mb-1">
                    {{permission}}
                  </span>
                </div>
              </td>
              <td>
                <div class="flex">
                  <button pButton pRipple icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" (click)="editRole(role)"></button>
                  <button pButton pRipple icon="pi pi-trash" class="p-button-rounded p-button-warning" (click)="confirmDelete(role)"></button>
                </div>
              </td>
            </tr>
          </ng-template>
          <ng-template pTemplate="summary">
            <div class="flex align-items-center justify-content-between">
              Au total il y a {{roles ? roles.length : 0 }} rôles.
            </div>
          </ng-template>
        </p-table>
      </div>
    </div>
  `,
  styles: [`
    .permission-badge {
      border-radius: 2px;
      padding: .25em .5rem;
      font-size: 12px;
      letter-spacing: .3px;
      background-color: #EEF2FF;
      color: #4F46E5;
    }
  `]
})
export class RoleListComponent implements OnInit {
  roles: Role[] = [];
  selectedRoles: Role[] = [];

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    // Données de test - à remplacer par un appel API
    this.roles = [
      {
        id: 1,
        name: 'ADMIN',
        description: 'Administrateur système avec accès complet',
        permissions: ['CREATE_USER', 'READ_USER', 'UPDATE_USER', 'DELETE_USER', 'MANAGE_ROLES', 'MANAGE_SETTINGS'],
        createdAt: new Date('2023-01-10')
      },
      {
        id: 2,
        name: 'HR',
        description: 'Ressources Humaines avec accès aux modules RH',
        permissions: ['READ_USER', 'CREATE_CANDIDATE', 'READ_CANDIDATE', 'UPDATE_CANDIDATE', 'MANAGE_EVALUATION'],
        createdAt: new Date('2023-01-15')
      },
      {
        id: 3,
        name: 'MANAGER',
        description: 'Manager avec accès aux tableaux de bord et missions',
        permissions: ['READ_USER', 'MANAGE_MISSION', 'READ_EVALUATION', 'UPDATE_EVALUATION'],
        createdAt: new Date('2023-01-20')
      },
      {
        id: 4,
        name: 'CONSULTANT',
        description: 'Consultant avec accès limité à son profil',
        permissions: ['READ_PROFILE', 'UPDATE_PROFILE', 'READ_MISSION', 'MANAGE_TIMESHEET'],
        createdAt: new Date('2023-01-25')
      }
    ];
  }

  onGlobalFilter(event: Event) {
    const element = event.target as HTMLInputElement;
    // Implémentation du filtre global - à compléter
  }

  editRole(role: Role) {
    // Navigation vers le formulaire d'édition
    // À implémenter avec le Router
  }

  confirmDelete(role: Role) {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer ce rôle ?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // Suppression du rôle - à implémenter avec un appel API
        this.roles = this.roles.filter(r => r.id !== role.id);
        this.messageService.add({
          severity: 'success',
          summary: 'Succès',
          detail: 'Rôle supprimé',
          life: 3000
        });
      }
    });
  }

  deleteSelectedRoles() {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer les rôles sélectionnés ?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // Suppression des rôles sélectionnés - à implémenter avec un appel API
        this.roles = this.roles.filter(r => !this.selectedRoles.includes(r));
        this.selectedRoles = [];
        this.messageService.add({
          severity: 'success',
          summary: 'Succès',
          detail: 'Rôles supprimés',
          life: 3000
        });
      }
    });
  }
}
