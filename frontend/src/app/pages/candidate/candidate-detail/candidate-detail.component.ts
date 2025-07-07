import { Component, OnInit, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TabViewModule } from 'primeng/tabview';
import { TagModule } from 'primeng/tag';
import { TimelineModule } from 'primeng/timeline';
import { RatingModule } from 'primeng/rating';
import { FormsModule } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { CandidateService } from '../candidate.service';
import { Candidate } from '../../../types/candidate';
import { Application } from '../../../types/application';
import { SkillsViewerComponent } from '../skills-viewer/skills-viewer.component';
import { ResumeViewerComponent } from '../resume-viewer/resume-viewer.component';
import { ApplicationStatusTrackerComponent } from '../application-status-tracker/application-status-tracker.component';

@Component({
  selector: 'app-candidate-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ButtonModule,
    CardModule,
    TabViewModule,
    TagModule,
    TimelineModule,
    RatingModule,
    FormsModule,
    ToastModule,
    SkillsViewerComponent,
    ResumeViewerComponent,
    ApplicationStatusTrackerComponent
  ],
  providers: [MessageService],
  templateUrl: './candidate-detail.component.html',
  styleUrls: ['./candidate-detail.component.scss'],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CandidateDetailComponent implements OnInit {
  candidate: Candidate = {};
  applications: Application[] = [];
  loading: boolean = true;
  statuses: any[] = [];

  constructor(
    private candidateService: CandidateService,
    private route: ActivatedRoute,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.statuses = [
      { label: 'Disponible', value: 'available' },
      { label: 'En entretien', value: 'interviewing' },
      { label: 'Embauché', value: 'hired' },
      { label: 'Rejeté', value: 'rejected' },
      { label: 'En attente', value: 'pending' }
    ];
    
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.loadCandidate(id);
        this.loadApplications(id);
      }
    });
  }

  loadCandidate(id: string) {
    this.loading = true;
    this.candidateService.getCandidate(id).then(data => {
      this.candidate = data;
      this.loading = false;
    });
  }

  loadApplications(candidateId: string) {
    this.candidateService.getApplications(candidateId).then(data => {
      this.applications = data;
    });
  }

  getSeverity(status: string) {
    switch (status) {
      case 'available':
        return 'success';
      case 'interviewing':
        return 'info';
      case 'hired':
        return 'warning';
      case 'rejected':
        return 'danger';
      default:
        return 'secondary';
    }
  }

  getStatusLabel(status: string) {
    const statusObj = this.statuses.find(s => s.value === status);
    return statusObj ? statusObj.label : status;
  }
}
