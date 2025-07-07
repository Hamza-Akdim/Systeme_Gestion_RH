import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TimelineModule } from 'primeng/timeline';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { Application } from '../../../types/application';

@Component({
  selector: 'app-application-status-tracker',
  standalone: true,
  imports: [
    CommonModule,
    TimelineModule,
    CardModule,
    TagModule
  ],
  templateUrl: './application-status-tracker.component.html',
  styleUrls: ['./application-status-tracker.component.scss']
})
export class ApplicationStatusTrackerComponent {
  @Input() application: Application | undefined;
  
  getSeverity(status: string): string {
    switch (status) {
      case 'applied':
        return 'info';
      case 'screening':
        return 'primary';
      case 'interview':
        return 'warning';
      case 'offer':
        return 'success';
      case 'accepted':
        return 'success';
      case 'rejected':
        return 'danger';
      case 'hired':
        return 'success';
      default:
        return 'secondary';
    }
  }
  
  getStatusLabel(status: string): string {
    switch (status) {
      case 'applied':
        return 'Candidature soumise';
      case 'screening':
        return 'Présélection';
      case 'interview':
        return 'Entretien';
      case 'offer':
        return 'Offre';
      case 'accepted':
        return 'Acceptée';
      case 'rejected':
        return 'Rejetée';
      case 'hired':
        return 'Embauché';
      default:
        return status;
    }
  }
}
