export interface AuthPayload {
  email: string;
  password: string;
  fullName?: string;
}

export interface AuthResponse {
  token: string;
  email: string;
  fullName: string;
}

export interface DietItem {
  mealType: string;
  item: string;
}

export type DietPlanResponse = Record<string, DietItem[]>;
