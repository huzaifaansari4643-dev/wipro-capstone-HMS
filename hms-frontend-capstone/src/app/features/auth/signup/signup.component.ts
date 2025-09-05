// import { Component } from '@angular/core';
// import { FormsModule, ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
// import { MatButtonModule } from '@angular/material/button';
// import { MatCardModule } from '@angular/material/card';
// import { MatSelectModule } from '@angular/material/select';
// import { Router } from '@angular/router';
// import { AuthService } from '../../../core/services/auth.service';
// import { UserRole } from '../../../core/models/user-role';

// @Component({
//   standalone: true,
//   imports: [FormsModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatCardModule, MatSelectModule],
//   templateUrl: './signup.component.html',
//   styleUrls: ['./signup.component.css']
// })
// export class SignupComponent {
//   signupForm: FormGroup;
//   roles = [UserRole.PATIENT, UserRole.DOCTOR]; // Exclude ADMIN
//   error: string = '';

//   constructor(private authService: AuthService, private router: Router, private fb: FormBuilder) {
//     this.signupForm = this.fb.group({
//       name: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
//       email: ['', [Validators.required, Validators.email]],
//       password: ['', [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=*!?])[A-Za-z\d@#$%^&+=*!?]{8,20}$/), Validators.minLength(8), Validators.maxLength(20)]],
//       role: ['', Validators.required]
//     });
//   }

//   onSubmit(): void {
//     if (this.signupForm.valid) {
//       this.authService.signup(this.signupForm.value).subscribe({
//         next: (response) => {
//           this.redirectToDashboard(response.userRole);
//         },
//         error: (err) => {
//           this.error = err.error?.message || 'Signup failed. Please check your input.';
//         }
//       });
//     }
//   }

//   private redirectToDashboard(role: UserRole): void {
//     switch (role) {
//       case UserRole.PATIENT:
//         this.router.navigate(['/patient']);
//         break;
//       case UserRole.DOCTOR:
//         this.router.navigate(['/doctor']);
//         break;
//       default:
//         this.router.navigate(['/login']);
//     }
//   }
// }

import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { UserRole } from '../../../core/models/user-role';

@Component({
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatSelectModule
  ],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  signupForm: FormGroup;
  roles = Object.values(UserRole).filter(role => role !== UserRole.ADMIN); // Exclude ADMIN
  error: string = '';

  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder) {
    this.signupForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [
        Validators.required,
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=*!?])[A-Za-z\d@#$%^&+=*!?]{8,20}$/),
        Validators.minLength(8),
        Validators.maxLength(20)
      ]],
      role: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.signupForm.valid) {
      this.authService.signup(this.signupForm.value).subscribe({
        next: (response) => {
          this.redirectToDashboard(response.userRole);
        },
        error: (err) => {
          this.error = err.error?.message || 'Signup failed. Please check your input.';
        }
      });
    }
  }

  private redirectToDashboard(role: UserRole): void {
    switch (role) {
      case UserRole.PATIENT:
        this.router.navigate(['/patient']);
        break;
      case UserRole.DOCTOR:
        this.router.navigate(['/doctor']);
        break;
      default:
        this.router.navigate(['/login']);
    }
  }
}