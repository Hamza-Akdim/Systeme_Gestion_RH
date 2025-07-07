import { Component, OnInit, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { Textarea } from 'primeng/textarea';
import { DropdownModule } from 'primeng/dropdown';
import { CalendarModule } from 'primeng/calendar';
import { InputNumberModule } from 'primeng/inputnumber';
import { FileUploadModule } from 'primeng/fileupload';
import { RatingModule } from 'primeng/rating';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { CandidateService } from '../candidate.service';
import { Application } from '../../../types/application';
import { Candidate } from '../../../types/candidate';

@Component({
  selector: 'app-application-form',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonModule,
    InputTextModule,
    Textarea,
    DropdownModule,
    CalendarModule,
    InputNumberModule,
    FileUploadModule,
    ToastModule,
    RatingModule
  ],
  providers: [MessageService],
  templateUrl: './application-form.component.html',
  styleUrls: ['./application-form.component.scss'],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApplicationFormComponent implements OnInit {
  applicationForm!: FormGroup;
  application: Application = {};
  isEditMode: boolean = false;
  statuses: any[] = [];
  offerStatuses: any[] = [];
  candidates: Candidate[] = [];
  submitted: boolean = false;
  
  constructor(
    private fb: FormBuilder,
    private candidateService: CandidateService,
    private route: ActivatedRoute,
    private router: Router,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.initForm();
    
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
    
    this.loadCandidates();
    
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.loadApplication(id);
      } else {
        this.route.queryParamMap.subscribe(queryParams => {
          const candidateId = queryParams.get('candidateId');
          if (candidateId) {
            this.applicationForm.patchValue({ candidateId });
          }
        });
      }
    });
  }

  initForm() {
    this.applicationForm = this.fb.group({
      candidateId: ['', Validators.required],
      jobId: [''],
      jobTitle: ['', Validators.required],
      status: ['applied', Validators.required],
      interviewDate: [null],
      interviewNotes: [''],
      feedback: [''],
      rating: [0],
      salary: [null],
      offerStatus: [''],
      offerDate: [null],
      startDate: [null],
      documents: [[]]
    });
  }

  loadCandidates() {
    this.candidateService.getCandidates().then(data => {
      this.candidates = data;
    });
  }

  loadApplication(id: string) {
    this.candidateService.getApplication(id).then(data => {
      this.application = data;
      this.applicationForm.patchValue({
        ...this.application,
        interviewDate: this.application.interviewDate ? new Date(this.application.interviewDate) : null,
        offerDate: this.application.offerDate ? new Date(this.application.offerDate) : null,
        startDate: this.application.startDate ? new Date(this.application.startDate) : null
      });
    });
  }

  onSubmit() {
    this.submitted = true;
    
    if (this.applicationForm.invalid) {
      return;
    }
    
    const applicationData: Application = {
      ...this.applicationForm.value
    };
    
    if (this.isEditMode && this.application.id) {
      applicationData.id = this.application.id;
      applicationData.appliedDate = this.application.appliedDate;
      applicationData.history = this.application.history;
    }
    
    this.candidateService.saveApplication(applicationData).then(savedApplication => {
      this.messageService.add({
        severity: 'success',
        summary: 'Succès',
        detail: this.isEditMode ? 'Candidature mise à jour' : 'Candidature créée',
        life: 3000
      });
      
      setTimeout(() => {
        this.router.navigate(['/pages/candidate/applications', savedApplication.id]);
      }, 1000);
    });
  }

  onCancel() {
    if (this.isEditMode && this.application.id) {
      this.router.navigate(['/pages/candidate/applications', this.application.id]);
    } else {
      this.router.navigate(['/pages/candidate/applications']);
    }
  }

  getCandidateLabel(candidateId: string): string {
    const candidate = this.candidates.find(c => c.id === candidateId);
    return candidate ? `${candidate.firstName} ${candidate.lastName}` : '';
  }
}
