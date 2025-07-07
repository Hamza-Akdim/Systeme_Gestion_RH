import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { Prospect } from '../../../types/prospect';

@Component({
  selector: 'app-prospect-assignment-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonModule,
    InputTextModule,
    DropdownModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './prospect-assignment-form.component.html',
  styleUrls: ['./prospect-assignment-form.component.scss']
})
export class ProspectAssignmentFormComponent {
  @Input() prospect: Prospect | null = null;
  @Output() assignmentComplete = new EventEmitter<Prospect>();
  
  assignmentForm!: FormGroup;
  users: any[] = [];
  submitted: boolean = false;
  
  constructor(
    private fb: FormBuilder,
    private messageService: MessageService
  ) {}
  
  ngOnInit() {
    this.initForm();
    this.loadUsers();
  }
  
  ngOnChanges() {
    if (this.prospect && this.assignmentForm) {
      this.assignmentForm.patchValue({
        assignedTo: this.prospect.assignedTo || ''
      });
    }
  }
  
  initForm() {
    this.assignmentForm = this.fb.group({
      assignedTo: ['', Validators.required]
    });
  }
  
  loadUsers() {
    // Simuler le chargement des utilisateurs
    // Dans un cas réel, cela viendrait d'un service
    this.users = [
      { label: 'Jean Dupont', value: 'Jean Dupont' },
      { label: 'Marie Martin', value: 'Marie Martin' },
      { label: 'Pierre Durand', value: 'Pierre Durand' },
      { label: 'Sophie Lefebvre', value: 'Sophie Lefebvre' },
      { label: 'Thomas Bernard', value: 'Thomas Bernard' }
    ];
  }
  
  onSubmit() {
    this.submitted = true;
    
    if (this.assignmentForm.invalid) {
      return;
    }
    
    if (this.prospect) {
      const updatedProspect: Prospect = {
        ...this.prospect,
        assignedTo: this.assignmentForm.value.assignedTo
      };
      
      // Émettre l'événement avec le prospect mis à jour
      this.assignmentComplete.emit(updatedProspect);
      
      this.messageService.add({
        severity: 'success',
        summary: 'Succès',
        detail: 'Prospect assigné avec succès',
        life: 3000
      });
    }
  }
  
  onCancel() {
    this.assignmentComplete.emit(this.prospect || {});
  }
}
