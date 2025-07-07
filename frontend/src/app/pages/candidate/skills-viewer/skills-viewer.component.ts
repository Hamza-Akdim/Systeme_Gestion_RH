import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChipModule } from 'primeng/chip';
import { ProgressBarModule } from 'primeng/progressbar';

@Component({
  selector: 'app-skills-viewer',
  standalone: true,
  imports: [
    CommonModule,
    ChipModule,
    ProgressBarModule
  ],
  templateUrl: './skills-viewer.component.html',
  styleUrls: ['./skills-viewer.component.scss']
})
export class SkillsViewerComponent {
  @Input() skills: string[] | undefined;
}
