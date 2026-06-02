import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { DietPlanResponse } from '../models/types';

@Injectable({ providedIn: 'root' })
export class DietService {
  constructor(private http: HttpClient) {}

  getDietPlan(): Observable<DietPlanResponse> {
    return this.http.get<DietPlanResponse>(`${environment.apiBaseUrl}/diet-plan`);
  }
}
