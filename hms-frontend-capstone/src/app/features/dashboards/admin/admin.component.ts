import { Component, OnInit, Pipe, PipeTransform } from '@angular/core';
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { HttpClient } from '@angular/common/http';

@Pipe({
  name: 'filterByLocation',
  standalone: true
})
export class FilterByLocationPipe implements PipeTransform {
  transform(items: any[], location: string): any[] {
    return items.filter(item => item.location === location);
  }
}

@Component({
  standalone: true,
  imports: [CommonModule, RouterLink, CurrencyPipe, DatePipe, FilterByLocationPipe],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminDashboardComponent implements OnInit {
  doctors: any[] = [];
  patients: any[] = [];
  appointments: any[] = [];
  bills: any[] = [];
  error: string = '';

  private Doctors = [
  
  ];

  private Patients = [
    
  ];

  private Appointments = [
   
  ];

  private Bills = [
  
  ];

  constructor(
    public authService: AuthService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    // Fetch Doctors
    this.http.get('http://localhost:8761/api/doctors?page=0&size=10').subscribe({
      next: (data: any) => {
        this.doctors = data.content.map((doc: any) => ({
          id: doc.id,
          name: doc.name,
          location: location, 
          specialty: doc.specialty
        }));
      },
      error: (err) => {
        this.error = 'Failed to fetch doctors; using  data';
        this.doctors = this.Doctors;
      }
    });

    // Fetch Patients
    this.http.get('http://localhost:8761/api/patients?page=0&size=10').subscribe({
      next: (data: any) => {
        this.patients = data.content.map((patient: any) => ({
          id: patient.id,
          name: patient.name,
          address: patient.address
        }));
      },
      error: (err) => {
        this.error = 'Failed to fetch patients; using  data';
        this.patients = this.Patients;
      }
    });

    // Fetch Appointments
    this.http.get('http://localhost:8761/api/appointments?page=0&size=10').subscribe({
      next: (data: any) => {
        this.appointments = data.content.map((appt: any) => ({
          id: appt.id,
          patient: appt.patient, 
          doctor: appt.doctor,
          slot: appt.slot,
          status: appt.status
        }));
      },
      error: (err) => {
        this.error = '';
        this.appointments = this.Appointments;
      }
    });

    // Fetch Billings
    this.http.get('http://localhost:8761/api/billing?page=0&size=10').subscribe({
      next: (data: any) => {
        this.bills = data.content.map((bill: any) => ({
          id: bill.id,
          patient: bill.patient, 
          total: bill.total,
          status: bill.status
        }));
      },
      error: (err) => {
        this.error = '';
        this.bills = this.Bills;
      }
    });
  }
}