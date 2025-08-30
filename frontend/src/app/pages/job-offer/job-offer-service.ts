// src/app/pages/job-offer/service/job-offer.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class JobOfferService {
  private apiUrl = 'http://localhost:8081/api/job-offers';

  constructor(private http: HttpClient) {}

  createJobOffer(payload: any): Observable<any> {
    return this.http.post(this.apiUrl, payload);
  }
}
