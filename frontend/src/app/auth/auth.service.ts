import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class AuthService {


  private BASE_URL = "http://localhost:8181";
  private httpHeaders = new HttpHeaders({ 'No-Auth': 'true' });

  constructor(private http: HttpClient) { }

  login(username:string,password:string): Observable<any> {
    return this.http.post(`${this.BASE_URL}/authenticate`, {username,password},{ headers: this.httpHeaders }).pipe(
      catchError(this.handleError)
    );
    }

    registerUser(user: any): Observable<any> {
      return this.http.post(`${this.BASE_URL}/register`, user,{ headers: this.httpHeaders}).pipe(
        catchError(this.handleError));
    }

    private handleError(error: any): Observable<never> {
      console.error('An error occurred', error);  // For demo purposes only
      return throwError('Something bad happened; please try again later.');
    }

    setRoles(roles: []) {
      localStorage.setItem('roles', JSON.stringify(roles));
    }
  
    public getRoles(): [] {
      const storedRoles = localStorage.getItem('roles');
      return storedRoles ? JSON.parse(storedRoles) : [];
    }
  
    public setToken(token: string): void {
      localStorage.setItem('token', token);
    }
  
    public getToken(): string | null {
      return localStorage.getItem('token');
    }
  
    public clear() {
      localStorage.clear();
    }
  
    public isLoggedIn() {
      return this.getToken() && this.getRoles();
    }
}
