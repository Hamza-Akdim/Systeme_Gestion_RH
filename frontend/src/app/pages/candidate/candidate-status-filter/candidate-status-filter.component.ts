import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SelectButtonModule } from 'primeng/selectbutton';

@Component({
  selector: 'app-candidate-status-filter',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    SelectButtonModule
  ],
  templateUrl: './candidate-status-filter.component.html',
  styleUrls: ['./candidate-status-filter.component.scss']
})
export class CandidateStatusFilterComponent implements OnInit {
  @Output() statusChange = new EventEmitter<string[]>();
  
  statuses: any[] = [];
  selectedStatuses: any[] = [];

  ngOnInit() {
    this.statuses = [
      { label: 'Disponible', value: 'available' },
      { label: 'En entretien', value: 'interviewing' },
      { label: 'Embauché', value: 'hired' },
      { label: 'Rejeté', value: 'rejected' },
      { label: 'En attente', value: 'pending' }
    ];
  }

  onStatusChange() {
    this.statusChange.emit(this.selectedStatuses);
  }
}
