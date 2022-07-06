import { Component, OnInit, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-recent-tacos',
  templateUrl: './recents.component.html',
  styleUrls: ['./recents.component.css'],
})
// if you forget to include the decorator, the class might not be injectable into your application
@Injectable()
export class RecentsComponent implements OnInit {
  recentTacos: any;

  // uses the injected Http module to perform an HTTP GET request
  constructor(private httpClient: HttpClient) {}

  ngOnInit(): void {
    // Using observables to expect that the response will contain a list of taco designs
    // To use observables, you subscribe to the stream of data and pass a function that will run every time there’s a new piece of data
    // When the data loads, it will store it on the recentTacos property. This is like an event listener, except that it comes in a stream
    // In this case, recentTacos property stores a JSON object received from back-end API
    this.httpClient
      .get('http://localhost:8080/design/recent') // performs an HTTP GET request
      .subscribe((data) => (this.recentTacos = data));
  }
}
