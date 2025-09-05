// import { Component } from '@angular/core';
// import { MatToolbarModule } from '@angular/material/toolbar';
// import { MatButtonModule } from '@angular/material/button';
// import { Router } from '@angular/router';
// import { AuthService } from '../../../core/services/auth.service';

// @Component({
//   standalone: true,
//   imports: [MatToolbarModule, MatButtonModule],
//   selector: 'app-nav-bar',
//   templateUrl: './nav-bar.component.html',
//   styleUrls: ['./nav-bar.component.css']
// })
// export class NavBarComponent {
//   constructor(public authService: AuthService, private router: Router) {}

//   logout(): void {
//     this.authService.logout();
//     this.router.navigate(['/login']);
//   }
// }

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  standalone: true,
  imports: [CommonModule, RouterLink],
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent {
  constructor(public authService: AuthService, private router: Router) {}

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}