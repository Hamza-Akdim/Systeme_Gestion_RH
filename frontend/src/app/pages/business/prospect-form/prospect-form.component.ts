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
import { RatingModule } from 'primeng/rating';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { Prospect } from '../../../types/prospect';
import { BusinessService } from '../business.service';

@Component({
  selector: 'app-prospect-form',
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
    RatingModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './prospect-form.component.html',
  styleUrls: ['./prospect-form.component.scss'],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProspectFormComponent implements OnInit {
  prospectForm!: FormGroup;
  prospect: Prospect = {};
  isEditMode: boolean = false;
  statuses: any[] = [];
  sources: any[] = [];
  submitted: boolean = false;

  constructor(
    private fb: FormBuilder,
    private businessService: BusinessService,
    private route: ActivatedRoute,
    private router: Router,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.initForm();

    this.statuses = [
      { label: 'Actif', value: 'active' },
      { label: 'Nouveau', value: 'new' },
      { label: 'Inactif', value: 'inactive' }
    ];

    this.sources = [
      { label: 'Site web', value: 'website' },
      { label: 'Recommandation', value: 'referral' },
      { label: 'Conférence', value: 'conference' },
      { label: 'Réseaux sociaux', value: 'social' },
      { label: 'Email', value: 'email' },
      { label: 'Autre', value: 'other' }
    ];

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.loadProspect(id);
      }
    });
  }

  initForm() {
    this.prospectForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      company: ['', Validators.required],
      position: [''],
      email: ['', [Validators.required, Validators.email]],
      phone: [''],
      address: [''],
      city: [''],
      country: [''],
      postalCode: [''],
      status: ['new', Validators.required],
      source: [''],
      assignedTo: [''],
      notes: [''],
      rating: [0],
      lastContact: [null],
      nextContact: [null],
      tags: [[]],
      budget: [null],
      opportunity: [null]
    });
  }

  loadProspect(id: string) {
    this.businessService.getProspect(id).then(data => {
      this.prospect = data;
      this.prospectForm.patchValue({
        ...this.prospect,
        lastContact: this.prospect.lastContact ? new Date(this.prospect.lastContact) : null,
        nextContact: this.prospect.nextContact ? new Date(this.prospect.nextContact) : null
      });
    });
  }

  onSubmit() {
    this.submitted = true;

    if (this.prospectForm.invalid) {
      return;
    }

    const prospectData: Prospect = {
      ...this.prospectForm.value
    };

    if (this.isEditMode && this.prospect.id) {
      prospectData.id = this.prospect.id;
      prospectData.createdAt = this.prospect.createdAt;
    }

    this.businessService.saveProspect(prospectData).then(savedProspect => {
      this.messageService.add({
        severity: 'success',
        summary: 'Succès',
        detail: this.isEditMode ? 'Prospect mis à jour' : 'Prospect créé',
        life: 3000
      });

      setTimeout(() => {
        this.router.navigate(['/pages/business', savedProspect.id]);
      }, 1000);
    });
  }

  onCancel() {
    if (this.isEditMode && this.prospect.id) {
      this.router.navigate(['/pages/business', this.prospect.id]);
    } else {
      this.router.navigate(['/pages/business']);
    }
  }
}
