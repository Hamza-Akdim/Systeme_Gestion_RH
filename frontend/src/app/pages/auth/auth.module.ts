import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { DropdownModule } from 'primeng/dropdown';
import { DividerModule } from 'primeng/divider';
import { ToastModule } from 'primeng/toast';
import { InputTextarea } from 'primeng/inputtextarea';

import routes from './auth.routes';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ProfileComponent } from './profile/profile.component';


@NgModule({
  declarations: [
    // Nous n'avons pas besoin de déclarer les composants standalone ici

  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    ButtonModule,
    CheckboxModule,
    InputTextModule,
    PasswordModule,
    DropdownModule,
    DividerModule,
    ToastModule,
    InputTextarea,
    // Importer les composants standalone
    LoginComponent,
    RegisterComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
    ProfileComponent
  ]
})
export class AuthModule { }
