import { Component, OnInit,CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TabViewModule } from 'primeng/tabview';
import { TagModule } from 'primeng/tag';
import { RatingModule } from 'primeng/rating';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { Prospect } from '../../../types/prospect';
import { BusinessService } from '../business.service';
import { BusinessSurvey } from '../../../types/business-survey';

@Component({
  selector: 'app-prospect-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ButtonModule,
    CardModule,
    TabViewModule,
    TagModule,
    RatingModule,
    ToastModule,
    ConfirmDialogModule
  ],
  providers: [MessageService, ConfirmationService],
  templateUrl: './prospect-detail.component.html',
  styleUrls: ['./prospect-detail.component.scss'],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProspectDetailComponent implements OnInit {
  prospect: Prospect = {};
  loading: boolean = true;
  survey: any[] = [];

  constructor(
    private businessService: BusinessService,
    private route: ActivatedRoute,
    private router: Router,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.loadProspect(id);
      }
    });
  }

  loadProspect(id: string) {
    this.loading = true;
    this.businessService.getProspect(id).then(data => {
      this.prospect = data;
      this.loading = false;
    });
  }

  deleteProspect() {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir supprimer ce prospect ?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (this.prospect.id) {
          this.businessService.deleteProspect(this.prospect.id).then(() => {
            this.messageService.add({
              severity: 'success',
              summary: 'Succès',
              detail: 'Prospect supprimé',
              life: 3000
            });

            setTimeout(() => {
              this.router.navigate(['/pages/business']);
            }, 1000);
          });
        }
      }
    });
  }

  getStatusSeverity(status: string): string {
    switch (status) {
      case 'active':
        return 'success';
      case 'new':
        return 'info';
      case 'inactive':
        return 'warning';
      default:
        return 'secondary';
    }
  }

  getStatusLabel(status: string): string {
    switch (status) {
      case 'active':
        return 'Actif';
      case 'new':
        return 'Nouveau';
      case 'inactive':
        return 'Inactif';
      default:
        return status;
    }
  }

  getSourceLabel(source: string): string {
    switch (source) {
      case 'website':
        return 'Site web';
      case 'referral':
        return 'Recommandation';
      case 'conference':
        return 'Conférence';
      case 'social':
        return 'Réseaux sociaux';
      case 'email':
        return 'Email';
      case 'other':
        return 'Autre';
      default:
        return source;
    }
  }
}
