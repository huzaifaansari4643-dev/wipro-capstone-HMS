import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { UserRole } from '../../../core/models/user-role';

@Component({
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatCardModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  error: string = '';

  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;
      this.authService.login(email, password).subscribe({
        next: (response) => {
          this.redirectToDashboard(response.userRole);
        },
        error: (err) => {
          this.error = err.error?.message || 'Login failed. Please check your credentials.';
        }
      });
    }
  }

  private redirectToDashboard(role: UserRole): void {
    switch (role) {
      case UserRole.ADMIN:
        this.router.navigate(['/admin']);
        break;
      case UserRole.PATIENT:
        this.router.navigate(['/patient']);
        break;
      case UserRole.DOCTOR:
        this.router.navigate(['/doctor']);
        break;
    }
  }
}