import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { InputSwitchModule } from 'primeng/inputswitch';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { CardModule } from 'primeng/card';
import { DividerModule } from 'primeng/divider';
import { PasswordModule } from 'primeng/password';

interface User {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  username?: string;
  password?: string;
  phoneNumber?: string;
  profilePictureUrl?: string;
  jobTitle?: string;
  department?: string;
  role: string;
  active: boolean;
}

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    ButtonModule,
    InputTextModule,
    DropdownModule,
    InputSwitchModule,
    ToastModule,
    CardModule,
    DividerModule,
    PasswordModule
  ],
  providers: [MessageService],
  template: `
    <div class="card">
      <p-toast></p-toast>
      
      <div class="card">
        <h5>{{editMode ? 'Modifier l\'utilisateur' : 'Nouvel utilisateur'}}</h5>
        <p-card>
          <form [formGroup]="userForm" (ngSubmit)="saveUser()">
            <div class="grid">
              <div class="col-12 md:col-6">
                <div class="field">
                  <label for="firstName">Prénom</label>
                  <input id="firstName" type="text" pInputText class="w-full" formControlName="firstName" />
                  <small *ngIf="userForm.get('firstName')?.invalid && userForm.get('firstName')?.touched" class="p-error">
                    Le prénom est requis.
                  </small>
                </div>
              </div>
              
              <div class="col-12 md:col-6">
                <div class="field">
                  <label for="lastName">Nom</label>
                  <input id="lastName" type="text" pInputText class="w-full" formControlName="lastName" />
                  <small *ngIf="userForm.get('lastName')?.invalid && userForm.get('lastName')?.touched" class="p-error">
                    Le nom est requis.
                  </small>
                </div>
              </div>
              
              <div class="col-12 md:col-6">
                <div class="field">
                  <label for="email">Email</label>
                  <input id="email" type="email" pInputText class="w-full" formControlName="email" />
                  <small *ngIf="userForm.get('email')?.invalid && userForm.get('email')?.touched" class="p-error">
                    Un email valide est requis.
                  </small>
                </div>
              </div>
              
              <div class="col-12 md:col-6">
                <div class="field">
                  <label for="username">Nom d'utilisateur</label>
                  <input id="username" type="text" pInputText class="w-full" formControlName="username" />
                </div>
              </div>
              
              <div class="col-12 md:col-6" *ngIf="!editMode">
                <div class="field">
                  <label for="password">Mot de passe</label>
                  <p-password id="password" [toggleMask]="true" formControlName="password" [feedback]="true" [style]="{'width':'100%'}" [inputStyle]="{'width':'100%'}"></p-password>
                  <small *ngIf="userForm.get('password')?.invalid && userForm.get('password')?.touched" class="p-error">
                    Le mot de passe est requis (minimum 8 caractères).
                  </small>
                </div>
              </div>
              
              <div class="col-12 md:col-6">
                <div class="field">
                  <label for="phoneNumber">Téléphone</label>
                  <input id="phoneNumber" type="text" pInputText class="w-full" formControlName="phoneNumber" />
                </div>
              </div>
              
              <div class="col-12 md:col-6">
                <div class="field">
                  <label for="jobTitle">Titre du poste</label>
                  <input id="jobTitle" type="text" pInputText class="w-full" formControlName="jobTitle" />
                </div>
              </div>
              
              <div class="col-12 md:col-6">
                <div class="field">
                  <label for="department">Département</label>
                  <input id="department" type="text" pInputText class="w-full" formControlName="department" />
                </div>
              </div>
              
              <div class="col-12 md:col-6">
                <div class="field">
                  <label for="role">Rôle</label>
                  <p-dropdown id="role" [options]="roles" formControlName="role" placeholder="Sélectionner un rôle" optionLabel="label" optionValue="value" [style]="{'width':'100%'}"></p-dropdown>
                  <small *ngIf="userForm.get('role')?.invalid && userForm.get('role')?.touched" class="p-error">
                    Le rôle est requis.
                  </small>
                </div>
              </div>
              
              <div class="col-12 md:col-6">
                <div class="field">
                  <label for="active" class="block mb-2">Statut</label>
                  <div class="flex align-items-center">
                    <p-inputSwitch id="active" formControlName="active"></p-inputSwitch>
                    <label for="active" class="ml-2">{{userForm.get('active')?.value ? 'Actif' : 'Inactif'}}</label>
                  </div>
                </div>
              </div>
            </div>
            
            <p-divider></p-divider>
            
            <div class="flex justify-content-end">
              <button pButton pRipple label="Annuler" icon="pi pi-times" class="p-button-text" type="button" (click)="cancel()"></button>
              <button pButton pRipple label="Enregistrer" icon="pi pi-check" class="p-button-primary ml-2" type="submit" [disabled]="userForm.invalid"></button>
            </div>
          </form>
        </p-card>
      </div>
    </div>
  `
})
export class UserFormComponent implements OnInit {
  userForm!: FormGroup;
  editMode = false;
  userId: number | null = null;
  
  roles = [
    { label: 'Administrateur', value: 'ADMIN' },
    { label: 'Ressources Humaines', value: 'HR' },
    { label: 'Manager', value: 'MANAGER' },
    { label: 'Consultant', value: 'CONSULTANT' }
  ];

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
        this.userId = +params['id'];
        this.loadUserData(this.userId);
      }
    });
  }

  initForm() {
    this.userForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      username: [''],
      password: ['', this.editMode ? [] : [Validators.required, Validators.minLength(8)]],
      phoneNumber: [''],
      jobTitle: [''],
      department: [''],
      role: ['', Validators.required],
      active: [true]
    });
  }

  loadUserData(userId: number) {
    // Simuler le chargement des données utilisateur - à remplacer par un appel API
    const user = {
      id: userId,
      firstName: 'Jean',
      lastName: 'Dupont',
      email: 'jean.dupont@example.com',
      username: 'jdupont',
      phoneNumber: '0123456789',
      jobTitle: 'Développeur Senior',
      department: 'IT',
      role: 'ADMIN',
      active: true
    };
    
    // Remplir le formulaire avec les données
    this.userForm.patchValue(user);
    
    // Désactiver le champ mot de passe en mode édition
    if (this.editMode) {
      this.userForm.get('password')?.clearValidators();
      this.userForm.get('password')?.updateValueAndValidity();
    }
  }

  saveUser() {
    if (this.userForm.invalid) {
      return;
    }
    
    const userData = this.userForm.value;
    
    // Simuler la sauvegarde - à remplacer par un appel API
    setTimeout(() => {
      this.messageService.add({
        severity: 'success',
        summary: 'Succès',
        detail: `Utilisateur ${this.editMode ? 'modifié' : 'créé'} avec succès`,
        life: 3000
      });
      
      // Rediriger vers la liste des utilisateurs
      this.router.navigate(['/admin/users']);
    }, 1000);
  }

  cancel() {
    this.router.navigate(['/admin/users']);
  }
}
