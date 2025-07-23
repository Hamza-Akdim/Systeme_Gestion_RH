import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'offer-card',
    standalone: true,
    imports: [CommonModule],
    template: `
        <div class="text-white rounded-2xl p-6 relative shadow-lg border border-white">
            <div class="text-xl font-semibold mb-2">{{ offre.titre }}</div>

            <div class="flex items-center text-sm mb-4 gap-4">
                <div class="flex items-center gap-1">
                    <span class="material-icons text-base">place</span>
                    {{ offre.localisation }}
                </div>
                <div class="flex items-center gap-1">
                    <span class="material-icons text-base">domain</span>
                    {{ offre.domaine }}
                </div>
            </div>

            <div class="text-sm text-gray-300 mb-4">{{ offre.description | slice: 0 : 180 }}...</div>

            <button class="absolute bottom-6 right-6 bg-white text-[#0e1242] py-2 px-4 rounded-full font-semibold">VOIR L'OFFRE →</button>

            <div class="absolute top-4 right-4 text-pink-400">
                <span class="material-icons text-2xl">star_border</span>
            </div>
        </div>
    `,
    styles: []
})
export class OfferCardComponent {
    @Input() offre!: {
        titre: string;
        localisation: string;
        domaine: string;
        description: string;
    };
}
