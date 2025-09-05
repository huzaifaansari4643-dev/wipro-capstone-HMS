import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';


@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [RouterLink, CommonModule,MatButtonModule],
  templateUrl: './landing.component.html', // Updated to use external template
  
  styleUrls: ['./landing.component.css']
})
export class LandingComponent {}