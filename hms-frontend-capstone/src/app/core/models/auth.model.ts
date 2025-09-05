import { UserRole } from './user-role';

export interface User {
  name: string;
  email: string;
  password: string;
  role: UserRole;
}

export interface AuthResponse {
  jwt: string;
  userId: number;
  name: string;
  email: string;
  userRole: UserRole;
}