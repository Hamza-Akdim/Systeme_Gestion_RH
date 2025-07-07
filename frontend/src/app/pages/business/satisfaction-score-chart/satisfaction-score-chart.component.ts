import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'primeng/chart';
import { SurveyResponse } from '../../../types/business-survey';

@Component({
  selector: 'app-satisfaction-score-chart',
  standalone: true,
  imports: [
    CommonModule,
    ChartModule
  ],
  templateUrl: './satisfaction-score-chart.component.html',
  styleUrls: ['./satisfaction-score-chart.component.scss']
})
export class SatisfactionScoreChartComponent {
  @Input() responses: SurveyResponse[] = [];
  
  chartData: any;
  chartOptions: any;
  
  ngOnInit() {
    this.initChart();
  }
  
  ngOnChanges() {
    this.initChart();
  }
  
  private initChart() {
    if (!this.responses || this.responses.length === 0) {
      return;
    }
    
    // Compter les scores de satisfaction
    const scoreCounts = [0, 0, 0, 0, 0, 0];
    
    this.responses.forEach(response => {
      const score = response.satisfactionScore || 0;
      if (score >= 0 && score <= 5) {
        scoreCounts[score]++;
      }
    });
    
    // Préparer les données du graphique
    this.chartData = {
      labels: ['0', '1', '2', '3', '4', '5'],
      datasets: [
        {
          label: 'Nombre de réponses',
          data: scoreCounts,
          backgroundColor: [
            '#FF6384',
            '#FF9F40',
            '#FFCD56',
            '#4BC0C0',
            '#36A2EB',
            '#9966FF'
          ],
          hoverBackgroundColor: [
            '#FF6384',
            '#FF9F40',
            '#FFCD56',
            '#4BC0C0',
            '#36A2EB',
            '#9966FF'
          ]
        }
      ]
    };
    
    this.chartOptions = {
      plugins: {
        legend: {
          position: 'right'
        },
        title: {
          display: true,
          text: 'Distribution des scores de satisfaction',
          font: {
            size: 16
          }
        }
      },
      responsive: true,
      maintainAspectRatio: false
    };
  }
}
