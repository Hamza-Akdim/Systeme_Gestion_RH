import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-my-information',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
        <div>
            <h3 class="text-lg text-blue-400 font-bold mt-8">Name</h3>

            <!-- First Name -->
            <div class="mt-3">
                <label class="block text-sm font-semibold mb-2"> First Name <span class="text-red-400">*</span> </label>
                <input
                    [(ngModel)]="candidate.firstName"
                    (ngModelChange)="onCandidateChange()"
                    name="firstName"
                    type="text"
                    required
                    maxlength="100"
                    class="w-full p-2 rounded bg-gray-800 text-white border border-gray-700"
                    placeholder="John"
                    #firstNameCtrl="ngModel"
                />
                <div *ngIf="firstNameCtrl.invalid && firstNameCtrl.touched" class="text-red-400 text-sm">First name is required (max 100 characters).</div>
            </div>

            <div class="my-8"></div>

            <!-- Last Name -->
            <div>
                <label class="block text-sm font-semibold mb-2"> Last Name <span class="text-red-400">*</span> </label>
                <input
                    [(ngModel)]="candidate.lastName"
                    (ngModelChange)="onCandidateChange()"
                    name="lastName"
                    type="text"
                    required
                    maxlength="100"
                    class="w-full p-2 rounded bg-gray-800 text-white border border-gray-700"
                    placeholder="Doe"
                    #lastNameCtrl="ngModel"
                />
                <div *ngIf="lastNameCtrl.invalid && lastNameCtrl.touched" class="text-red-400 text-sm">Last name is required (max 100 characters).</div>
            </div>

            <hr class="my-6 border-gray-600" />

            <!-- Email -->
            <h3 class="text-lg text-blue-400 font-bold mt-8">Email Address</h3>
            <div class="mt-3">
                <label class="block text-sm font-semibold mb-2"> Email <span class="text-red-400">*</span> </label>
                <input
                    [(ngModel)]="candidate.email"
                    (ngModelChange)="onCandidateChange()"
                    name="email"
                    type="email"
                    required
                    maxlength="100"
                    class="w-full p-2 rounded bg-gray-800 text-white border border-gray-700"
                    placeholder="john@example.com"
                    #emailCtrl="ngModel"
                />
                <div *ngIf="emailCtrl.invalid && emailCtrl.touched" class="text-red-400 text-sm">Valid email is required (max 100 characters).</div>
            </div>

            <hr class="my-6 border-gray-600" />

            <!-- Phone Number -->
            <h3 class="text-lg text-blue-400 font-bold mt-8">Phone</h3>
            <div class="mt-3">
                <label class="block text-sm font-semibold mb-2">Phone Number</label>
                <input [(ngModel)]="candidate.phoneNumber" (ngModelChange)="onCandidateChange()" name="phoneNumber" type="text" maxlength="20" class="w-full p-2 rounded bg-gray-800 text-white border border-gray-700" placeholder="+1 555 555 5555" />
            </div>
        </div>
    `
})
export class MyInformationComponent {
    @Input() candidate!: { firstName: string; lastName: string; email: string; phoneNumber: string };
    @Output() candidateChange = new EventEmitter<any>();

    onCandidateChange() {
        this.candidateChange.emit(this.candidate);
    }
}
