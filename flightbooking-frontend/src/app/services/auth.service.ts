import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private BASE_URL = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  signup(data: any): Observable<any> {
    return this.http.post(
      `${this.BASE_URL}/signup`,
      data,
      { responseType: 'text' }  
    );
  }

  login(data: any): Observable<any> {
    return this.http.post<any>(
      `${this.BASE_URL}/login`,
      data
    );
  }
}
