import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface JobOffer {
    id: number;
    title: string;
    description: string;
    contrat: string;
    secteur: string;
    hardSkills: { id: number; title: string; level: string; description?: string }[];
    status: string;
    closingDate: string;
    taskMissions: { id: number; title: string }[];
    createdAt: string;
    updatedAt: string;
}

@Injectable({
    providedIn: 'root'
})
export class JobOfferService {
    private apiUrl = 'http://localhost:8081/api/job-offers';

    constructor(private http: HttpClient) {}

    getAllJobOffers(page: number = 0, size: number = 10): Observable<JobOffer[]> {
        return this.http.get<JobOffer[]>(`${this.apiUrl}?page=${page}&size=${size}`);
    }

    getOfferById(id: number): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/${id}`);
    }
}
