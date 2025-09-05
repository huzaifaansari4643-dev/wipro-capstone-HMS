// import { Routes } from '@angular/router';

// export const routes: Routes = [];
import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { SignupComponent } from './features/auth/signup/signup.component';

import { authGuard, roleGuard } from './core/guards/auth.guard';
import { UserRole } from './core/models/user-role';
import { PatientDashboardComponent } from './features/dashboards/patient/patient.component';
import { DoctorDashboardComponent } from './features/dashboards/doctor/doctor.component';
import { AdminDashboardComponent } from './features/dashboards/admin/admin.component';
import { LandingComponent } from './features/landing/landing.component';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  {
    path: 'patient',
    component: PatientDashboardComponent,
    canActivate: [roleGuard([UserRole.PATIENT])]
  },
  {
    path: 'doctor',
    component: DoctorDashboardComponent,
    canActivate: [roleGuard([UserRole.DOCTOR])]
  },
  {
    path: 'admin',
    component: AdminDashboardComponent,
    canActivate: [roleGuard([UserRole.ADMIN])]
  },
  { path: '**', redirectTo: '/login' }
];

