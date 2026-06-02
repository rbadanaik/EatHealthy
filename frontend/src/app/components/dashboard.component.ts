import { JsonPipe, NgFor, NgIf, TitleCasePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';
import { DietPlanResponse } from '../models/types';
import { DietService } from '../services/diet.service';
import { ExcelService } from '../services/excel.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [NgFor, NgIf, TitleCasePipe, JsonPipe],
  template: `
    <section>
      <div class="toolbar">
        <button class="secondary" (click)="downloadPdf()">Download Diet Chart as PDF</button>
        <button class="secondary" (click)="downloadExcel()">Download Excel</button>
        <label class="upload">
          Import Excel
          <input type="file" accept=".xlsx" (change)="onFileUpload($event)" />
        </label>
      </div>

      <p *ngIf="message" class="message">{{ message }}</p>

      <div class="diet-grid" id="diet-chart">
        <article class="card" *ngFor="let section of sectionKeys()">
          <h3>{{ section }}</h3>
          <ul>
            <li *ngFor="let item of dietPlan[section]">
              <strong>{{ item.mealType | titlecase }}:</strong> {{ item.item }}
            </li>
          </ul>
        </article>
      </div>
    </section>
  `,
  styles: [
    `
      .toolbar {
        display: flex;
        flex-wrap: wrap;
        gap: 0.75rem;
        margin-bottom: 1rem;
      }

      .upload {
        display: inline-flex;
        align-items: center;
        gap: 0.5rem;
        padding: 0.65rem 1rem;
        border-radius: 12px;
        background: #335f49;
        color: #fff;
        font-weight: 700;
        cursor: pointer;
      }

      .upload input {
        display: none;
      }

      .message {
        background: #e5f7ea;
        border: 1px solid #bae7c5;
        padding: 0.6rem;
        border-radius: 10px;
        color: #275834;
      }

      .diet-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
        gap: 0.8rem;
      }

      h3 {
        margin-top: 0;
      }

      ul {
        margin: 0;
        padding-left: 1rem;
      }
    `
  ]
})
export class DashboardComponent implements OnInit {
  dietPlan: DietPlanResponse = {};
  message = '';

  constructor(private dietService: DietService, private excelService: ExcelService) {}

  ngOnInit(): void {
    this.loadDietPlan();
  }

  sectionKeys(): string[] {
    return Object.keys(this.dietPlan);
  }

  loadDietPlan(): void {
    this.dietService.getDietPlan().subscribe({
      next: (res) => {
        this.dietPlan = res;
      },
      error: () => {
        this.message = 'Unable to load diet plan';
      }
    });
  }

  async downloadPdf(): Promise<void> {
    const chart = document.getElementById('diet-chart');
    if (!chart) {
      return;
    }

    const canvas = await html2canvas(chart, { scale: 2 });
    const imgData = canvas.toDataURL('image/png');
    const pdf = new jsPDF('p', 'mm', 'a4');
    const pdfWidth = 190;
    const pdfHeight = (canvas.height * pdfWidth) / canvas.width;

    pdf.text("Samaira's Diet Chart", 10, 10);
    pdf.addImage(imgData, 'PNG', 10, 20, pdfWidth, pdfHeight);
    pdf.save('samaira-diet-chart.pdf');
  }

  downloadExcel(): void {
    this.excelService.exportExcel().subscribe((blob) => {
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = 'diet-plan.xlsx';
      link.click();
      window.URL.revokeObjectURL(url);
    });
  }

  onFileUpload(event: Event): void {
    const target = event.target as HTMLInputElement;
    const file = target.files?.[0];

    if (!file) {
      return;
    }

    this.excelService.importExcel(file).subscribe({
      next: (res) => {
        this.message = `Excel imported successfully. Rows: ${res.importedRows}`;
        this.loadDietPlan();
      },
      error: (err) => {
        this.message = err?.error?.message || 'Excel import failed';
      }
    });
  }
}
