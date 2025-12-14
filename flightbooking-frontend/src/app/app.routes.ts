import { Routes } from '@angular/router';
import { Login } from './auth/login/login';
import { Register } from './auth/register/register';
import { FlightSearch } from './flights/search/search';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'search', component: FlightSearch },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];
