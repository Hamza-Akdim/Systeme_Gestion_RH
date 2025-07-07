import { Component, OnInit,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { Textarea } from 'primeng/textarea';
import { DropdownModule } from 'primeng/dropdown';
import { CalendarModule } from 'primeng/calendar';
import { InputNumberModule } from 'primeng/inputnumber';
import { ChipsModule } from 'primeng/chips';
import { FileUploadModule } from 'primeng/fileupload';
import { RatingModule } from 'primeng/rating';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { CandidateService } from '../candidate.service';
import { Candidate } from '../../../types/candidate';

@Component({
  selector: 'app-candidate-form',
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
    ChipsModule,
    FileUploadModule,
    RatingModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './candidate-form.component.html',
  styleUrls: ['./candidate-form.component.scss'],
  schemas: [CUSTOM_ELEMENTS_SCHEMA] // Ajouter cette ligne
})
export class CandidateFormComponent implements OnInit {
  candidateForm!: FormGroup;
  candidate: Candidate = {};
  isEditMode: boolean = false;
  statuses: any[] = [];
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
      { label: 'Disponible', value: 'available' },
      { label: 'En entretien', value: 'interviewing' },
      { label: 'Embauché', value: 'hired' },
      { label: 'Rejeté', value: 'rejected' },
      { label: 'En attente', value: 'pending' }
    ];
    
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.loadCandidate(id);
      }
    });
  }

  initForm() {
    this.candidateForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: [''],
      address: [''],
      city: [''],
      country: [''],
      postalCode: [''],
      birthDate: [null],
      skills: [[]],
      experience: [0],
      education: [''],
      resumeUrl: [''],
      status: ['available'],
      rating: [0],
      notes: ['']
    });
  }

  loadCandidate(id: string) {
    this.candidateService.getCandidate(id).then(data => {
      this.candidate = data;
      this.candidateForm.patchValue({
        ...this.candidate,
        birthDate: this.candidate.birthDate ? new Date(this.candidate.birthDate) : null
      });
    });
  }

  onSubmit() {
    this.submitted = true;
    
    if (this.candidateForm.invalid) {
      return;
    }
    
    const candidateData: Candidate = {
      ...this.candidateForm.value
    };
    
    if (this.isEditMode && this.candidate.id) {
      candidateData.id = this.candidate.id;
      candidateData.createdAt = this.candidate.createdAt;
    }
    
    this.candidateService.saveCandidate(candidateData).then(savedCandidate => {
      this.messageService.add({
        severity: 'success',
        summary: 'Succès',
        detail: this.isEditMode ? 'Candidat mis à jour' : 'Candidat créé',
        life: 3000
      });
      
      setTimeout(() => {
        this.router.navigate(['/pages/candidate', savedCandidate.id]);
      }, 1000);
    });
  }

  onCancel() {
    if (this.isEditMode && this.candidate.id) {
      this.router.navigate(['/pages/candidate', this.candidate.id]);
    } else {
      this.router.navigate(['/pages/candidate']);
    }
  }
}
