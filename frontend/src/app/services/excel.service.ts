import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ExcelService {
  constructor(private http: HttpClient) {}

  exportExcel(): Observable<Blob> {
    return this.http.get(`${environment.apiBaseUrl}/excel/export`, { responseType: 'blob' });
  }

  importExcel(file: File): Observable<{ importedRows: number }> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<{ importedRows: number }>(`${environment.apiBaseUrl}/excel/import`, formData);
  }
}
