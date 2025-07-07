import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TabViewModule } from 'primeng/tabview';
import { TagModule } from 'primeng/tag';
import { TimelineModule } from 'primeng/timeline';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { CandidateService } from '../candidate.service';
import { Application } from '../../../types/application';

@Component({
  selector: 'app-application-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ButtonModule,
    CardModule,
    TabViewModule,
    TagModule,
    TimelineModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './application-detail.component.html',
  styleUrls: ['./application-detail.component.scss']
})
export class ApplicationDetailComponent implements OnInit {
  application: Application = {};
  loading: boolean = true;
  statuses: any[] = [];
  offerStatuses: any[] = [];
  
  constructor(
    private candidateService: CandidateService,
    private route: ActivatedRoute,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.statuses = [
      { label: 'Candidature soumise', value: 'applied' },
      { label: 'Présélection', value: 'screening' },
      { label: 'Entretien', value: 'interview' },
      { label: 'Offre', value: 'offer' },
      { label: 'Acceptée', value: 'accepted' },
      { label: 'Rejetée', value: 'rejected' },
      { label: 'Embauché', value: 'hired' }
    ];
    
    this.offerStatuses = [
      { label: 'En attente', value: 'pending' },
      { label: 'Acceptée', value: 'accepted' },
      { label: 'Rejetée', value: 'rejected' },
      { label: 'Négociation', value: 'negotiation' }
    ];
    
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.loadApplication(id);
      }
    });
  }

  loadApplication(id: string) {
    this.loading = true;
    this.candidateService.getApplication(id).then(data => {
      this.application = data;
      this.loading = false;
    });
  }

  getSeverity(status: string) {
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
  
  getStatusLabel(status: string) {
    const statusObj = this.statuses.find(s => s.value === status);
    return statusObj ? statusObj.label : status;
  }
  
  getOfferStatusLabel(status: string) {
    const statusObj = this.offerStatuses.find(s => s.value === status);
    return statusObj ? statusObj.label : status;
  }
}
