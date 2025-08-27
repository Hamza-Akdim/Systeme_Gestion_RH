import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-voluntary-disclosures',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
        <div class="mt-5">
            <h3 class="text-lg font-semibold text-blue-400">Terms and Conditions</h3>
            <p class="text-sm mt-2 leading-relaxed">
                Protecting your privacy is a top priority, and we use this data to allow us to track ongoing recruitments. Otherwise, it would still be a shame if you couldn't apply for our job openings. If you do not wish to share your personal
                data, please do not submit it to us.
            </p>

            <div class="mt-4 flex items-start gap-2">
                <input type="checkbox" [checked]="accepted" (change)="onCheckboxChange($event)" required class="w-5 h-5 accent-blue-600" />
                <label class="text-sm">
                    Yes, I have read and accept the <strong>privacy policy</strong>
                    <span class="text-red-400"> *</span>
                </label>
            </div>
        </div>
    `
})
export class VoluntaryDisclosuresComponent {
    @Input() accepted = false;
    @Output() acceptedChange = new EventEmitter<boolean>();

    onCheckboxChange(event: Event) {
        this.accepted = (event.target as HTMLInputElement).checked;

        this.acceptedChange.emit(this.accepted);
    }
}
