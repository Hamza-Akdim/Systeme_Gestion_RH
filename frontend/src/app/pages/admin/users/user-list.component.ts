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

interface User {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  username?: string;
  phoneNumber?: string;
  profilePictureUrl?: string;
  jobTitle?: string;
  department?: string;
  role: string;
  active: boolean;
  createdAt?: Date;
}

@Component({
  selector: 'app-user-list',
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
        <h5>Gestion des Utilisateurs</h5>
        <p-toolbar styleClass="mb-4">
          <ng-template pTemplate="left">
            <div class="my-2">
              <button pButton pRipple label="Nouvel Utilisateur" icon="pi pi-plus" class="p-button-success mr-2" routerLink="new"></button>
              <button pButton pRipple label="Supprimer" icon="pi pi-trash" class="p-button-danger" (click)="deleteSelectedUsers()" [disabled]="!selectedUsers || !selectedUsers.length"></button>
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
          [value]="users" 
          [rows]="10" 
          [paginator]="true" 
          [globalFilterFields]="['firstName', 'lastName', 'email', 'role']"
          [tableStyle]="{'min-width': '75rem'}"
          [(selection)]="selectedUsers" 
          [rowHover]="true" 
          dataKey="id"
          [showCurrentPageReport]="true" 
          [rowsPerPageOptions]="[10, 25, 50]"
          currentPageReportTemplate="Affichage de {first} à {last} sur {totalRecords} utilisateurs">
          
          <ng-template pTemplate="header">
            <tr>
              <th style="width: 4rem">
                <p-tableHeaderCheckbox></p-tableHeaderCheckbox>
              </th>
              <th pSortableColumn="firstName">Prénom <p-sortIcon field="firstName"></p-sortIcon></th>
              <th pSortableColumn="lastName">Nom <p-sortIcon field="lastName"></p-sortIcon></th>
              <th pSortableColumn="email">Email <p-sortIcon field="email"></p-sortIcon></th>
              <th pSortableColumn="role">Rôle <p-sortIcon field="role"></p-sortIcon></th>
              <th pSortableColumn="active">Statut <p-sortIcon field="active"></p-sortIcon></th>
              <th></th>
            </tr>
          </ng-template>
          <ng-template pTemplate="body" let-user>
            <tr>
              <td>
                <p-tableCheckbox [value]="user"></p-tableCheckbox>
              </td>
              <td>{{user.firstName}}</td>
              <td>{{user.lastName}}</td>
              <td>{{user.email}}</td>
              <td>{{user.role}}</td>
              <td>
                <span [class]="'user-badge status-' + (user.active ? 'active' : 'inactive')">
                  {{user.active ? 'Actif' : 'Inactif'}}
                </span>
              </td>
              <td>
                <div class="flex">
                  <button pButton pRipple icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" (click)="editUser(user)"></button>
                  <button pButton pRipple icon="pi pi-trash" class="p-button-rounded p-button-warning" (click)="confirmDelete(user)"></button>
                </div>
              </td>
            </tr>
          </ng-template>
          <ng-template pTemplate="summary">
            <div class="flex align-items-center justify-content-between">
              Au total il y a {{users ? users.length : 0 }} utilisateurs.
            </div>
          </ng-template>
        </p-table>
      </div>
    </div>
  `,
  styles: [`
    :host ::ng-deep .p-dialog .user-image {
      width: 150px;
      margin: 0 auto 2rem auto;
      display: block;
    }

    .user-badge {
      border-radius: 2px;
      padding: .25em .5rem;
      text-transform: uppercase;
      font-weight: 700;
      font-size: 12px;
      letter-spacing: .3px;
    }

    .user-badge.status-active {
      background: #C8E6C9;
      color: #256029;
    }

    .user-badge.status-inactive {
      background: #FFCDD2;
      color: #C63737;
    }
  `]
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  selectedUsers: User[] = [];

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    // Données de test - à remplacer par un appel API
    this.users = [
      {
        id: 1,
        firstName: 'Jean',
        lastName: 'Dupont',
        email: 'jean.dupont@example.com',
        username: 'jdupont',
        role: 'ADMIN',
        active: true,
        createdAt: new Date('2025-01-15')
      },
      {
        id: 2,
        firstName: 'Marie',
        lastName: 'Martin',
        email: 'marie.martin@example.com',
        username: 'mmartin',
        role: 'HR',
        active: true,
        createdAt: new Date('2025-02-20')
      },
      {
        id: 3,
        firstName: 'Pierre',
        lastName: 'Bernard',
        email: 'pierre.bernard@example.com',
        username: 'pbernard',
        role: 'MANAGER',
        active: false,
        createdAt: new Date('2025-03-10')
      },
      {
        id: 4,
        firstName: 'Sophie',
        lastName: 'Petit',
        email: 'sophie.petit@example.com',
        username: 'spetit',
        role: 'CONSULTANT',
        active: true,
        createdAt: new Date('2025-04-05')
      },
      {
        id: 5,
        firstName: 'Thomas',
        lastName: 'Dubois',
        email: 'thomas.dubois@example.com',
        username: 'tdubois',
        role: 'MANAGER',
        active: true,
        createdAt: new Date('2025-05-12')
      }
    ];
  }

  onGlobalFilter(event: Event) {
    const element = event.target as HTMLInputElement;
    // Implémentation du filtre global - à compléter
  }

  editUser(user: User) {
    // Navigation vers le formulaire d'édition
    // À implémenter avec le Router
  }

  confirmDelete(user: User) {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer cet utilisateur ?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // Suppression de l'utilisateur - à implémenter avec un appel API
        this.users = this.users.filter(u => u.id !== user.id);
        this.messageService.add({
          severity: 'success',
          summary: 'Succès',
          detail: 'Utilisateur supprimé',
          life: 3000
        });
      }
    });
  }

  deleteSelectedUsers() {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer les utilisateurs sélectionnés ?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // Suppression des utilisateurs sélectionnés - à implémenter avec un appel API
        this.users = this.users.filter(u => !this.selectedUsers.includes(u));
        this.selectedUsers = [];
        this.messageService.add({
          severity: 'success',
          summary: 'Succès',
          detail: 'Utilisateurs supprimés',
          life: 3000
        });
      }
    });
  }
}
