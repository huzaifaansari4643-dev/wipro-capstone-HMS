import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { HttpClient } from '@angular/common/http';

@Component({
  standalone: true,
  imports: [CommonModule, RouterLink, DatePipe],
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.css']
})
export class DoctorDashboardComponent implements OnInit {
  appointments: any[] = [];
  error: string = '';

  private Appointments = [
   
  ];

  constructor(
    public authService: AuthService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    const doctorId = this.authService.getAuthData() || 'doc1';
    this.http.get(`http://localhost:8761/api/appointments/doctor/${doctorId}?page=0&size=10`).subscribe({
      next: (data: any) => {
        this.appointments = data.content.map((appt: any) => ({
          id: appt.id,
          patient: appt.patient, 
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

  markAsResolved(id: number): void {
    this.http.patch(`http://localhost:8761/api/appointments/${id}/accept`, {}).subscribe({
      next: () => {
        const appointment = this.appointments.find(a => a.id === id);
        if (appointment) {
          appointment.status = 'RESOLVED';
        }
      },
      error: (err) => {
        this.error = 'Failed to mark as resolved; updating locally';
        const appointment = this.appointments.find(a => a.id === id);
        if (appointment) {
          appointment.status = 'RESOLVED';
        }
      }
    });
  }
}