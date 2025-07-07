import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputSwitchModule } from 'primeng/inputswitch';
import { DropdownModule } from 'primeng/dropdown';
import { InputNumberModule } from 'primeng/inputnumber';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { CardModule } from 'primeng/card';
import { DividerModule } from 'primeng/divider';
import { TabViewModule } from 'primeng/tabview';
import { ColorPickerModule } from 'primeng/colorpicker';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonModule,
    InputTextModule,
    InputSwitchModule,
    DropdownModule,
    InputNumberModule,
    ToastModule,
    CardModule,
    DividerModule,
    TabViewModule,
    ColorPickerModule
  ],
  providers: [MessageService],
  template: `
    <div class="card">
      <p-toast></p-toast>
      
      <div class="card">
        <h5>Paramètres Système</h5>
        
        <p-tabView>
          <p-tabPanel header="Général">
            <p-card>
              <form [formGroup]="generalForm" (ngSubmit)="saveGeneralSettings()">
                <div class="grid">
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="companyName">Nom de l'entreprise</label>
                      <input id="companyName" type="text" pInputText class="w-full" formControlName="companyName" />
                    </div>
                  </div>
                  
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="contactEmail">Email de contact</label>
                      <input id="contactEmail" type="email" pInputText class="w-full" formControlName="contactEmail" />
                    </div>
                  </div>
                  
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="language">Langue par défaut</label>
                      <p-dropdown id="language" [options]="languages" formControlName="language" optionLabel="label" optionValue="value" [style]="{'width':'100%'}"></p-dropdown>
                    </div>
                  </div>
                  
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="dateFormat">Format de date</label>
                      <p-dropdown id="dateFormat" [options]="dateFormats" formControlName="dateFormat" optionLabel="label" optionValue="value" [style]="{'width':'100%'}"></p-dropdown>
                    </div>
                  </div>
                  
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="maintenanceMode" class="block mb-2">Mode maintenance</label>
                      <div class="flex align-items-center">
                        <p-inputSwitch id="maintenanceMode" formControlName="maintenanceMode"></p-inputSwitch>
                        <label for="maintenanceMode" class="ml-2">{{generalForm.get('maintenanceMode')?.value ? 'Activé' : 'Désactivé'}}</label>
                      </div>
                    </div>
                  </div>
                </div>
                
                <p-divider></p-divider>
                
                <div class="flex justify-content-end">
                  <button pButton pRipple label="Enregistrer" icon="pi pi-check" class="p-button-primary" type="submit"></button>
                </div>
              </form>
            </p-card>
          </p-tabPanel>
          
          <p-tabPanel header="Apparence">
            <p-card>
              <form [formGroup]="appearanceForm" (ngSubmit)="saveAppearanceSettings()">
                <div class="grid">
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="theme">Thème</label>
                      <p-dropdown id="theme" [options]="themes" formControlName="theme" optionLabel="label" optionValue="value" [style]="{'width':'100%'}"></p-dropdown>
                    </div>
                  </div>
                  
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="darkMode" class="block mb-2">Mode sombre</label>
                      <div class="flex align-items-center">
                        <p-inputSwitch id="darkMode" formControlName="darkMode"></p-inputSwitch>
                        <label for="darkMode" class="ml-2">{{appearanceForm.get('darkMode')?.value ? 'Activé' : 'Désactivé'}}</label>
                      </div>
                    </div>
                  </div>
                  
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="primaryColor">Couleur primaire</label>
                      <p-colorPicker id="primaryColor" formControlName="primaryColor" [style]="{'width':'100%'}"></p-colorPicker>
                    </div>
                  </div>
                  
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="secondaryColor">Couleur secondaire</label>
                      <p-colorPicker id="secondaryColor" formControlName="secondaryColor" [style]="{'width':'100%'}"></p-colorPicker>
                    </div>
                  </div>
                  
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="menuLayout">Disposition du menu</label>
                      <p-dropdown id="menuLayout" [options]="menuLayouts" formControlName="menuLayout" optionLabel="label" optionValue="value" [style]="{'width':'100%'}"></p-dropdown>
                    </div>
                  </div>
                </div>
                
                <p-divider></p-divider>
                
                <div class="flex justify-content-end">
                  <button pButton pRipple label="Enregistrer" icon="pi pi-check" class="p-button-primary" type="submit"></button>
                </div>
              </form>
            </p-card>
          </p-tabPanel>
          
          <p-tabPanel header="Sécurité">
            <p-card>
              <form [formGroup]="securityForm" (ngSubmit)="saveSecuritySettings()">
                <div class="grid">
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="sessionTimeout">Délai d'expiration de session (minutes)</label>
                      <p-inputNumber id="sessionTimeout" formControlName="sessionTimeout" [min]="5" [max]="240" [style]="{'width':'100%'}"></p-inputNumber>
                    </div>
                  </div>
                  
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="maxLoginAttempts">Nombre maximal de tentatives de connexion</label>
                      <p-inputNumber id="maxLoginAttempts" formControlName="maxLoginAttempts" [min]="3" [max]="10" [style]="{'width':'100%'}"></p-inputNumber>
                    </div>
                  </div>
                  
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="passwordExpiration">Expiration du mot de passe (jours)</label>
                      <p-inputNumber id="passwordExpiration" formControlName="passwordExpiration" [min]="0" [max]="365" [style]="{'width':'100%'}"></p-inputNumber>
                    </div>
                  </div>
                  
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="passwordComplexity">Complexité du mot de passe</label>
                      <p-dropdown id="passwordComplexity" [options]="passwordComplexities" formControlName="passwordComplexity" optionLabel="label" optionValue="value" [style]="{'width':'100%'}"></p-dropdown>
                    </div>
                  </div>
                  
                  <div class="col-12 md:col-6">
                    <div class="field">
                      <label for="twoFactorAuth" class="block mb-2">Authentification à deux facteurs</label>
                      <div class="flex align-items-center">
                        <p-inputSwitch id="twoFactorAuth" formControlName="twoFactorAuth"></p-inputSwitch>
                        <label for="twoFactorAuth" class="ml-2">{{securityForm.get('twoFactorAuth')?.value ? 'Activée' : 'Désactivée'}}</label>
                      </div>
                    </div>
                  </div>
                </div>
                
                <p-divider></p-divider>
                
                <div class="flex justify-content-end">
                  <button pButton pRipple label="Enregistrer" icon="pi pi-check" class="p-button-primary" type="submit"></button>
                </div>
              </form>
            </p-card>
          </p-tabPanel>
        </p-tabView>
      </div>
    </div>
  `
})
export class SettingsComponent implements OnInit {
  generalForm!: FormGroup;
  appearanceForm!: FormGroup;
  securityForm!: FormGroup;
  
  languages = [
    { label: 'Français', value: 'fr' },
    { label: 'Anglais', value: 'en' },
    { label: 'Espagnol', value: 'es' },
    { label: 'Allemand', value: 'de' }
  ];
  
  dateFormats = [
    { label: 'DD/MM/YYYY', value: 'DD/MM/YYYY' },
    { label: 'MM/DD/YYYY', value: 'MM/DD/YYYY' },
    { label: 'YYYY-MM-DD', value: 'YYYY-MM-DD' }
  ];
  
  themes = [
    { label: 'Clair', value: 'light' },
    { label: 'Sombre', value: 'dark' },
    { label: 'Bleu', value: 'blue' },
    { label: 'Vert', value: 'green' }
  ];
  
  menuLayouts = [
    { label: 'Statique', value: 'static' },
    { label: 'Overlay', value: 'overlay' },
    { label: 'Slim', value: 'slim' },
    { label: 'Horizontal', value: 'horizontal' }
  ];
  
  passwordComplexities = [
    { label: 'Faible', value: 'low' },
    { label: 'Moyenne', value: 'medium' },
    { label: 'Élevée', value: 'high' }
  ];

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.initForms();
    this.loadSettings();
  }

  initForms() {
    this.generalForm = this.fb.group({
      companyName: ['', Validators.required],
      contactEmail: ['', [Validators.required, Validators.email]],
      language: ['fr', Validators.required],
      dateFormat: ['DD/MM/YYYY', Validators.required],
      maintenanceMode: [false]
    });
    
    this.appearanceForm = this.fb.group({
      theme: ['light', Validators.required],
      darkMode: [false],
      primaryColor: ['#0ea5e9'],
      secondaryColor: ['#4ade80'],
      menuLayout: ['static', Validators.required]
    });
    
    this.securityForm = this.fb.group({
      sessionTimeout: [30, [Validators.required, Validators.min(5), Validators.max(240)]],
      maxLoginAttempts: [5, [Validators.required, Validators.min(3), Validators.max(10)]],
      passwordExpiration: [90, [Validators.required, Validators.min(0), Validators.max(365)]],
      passwordComplexity: ['medium', Validators.required],
      twoFactorAuth: [false]
    });
  }

  loadSettings() {
    // Simuler le chargement des paramètres - à remplacer par un appel API
    const generalSettings = {
      companyName: 'TalentWave',
      contactEmail: 'contact@talentwave.com',
      language: 'fr',
      dateFormat: 'DD/MM/YYYY',
      maintenanceMode: false
    };
    
    const appearanceSettings = {
      theme: 'light',
      darkMode: false,
      primaryColor: '#0ea5e9',
      secondaryColor: '#4ade80',
      menuLayout: 'static'
    };
    
    const securitySettings = {
      sessionTimeout: 30,
      maxLoginAttempts: 5,
      passwordExpiration: 90,
      passwordComplexity: 'medium',
      twoFactorAuth: false
    };
    
    this.generalForm.patchValue(generalSettings);
    this.appearanceForm.patchValue(appearanceSettings);
    this.securityForm.patchValue(securitySettings);
  }

  saveGeneralSettings() {
    if (this.generalForm.invalid) {
      return;
    }
    
    const generalData = this.generalForm.value;
    
    // Simuler la sauvegarde - à remplacer par un appel API
    setTimeout(() => {
      this.messageService.add({
        severity: 'success',
        summary: 'Succès',
        detail: 'Paramètres généraux enregistrés avec succès',
        life: 3000
      });
    }, 1000);
  }

  saveAppearanceSettings() {
    if (this.appearanceForm.invalid) {
      return;
    }
    
    const appearanceData = this.appearanceForm.value;
    
    // Simuler la sauvegarde - à remplacer par un appel API
    setTimeout(() => {
      this.messageService.add({
        severity: 'success',
        summary: 'Succès',
        detail: 'Paramètres d\'apparence enregistrés avec succès',
        life: 3000
      });
    }, 1000);
  }

  saveSecuritySettings() {
    if (this.securityForm.invalid) {
      return;
    }
    
    const securityData = this.securityForm.value;
    
    // Simuler la sauvegarde - à remplacer par un appel API
    setTimeout(() => {
      this.messageService.add({
        severity: 'success',
        summary: 'Succès',
        detail: 'Paramètres de sécurité enregistrés avec succès',
        life: 3000
      });
    }, 1000);
  }
}
