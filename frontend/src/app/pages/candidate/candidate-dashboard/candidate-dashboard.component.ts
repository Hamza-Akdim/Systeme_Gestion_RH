import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'primeng/chart';
import { CardModule } from 'primeng/card';
import { CandidateService } from '../candidate.service';
import { Candidate } from '../../../types/candidate';
import { Application } from '../../../types/application';

@Component({
  selector: 'app-candidate-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    ChartModule,
    CardModule
  ],
  templateUrl: './candidate-dashboard.component.html',
  styleUrls: ['./candidate-dashboard.component.scss']
})
export class CandidateDashboardComponent implements OnInit {
  candidates: Candidate[] = [];
  applications: Application[] = [];
  
  candidateStatusData: any;
  candidateStatusOptions: any;
  
  applicationStatusData: any;
  applicationStatusOptions: any;
  
  applicationTimelineData: any;
  applicationTimelineOptions: any;
  
  candidatesBySkillData: any;
  candidatesBySkillOptions: any;
  
  loading: boolean = true;

  constructor(private candidateService: CandidateService) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    Promise.all([
      this.candidateService.getCandidates(),
      this.candidateService.getApplications()
    ]).then(([candidates, applications]) => {
      this.candidates = candidates;
      this.applications = applications;
      
      this.initCandidateStatusChart();
      this.initApplicationStatusChart();
      this.initApplicationTimelineChart();
      this.initCandidatesBySkillChart();
      
      this.loading = false;
    });
  }

  initCandidateStatusChart() {
    // Compter les candidats par statut
    const statusCounts: Record<string, number> = {};
    
    this.candidates.forEach(candidate => {
      const status = candidate.status || 'unknown';
      statusCounts[status] = (statusCounts[status] || 0) + 1;
    });
    
    const labels = {
      'available': 'Disponible',
      'interviewing': 'En entretien',
      'hired': 'Embauché',
      'rejected': 'Rejeté',
      'pending': 'En attente',
      'unknown': 'Inconnu'
    };
    
    const colors = {
      'available': '#4CAF50',
      'interviewing': '#2196F3',
      'hired': '#FFC107',
      'rejected': '#F44336',
      'pending': '#9C27B0',
      'unknown': '#9E9E9E'
    };
    
    const chartLabels = Object.keys(statusCounts).map(key => labels[key as keyof typeof labels] || key);
    const chartData = Object.values(statusCounts);
    const chartColors = Object.keys(statusCounts).map(key => colors[key as keyof typeof colors] || '#9E9E9E');
    
    this.candidateStatusData = {
      labels: chartLabels,
      datasets: [
        {
          data: chartData,
          backgroundColor: chartColors,
          hoverBackgroundColor: chartColors.map(color => this.adjustBrightness(color, 0.1))
        }
      ]
    };
    
    this.candidateStatusOptions = {
      plugins: {
        legend: {
          position: 'right'
        },
        title: {
          display: true,
          text: 'Répartition des candidats par statut',
          font: {
            size: 16
          }
        }
      },
      responsive: true,
      maintainAspectRatio: false
    };
  }

  initApplicationStatusChart() {
    // Compter les candidatures par statut
    const statusCounts: Record<string, number> = {};
    
    this.applications.forEach(application => {
      const status = application.status || 'unknown';
      statusCounts[status] = (statusCounts[status] || 0) + 1;
    });
    
    const labels = {
      'applied': 'Candidature soumise',
      'screening': 'Présélection',
      'interview': 'Entretien',
      'offer': 'Offre',
      'accepted': 'Acceptée',
      'rejected': 'Rejetée',
      'hired': 'Embauché',
      'unknown': 'Inconnu'
    };
    
    const colors = {
      'applied': '#2196F3',
      'screening': '#4CAF50',
      'interview': '#FFC107',
      'offer': '#9C27B0',
      'accepted': '#4CAF50',
      'rejected': '#F44336',
      'hired': '#4CAF50',
      'unknown': '#9E9E9E'
    };
    
    const chartLabels = Object.keys(statusCounts).map(key => labels[key as keyof typeof labels] || key);
    const chartData = Object.values(statusCounts);
    const chartColors = Object.keys(statusCounts).map(key => colors[key as keyof typeof colors] || '#9E9E9E');
    
    this.applicationStatusData = {
      labels: chartLabels,
      datasets: [
        {
          data: chartData,
          backgroundColor: chartColors,
          hoverBackgroundColor: chartColors.map(color => this.adjustBrightness(color, 0.1))
        }
      ]
    };
    
    this.applicationStatusOptions = {
      plugins: {
        legend: {
          position: 'right'
        },
        title: {
          display: true,
          text: 'Répartition des candidatures par statut',
          font: {
            size: 16
          }
        }
      },
      responsive: true,
      maintainAspectRatio: false
    };
  }

  initApplicationTimelineChart() {
    // Regrouper les candidatures par mois
    const monthlyApplications: Record<string, number> = {};
    
    this.applications.forEach(application => {
      if (application.appliedDate) {
        const date = new Date(application.appliedDate);
        const monthYear = `${date.getMonth() + 1}/${date.getFullYear()}`;
        monthlyApplications[monthYear] = (monthlyApplications[monthYear] || 0) + 1;
      }
    });
    
    // Trier les mois chronologiquement
    const sortedMonths = Object.keys(monthlyApplications).sort((a, b) => {
      const [monthA, yearA] = a.split('/').map(Number);
      const [monthB, yearB] = b.split('/').map(Number);
      
      if (yearA !== yearB) {
        return yearA - yearB;
      }
      
      return monthA - monthB;
    });
    
    // Formater les labels de mois
    const monthNames = ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Juin', 'Juil', 'Août', 'Sep', 'Oct', 'Nov', 'Déc'];
    const formattedLabels = sortedMonths.map(monthYear => {
      const [month, year] = monthYear.split('/').map(Number);
      return `${monthNames[month - 1]} ${year}`;
    });
    
    this.applicationTimelineData = {
      labels: formattedLabels,
      datasets: [
        {
          label: 'Candidatures',
          data: sortedMonths.map(month => monthlyApplications[month]),
          fill: false,
          borderColor: '#2196F3',
          tension: 0.4,
          backgroundColor: 'rgba(33, 150, 243, 0.2)'
        }
      ]
    };
    
    this.applicationTimelineOptions = {
      plugins: {
        legend: {
          position: 'top'
        },
        title: {
          display: true,
          text: 'Évolution des candidatures dans le temps',
          font: {
            size: 16
          }
        }
      },
      scales: {
        x: {
          title: {
            display: true,
            text: 'Mois'
          }
        },
        y: {
          beginAtZero: true,
          title: {
            display: true,
            text: 'Nombre de candidatures'
          },
          ticks: {
            stepSize: 1
          }
        }
      },
      responsive: true,
      maintainAspectRatio: false
    };
  }

  initCandidatesBySkillChart() {
    // Compter les candidats par compétence
    const skillCounts: Record<string, number> = {};
    
    this.candidates.forEach(candidate => {
      if (candidate.skills && candidate.skills.length > 0) {
        candidate.skills.forEach(skill => {
          skillCounts[skill] = (skillCounts[skill] || 0) + 1;
        });
      }
    });
    
    // Trier par nombre de candidats (décroissant) et prendre les 10 premières compétences
    const topSkills = Object.entries(skillCounts)
      .sort((a, b) => b[1] - a[1])
      .slice(0, 10);
    
    const chartLabels = topSkills.map(([skill]) => skill);
    const chartData = topSkills.map(([, count]) => count);
    
    this.candidatesBySkillData = {
      labels: chartLabels,
      datasets: [
        {
          label: 'Nombre de candidats',
          data: chartData,
          backgroundColor: '#4CAF50',
          borderColor: '#388E3C',
          borderWidth: 1
        }
      ]
    };
    
    this.candidatesBySkillOptions = {
      plugins: {
        legend: {
          position: 'top'
        },
        title: {
          display: true,
          text: 'Top 10 des compétences des candidats',
          font: {
            size: 16
          }
        }
      },
      scales: {
        x: {
          title: {
            display: true,
            text: 'Compétence'
          }
        },
        y: {
          beginAtZero: true,
          title: {
            display: true,
            text: 'Nombre de candidats'
          },
          ticks: {
            stepSize: 1
          }
        }
      },
      responsive: true,
      maintainAspectRatio: false,
      indexAxis: 'y'
    };
  }

  // Utilitaire pour ajuster la luminosité d'une couleur
  adjustBrightness(color: string, factor: number): string {
    if (!color.startsWith('#')) {
      return color;
    }
    
    let r = parseInt(color.substring(1, 3), 16);
    let g = parseInt(color.substring(3, 5), 16);
    let b = parseInt(color.substring(5, 7), 16);
    
    r = Math.min(255, Math.round(r * (1 + factor)));
    g = Math.min(255, Math.round(g * (1 + factor)));
    b = Math.min(255, Math.round(b * (1 + factor)));
    
    return `#${r.toString(16).padStart(2, '0')}${g.toString(16).padStart(2, '0')}${b.toString(16).padStart(2, '0')}`;
  }
}
