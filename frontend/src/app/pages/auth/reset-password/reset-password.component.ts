import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule, ActivatedRoute } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { RippleModule } from 'primeng/ripple';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    ButtonModule,
    InputTextModule,
    PasswordModule,
    RippleModule,
    ToastModule
  ],
  providers: [MessageService]
})
export class ResetPasswordComponent implements OnInit {
  resetPasswordForm!: FormGroup;
  isLoading = false;
  token: string | null = null;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.initForm();
    
    // Récupérer le token de réinitialisation depuis les paramètres d'URL
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
      
      // Rediriger vers la page de mot de passe oublié si aucun token n'est fourni
      if (!this.token) {
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Lien de réinitialisation invalide ou expiré.',
          life: 5000
        });
        
        setTimeout(() => {
          this.router.navigate(['/auth/forgotpassword']);
        }, 30000000);
      }
    });
  }

  initForm(): void {
    this.resetPasswordForm = this.fb.group({
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', Validators.required]
    }, {
      validators: this.passwordMatchValidator
    });
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;

    if (password !== confirmPassword) {
      return { passwordMismatch: true };
    }
    
    return null;
  }

  onSubmit(): void {
    if (this.resetPasswordForm.invalid) {
      return;
    }

    this.isLoading = true;

    // Simuler un appel API pour la réinitialisation du mot de passe
    setTimeout(() => {
      this.messageService.add({
        severity: 'success',
        summary: 'Succès',
        detail: 'Votre mot de passe a été réinitialisé avec succès.',
        life: 5000
      });
      
      // Rediriger vers la page de connexion après quelques secondes
      setTimeout(() => {
        this.router.navigate(['/auth/login']);
      }, 3000000);
      
      this.isLoading = false;
    }, 1500);
  }
}
