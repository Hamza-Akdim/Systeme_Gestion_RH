import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-resume-viewer',
  standalone: true,
  imports: [
    CommonModule,
    CardModule,
    ButtonModule
  ],
  templateUrl: './resume-viewer.component.html',
  styleUrls: ['./resume-viewer.component.scss']
})
export class ResumeViewerComponent {
  @Input() resumeUrl: string | undefined;

  openResume() {
  if (this.resumeUrl) {
    window.open(this.resumeUrl, '_blank');
  }
}
}
