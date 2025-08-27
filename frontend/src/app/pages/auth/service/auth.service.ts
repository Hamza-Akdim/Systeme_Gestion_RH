import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

interface LoginRequest {
    username: string;
    password: string;
}

interface JwtResponse {
    token: string;
    id: number;
    username: string;
    email: string;
    roles: string[];
}

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = 'http://localhost:8081/api/auth';

    constructor(private http: HttpClient) {}

    login(credentials: LoginRequest): Observable<JwtResponse> {
        return this.http.post<JwtResponse>(`${this.apiUrl}/login`, credentials).pipe(
            tap((response) => {
                localStorage.setItem('auth_token', response.token);
                localStorage.setItem('id', response.id.toString());
                localStorage.setItem('username', response.username);
                localStorage.setItem('email', response.email);
                localStorage.setItem('roles', response.roles.toString());
            })
        );
    }

    logout(): void {
        localStorage.removeItem('auth_token');
    }

    getToken(): string | null {
        return localStorage.getItem('auth_token');
    }

    isLoggedIn(): boolean {
        return !!this.getToken();
    }

    getRole() : string | null {
        return localStorage.getItem("roles");
    }
}
