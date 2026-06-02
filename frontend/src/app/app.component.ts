import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth.service';
import { TokenService } from './services/token.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, NgIf],
  template: `
    <main class="container">
      <section class="card app-shell">
        <header class="topbar">
          <div>
            <h1>Samaira's Diet Chart</h1>
            <p>Morning to night planner with PDF and Excel actions</p>
          </div>
          <nav>
            <a routerLink="/dashboard">Dashboard</a>
            <a routerLink="/login" *ngIf="!isLoggedIn()">Login</a>
            <a routerLink="/register" *ngIf="!isLoggedIn()">Register</a>
            <button class="secondary" *ngIf="isLoggedIn()" (click)="logout()">Logout</button>
          </nav>
        </header>
        <router-outlet></router-outlet>
      </section>
    </main>
  `,
  styles: [
    `
      .app-shell {
        margin: 1rem auto;
      }

      .topbar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        gap: 1rem;
        border-bottom: 1px solid #ead9c2;
        padding-bottom: 1rem;
        margin-bottom: 1rem;
      }

      .topbar h1 {
        margin: 0;
      }

      .topbar p {
        margin: 0.25rem 0 0;
        color: #5c6b66;
      }

      nav {
        display: flex;
        align-items: center;
        gap: 0.75rem;
      }

      a {
        text-decoration: none;
        font-weight: 700;
        color: #214a39;
      }

      @media (max-width: 800px) {
        .topbar {
          flex-direction: column;
          align-items: flex-start;
        }
      }
    `
  ]
})
export class AppComponent {
  constructor(
    private tokenService: TokenService,
    private authService: AuthService,
    private router: Router
  ) {}

  isLoggedIn(): boolean {
    return this.tokenService.isLoggedIn();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
