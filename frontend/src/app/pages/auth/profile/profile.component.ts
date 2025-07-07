import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextarea } from 'primeng/inputtextarea';
import { PasswordModule } from 'primeng/password';
import { DropdownModule } from 'primeng/dropdown';
import { DividerModule } from 'primeng/divider';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    ButtonModule,
    InputTextModule,
    InputTextarea,
    PasswordModule,
    DropdownModule,
    DividerModule,
    ToastModule
  ],
  providers: [MessageService]
})
export class ProfileComponent implements OnInit {
  @ViewChild('fileInput') fileInput!: ElementRef;
  
  profileForm!: FormGroup;
  isLoading = false;
  profileImageUrl: string | null = null;
  
  departments = [
    { name: 'Ressources Humaines', code: 'HR' },
    { name: 'Technologie', code: 'TECH' },
    { name: 'Finance', code: 'FIN' },
    { name: 'Marketing', code: 'MKT' },
    { name: 'Opérations', code: 'OPS' }
  ];
  
  countries = [
    { name: 'France', code: 'FR' },
    { name: 'Belgique', code: 'BE' },
    { name: 'Suisse', code: 'CH' },
    { name: 'Canada', code: 'CA' },
    { name: 'Maroc', code: 'MA' },
    { name: 'Sénégal', code: 'SN' },
    { name: 'Côte d\'Ivoire', code: 'CI' }
  ];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadUserProfile();
  }

  initForm(): void {
    this.profileForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: [''],
      jobTitle: [''],
      department: [''],
      address: [''],
      city: [''],
      postalCode: [''],
      country: [''],
      biography: [''],
      currentPassword: [''],
      newPassword: ['', [Validators.minLength(8)]],
      confirmPassword: ['']
    }, {
      validators: this.passwordMatchValidator
    });
  }

  passwordMatchValidator(form: FormGroup) {
    const newPassword = form.get('newPassword')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;

    if (newPassword && confirmPassword && newPassword !== confirmPassword) {
      return { passwordMismatch: true };
    }
    
    return null;
  }

  loadUserProfile(): void {
    // Simuler le chargement des données utilisateur depuis une API
    setTimeout(() => {
      const userData = {
        firstName: 'Jean',
        lastName: 'Dupont',
        email: 'jean.dupont@example.com',
        phoneNumber: '+33 6 12 34 56 78',
        jobTitle: 'Développeur Full Stack',
        department: 'TECH',
        address: '123 Rue de la République',
        city: 'Paris',
        postalCode: '75001',
        country: 'FR',
        biography: 'Développeur passionné avec plus de 5 ans d\'expérience en développement web et mobile.'
      };
      
      this.profileForm.patchValue(userData);
    }, 500);
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      
      // Vérifier le type et la taille du fichier
      if (!file.type.match(/image\/*/) || file.size > 5000000) {
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Veuillez sélectionner une image valide (max 5 Mo)',
          life: 3000
        });
        return;
      }
      
      // Créer une URL pour l'aperçu de l'image
      const reader = new FileReader();
      reader.onload = (e) => {
        this.profileImageUrl = e.target?.result as string;
      };
      reader.readAsDataURL(file);
      
      // Dans un cas réel, vous téléchargeriez l'image vers le serveur ici
    }
  }

  onSubmit(): void {
    if (this.profileForm.invalid) {
      return;
    }

    this.isLoading = true;

    // Simuler un appel API pour la mise à jour du profil
    setTimeout(() => {
      this.messageService.add({
        severity: 'success',
        summary: 'Succès',
        detail: 'Votre profil a été mis à jour avec succès.',
        life: 3000
      });
      
      this.isLoading = false;
    }, 1500);
  }

  resetForm(): void {
    this.loadUserProfile();
    this.profileForm.get('currentPassword')?.reset();
    this.profileForm.get('newPassword')?.reset();
    this.profileForm.get('confirmPassword')?.reset();
  }
}
