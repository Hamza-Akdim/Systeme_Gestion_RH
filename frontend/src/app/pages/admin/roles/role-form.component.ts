import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ChipsModule } from 'primeng/chips';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { CardModule } from 'primeng/card';
import { DividerModule } from 'primeng/divider';

interface Role {
  id?: number;
  name: string;
  description: string;
  permissions: string[];
}

@Component({
  selector: 'app-role-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    ButtonModule,
    InputTextModule,
    InputTextareaModule,
    ChipsModule,
    ToastModule,
    CardModule,
    DividerModule
  ],
  providers: [MessageService],
  template: `
    <div class="card">
      <p-toast></p-toast>
      
      <div class="card">
        <h5>{{editMode ? 'Modifier le rôle' : 'Nouveau rôle'}}</h5>
        <p-card>
          <form [formGroup]="roleForm" (ngSubmit)="saveRole()">
            <div class="grid">
              <div class="col-12">
                <div class="field">
                  <label for="name">Nom du rôle</label>
                  <input id="name" type="text" pInputText class="w-full" formControlName="name" />
                  <small *ngIf="roleForm.get('name')?.invalid && roleForm.get('name')?.touched" class="p-error">
                    Le nom du rôle est requis.
                  </small>
                </div>
              </div>
              
              <div class="col-12">
                <div class="field">
                  <label for="description">Description</label>
                  <textarea id="description" pInputTextarea [rows]="3" class="w-full" formControlName="description"></textarea>
                  <small *ngIf="roleForm.get('description')?.invalid && roleForm.get('description')?.touched" class="p-error">
                    La description est requise.
                  </small>
                </div>
              </div>
              
              <div class="col-12">
                <div class="field">
                  <label for="permissions">Permissions (appuyez sur Entrée après chaque permission)</label>
                  <p-chips id="permissions" formControlName="permissions" [style]="{'width':'100%'}" [allowDuplicate]="false"></p-chips>
                  <small *ngIf="roleForm.get('permissions')?.invalid && roleForm.get('permissions')?.touched" class="p-error">
                    Au moins une permission est requise.
                  </small>
                </div>
              </div>
            </div>
            
            <p-divider></p-divider>
            
            <div class="flex justify-content-end">
              <button pButton pRipple label="Annuler" icon="pi pi-times" class="p-button-text" type="button" (click)="cancel()"></button>
              <button pButton pRipple label="Enregistrer" icon="pi pi-check" class="p-button-primary ml-2" type="submit" [disabled]="roleForm.invalid"></button>
            </div>
          </form>
        </p-card>
      </div>
    </div>
  `
})
export class RoleFormComponent implements OnInit {
  roleForm!: FormGroup;
  editMode = false;
  roleId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.initForm();
    
    // Vérifier si on est en mode édition
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.editMode = true;
        this.roleId = +params['id'];
        this.loadRoleData(this.roleId);
      }
    });
  }

  initForm() {
    this.roleForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      permissions: [[], [Validators.required, Validators.minLength(1)]]
    });
  }

  loadRoleData(roleId: number) {
    // Simuler le chargement des données du rôle - à remplacer par un appel API
    const role = {
      id: roleId,
      name: 'ADMIN',
      description: 'Administrateur système avec accès complet',
      permissions: ['CREATE_USER', 'READ_USER', 'UPDATE_USER', 'DELETE_USER', 'MANAGE_ROLES', 'MANAGE_SETTINGS']
    };
    
    // Remplir le formulaire avec les données
    this.roleForm.patchValue(role);
  }

  saveRole() {
    if (this.roleForm.invalid) {
      return;
    }
    
    const roleData = this.roleForm.value;
    
    // Simuler la sauvegarde - à remplacer par un appel API
    setTimeout(() => {
      this.messageService.add({
        severity: 'success',
        summary: 'Succès',
        detail: `Rôle ${this.editMode ? 'modifié' : 'créé'} avec succès`,
        life: 3000
      });
      
      // Rediriger vers la liste des rôles
      this.router.navigate(['/admin/roles']);
    }, 1000);
  }

  cancel() {
    this.router.navigate(['/admin/roles']);
  }
}
