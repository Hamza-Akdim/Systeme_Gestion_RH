import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { SelectButtonModule } from 'primeng/selectbutton';

@Component({
  selector: 'app-prospect-status-filter',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ButtonModule,
    SelectButtonModule
  ],
  templateUrl: './prospect-status-filter.component.html',
  styleUrls: ['./prospect-status-filter.component.scss']
})
export class ProspectStatusFilterComponent {
  @Output() statusChange = new EventEmitter<string>();
  
  selectedStatus: string = '';
  
  statuses = [
    { label: 'Tous', value: '' },
    { label: 'Actifs', value: 'active' },
    { label: 'Nouveaux', value: 'new' },
    { label: 'Inactifs', value: 'inactive' }
  ];
  
  onStatusChange() {
    this.statusChange.emit(this.selectedStatus);
  }
}
