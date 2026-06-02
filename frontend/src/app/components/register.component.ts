import { NgIf } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, NgIf],
  template: `
    <section class="auth-box">
      <h2>Create Account</h2>
      <form [formGroup]="form" (ngSubmit)="submit()">
        <label>Full Name<input type="text" formControlName="fullName" /></label>
        <label>Email<input type="email" formControlName="email" /></label>
        <label>Password<input type="password" formControlName="password" /></label>
        <button class="primary" type="submit" [disabled]="form.invalid">Register</button>
      </form>
      <p class="error" *ngIf="error">{{ error }}</p>
      <p>Already have an account? <a routerLink="/login">Login</a></p>
    </section>
  `,
  styles: [
    `
      .auth-box {
        max-width: 430px;
        margin: 0 auto;
      }

      form {
        display: grid;
        gap: 0.8rem;
      }

      .error {
        color: #ad2f2f;
        font-weight: 600;
      }
    `
  ]
})
export class RegisterComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  error = '';

  form = this.fb.group({
    fullName: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  submit(): void {
    this.error = '';
    if (this.form.invalid) {
      return;
    }

    this.authService.register(this.form.getRawValue()).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: (err) => (this.error = err?.error?.message || 'Registration failed')
    });
  }
}
