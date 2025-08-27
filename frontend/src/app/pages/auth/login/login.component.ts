import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { RippleModule } from 'primeng/ripple';
import { AuthService } from '../service/auth.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule, RouterModule, ButtonModule, CheckboxModule, InputTextModule, PasswordModule, RippleModule]
})
export class LoginComponent implements OnInit {
    loginForm!: FormGroup;
    isLoading = false;
    errorMessage = '';

    constructor(
        private fb: FormBuilder,
        private router: Router,
        private authService: AuthService
    ) {}

    ngOnInit(): void {
        this.initForm();
    }

    initForm(): void {
        this.loginForm = this.fb.group({
            username: ['', Validators.required],
            password: ['', Validators.required],
            rememberMe: [false]
        });
    }

    onSubmit(): void {
        if (this.loginForm.invalid) {
            return;
        }

        this.isLoading = true;
        this.errorMessage = '';

        const { username, password } = this.loginForm.value;

        this.authService.login({ username, password }).subscribe({
            next: () => {
                this.isLoading = false;
                console.log(this.authService.getRole())
                if (this.authService.getRole() === 'ADMIN') {
                    this.router.navigate(['/admin/job-offer']);
                }
                if (this.authService.getRole() === 'HR') {
                    this.router.navigate(['/hr/job-offer']);
                }
                if (this.authService.getRole() === 'CONSULTANT') {
                    this.router.navigate(['/']);
                }
            },
            error: (err) => {
                this.isLoading = false;
                this.errorMessage = err.error?.message || 'Identifiants incorrects';
            }
        });
    }
}
