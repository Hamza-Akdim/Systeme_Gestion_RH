import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Langue } from './langue.model';

@Injectable({ providedIn: 'root' })
export class LangueService {
  private readonly baseUrl = '/api/langues';   // ↙︎ adapté à ton backend

  constructor(private http: HttpClient) {}

  list(): Observable<Langue[]>             { return this.http.get<Langue[]>(this.baseUrl); }
  add(lang: Langue): Observable<Langue>    { return this.http.post<Langue>(this.baseUrl, lang); }
  update(lang: Langue): Observable<Langue> { return this.http.put<Langue>(`${this.baseUrl}/${lang.idL}`, lang); }
  remove(id: number): Observable<void>     { return this.http.delete<void>(`${this.baseUrl}/${id}`); }
}
