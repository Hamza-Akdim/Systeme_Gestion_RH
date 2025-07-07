import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule, FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { Textarea } from 'primeng/textarea';;
import { DropdownModule } from 'primeng/dropdown';
import { CalendarModule } from 'primeng/calendar';
import { ChipsModule } from 'primeng/chips';
import { CheckboxModule } from 'primeng/checkbox';
import { RadioButtonModule } from 'primeng/radiobutton';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { BusinessSurvey, SurveyQuestion } from '../../../types/business-survey';
import { BusinessService } from '../business.service';

@Component({
  selector: 'app-business-survey-form',
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
    ChipsModule,
    CheckboxModule,
    RadioButtonModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './business-survey-form.component.html',
  styleUrls: ['./business-survey-form.component.scss']
})
export class BusinessSurveyFormComponent implements OnInit {
  surveyForm!: FormGroup;
  survey: BusinessSurvey = {};
  isEditMode: boolean = false;
  statuses: any[] = [];
  questionTypes: any[] = [];
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
      { label: 'Brouillon', value: 'draft' },
      { label: 'Terminé', value: 'completed' },
      { label: 'Archivé', value: 'archived' }
    ];

    this.questionTypes = [
      { label: 'Choix multiple', value: 'multiple_choice' },
      { label: 'Évaluation', value: 'rating' },
      { label: 'Texte', value: 'text' },
      { label: 'Oui/Non', value: 'boolean' }
    ];

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.loadSurvey(id);
      }
    });
  }

  initForm() {
    this.surveyForm = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      status: ['draft', Validators.required],
      targetAudience: [''],
      startDate: [null],
      endDate: [null],
      isPublic: [true],
      tags: [[]],
      questions: this.fb.array([])
    });
  }

  get questions(): FormArray {
    return this.surveyForm.get('questions') as FormArray;
  }

  loadSurvey(id: string) {
    this.businessService.getSurvey(id).then(data => {
      this.survey = data;

      // Réinitialiser le formulaire avec les données de l'enquête
      this.surveyForm.patchValue({
        title: this.survey.title,
        description: this.survey.description,
        status: this.survey.status,
        targetAudience: this.survey.targetAudience,
        startDate: this.survey.startDate ? new Date(this.survey.startDate) : null,
        endDate: this.survey.endDate ? new Date(this.survey.endDate) : null,
        isPublic: this.survey.isPublic,
        tags: this.survey.tags
      });

      // Ajouter les questions
      if (this.survey.questions && this.survey.questions.length > 0) {
        this.survey.questions.forEach(question => {
          this.addExistingQuestion(question);
        });
      }
    });
  }

  addQuestion() {
    const questionGroup = this.fb.group({
      id: [this.generateQuestionId()],
      text: ['', Validators.required],
      type: ['text', Validators.required],
      required: [false],
      options: [[]]
    });

    this.questions.push(questionGroup);
  }

  addExistingQuestion(question: SurveyQuestion) {
    const questionGroup = this.fb.group({
      id: [question.id || this.generateQuestionId()],
      text: [question.text || '', Validators.required],
      type: [question.type || 'text', Validators.required],
      required: [question.required || false],
      options: [question.options || []]
    });

    this.questions.push(questionGroup);
  }

  removeQuestion(index: number) {
    this.questions.removeAt(index);
  }

  generateQuestionId(): string {
    return 'q_' + new Date().getTime() + '_' + Math.floor(Math.random() * 1000);
  }

  onSubmit() {
    this.submitted = true;

    if (this.surveyForm.invalid) {
      return;
    }

    const surveyData: BusinessSurvey = {
      ...this.surveyForm.value
    };

    if (this.isEditMode && this.survey.id) {
      surveyData.id = this.survey.id;
      surveyData.createdAt = this.survey.createdAt;
      surveyData.createdBy = this.survey.createdBy;
      surveyData.responses = this.survey.responses;
    } else {
      surveyData.createdAt = new Date();
      surveyData.createdBy = 'Utilisateur actuel'; // À remplacer par l'utilisateur réel
    }

    this.businessService.saveSurvey(surveyData).then(savedSurvey => {
      this.messageService.add({
        severity: 'success',
        summary: 'Succès',
        detail: this.isEditMode ? 'Enquête mise à jour' : 'Enquête créée',
        life: 3000
      });

      setTimeout(() => {
        this.router.navigate(['/pages/business/surveys', savedSurvey.id]);
      }, 1000);
    });
  }

  onCancel() {
    if (this.isEditMode && this.survey.id) {
      this.router.navigate(['/pages/business/surveys', this.survey.id]);
    } else {
      this.router.navigate(['/pages/business/surveys']);
    }
  }

  shouldShowOptions(questionType: string): boolean {
    return questionType === 'multiple_choice';
  }
}
