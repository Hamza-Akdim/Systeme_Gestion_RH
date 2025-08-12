import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { RippleModule } from 'primeng/ripple';
import { UserService } from '../service/user.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss'],
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule, RouterModule, ButtonModule, CheckboxModule, InputTextModule, PasswordModule, RippleModule]
})
export class RegisterComponent implements OnInit {
    registerForm!: FormGroup;
    isLoading = false;
    errorMessage: string | null = null;

    constructor(
        private fb: FormBuilder,
        private router: Router,
        private userService: UserService
    ) {}

    ngOnInit(): void {
        this.initForm();
    }

    initForm(): void {
        this.registerForm = this.fb.group(
            {
                firstname: ['', Validators.required],
                lastname: ['', Validators.required],
                email: ['', [Validators.required, Validators.email]],
                password: ['', [Validators.required, Validators.minLength(6)]],
                confirmPassword: ['', Validators.required],
                acceptTerms: [false, Validators.requiredTrue]
            },
            {
                validators: this.passwordMatchValidator
            }
        );
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
        if (this.registerForm.invalid) {
            return;
        }

        this.isLoading = true;
        this.errorMessage = null;

        const { firstname, lastname, email, password } = this.registerForm.value;
        const payload = { firstname, lastname, email, password };

        this.userService.register(payload).subscribe({
            next: () => {
                this.router.navigate(['/auth/login'], { queryParams: { registered: 'true' } });
                this.isLoading = false;
            },
            error: (err: HttpErrorResponse) => {
                this.errorMessage = err.error?.message || 'Registration failed';
                this.isLoading = false;
            }
        });
    }
}
