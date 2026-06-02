import { NgIf } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, NgIf],
  template: `
    <section class="auth-box">
      <h2>Login</h2>
      <form [formGroup]="form" (ngSubmit)="submit()">
        <label>Email<input type="email" formControlName="email" /></label>
        <label>Password<input type="password" formControlName="password" /></label>
        <button class="primary" type="submit" [disabled]="form.invalid">Login</button>
      </form>
      <p class="error" *ngIf="error">{{ error }}</p>
      <p>New user? <a routerLink="/register">Create account</a></p>
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
export class LoginComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  error = '';

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  });

  submit(): void {
    this.error = '';
    if (this.form.invalid) {
      return;
    }

    this.authService.login(this.form.getRawValue()).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: (err) => (this.error = err?.error?.message || 'Login failed')
    });
  }
}
