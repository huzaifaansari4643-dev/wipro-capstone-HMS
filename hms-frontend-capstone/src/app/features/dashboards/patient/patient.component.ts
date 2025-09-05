import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { HttpClient } from '@angular/common/http';

@Component({
  standalone: true,
  imports: [CommonModule, RouterLink, DatePipe],
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientDashboardComponent implements OnInit {
  appointments: any[] = [];
  error: string = '';

  private Appointments = [
   
  ];

  constructor(
    public authService: AuthService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    const userId = this.authService.getAuthData() || '1';
    this.http.get(`http://localhost:8761/api/appointments/patient/${userId}?page=0&size=10`).subscribe({
      next: (data: any) => {
        this.appointments = data.content.map((appt: any) => ({
          id: appt.id,
          doctor: appt.doctor,
          slot: appt.slot,
          location: appt.location, 
          status: appt.status,
          notes: appt.notes
        }));
      },
      error: (err) => {
        this.error = '';
        this.appointments = this.Appointments;
      }
    });
  }
}