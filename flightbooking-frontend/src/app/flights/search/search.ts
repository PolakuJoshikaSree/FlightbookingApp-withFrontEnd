import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-flight-search',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './search.html',
  styleUrls: ['./search.css']
})
export class FlightSearch {

  source = '';
  destination = '';

  // 🔹 STATIC FLIGHT LIST (MOCK DATA)
  allFlights = [
    { flightNumber: 'AI101', source: 'VJA', destination: 'BLR', price: 4500 },
    { flightNumber: 'IND202', source: 'VJA', destination: 'HYD', price: 3800 },
    { flightNumber: 'VJA303', source: 'BLR', destination: 'DEL', price: 6200 },
    { flightNumber: 'GO404', source: 'VJA', destination: 'BLR', price: 4800 }
  ];

  flights: any[] = [];

  searchFlights() {
    this.flights = this.allFlights.filter(f =>
      f.source.toLowerCase() === this.source.toLowerCase() &&
      f.destination.toLowerCase() === this.destination.toLowerCase()
    );
  }
}
