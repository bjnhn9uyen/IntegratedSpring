import { Component, Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router/';
import { CartService } from '../cart/cart.service';

@Component({
  selector: 'app-design',
  templateUrl: './design.component.html',
  styleUrls: ['./design.component.css'],
})
// if you forget to include the decorator, the class might not be injectable into your application
@Injectable()
export class DesignComponent implements OnInit {
  // In order to be able to use two-way data binding for form inputs you need to import the FormsModule package in your App module
  // Two-way data binding [(...)] gives your app a way to share/sync data between a component class and its template
  // This way, as the user types into the text field in the template, the value will be immediately available for the controller to consume.
  // In this case, data to share/sycn is 'model', and data binding is [(ngModel)] in design.component.html
  model = {
    name: '',
    ingredients: [],
  };

  allIngredients: any;
  wraps = [];
  proteins = [];
  veggies = [];
  cheeses = [];
  sauces = [];

  constructor(
    private httpClient: HttpClient,
    private router: Router,
    private cart: CartService
  ) {}

  ngOnInit(): void {
    this.httpClient
      .get('http://localhost:8080/ingredients')
      .subscribe((data) => {
        this.allIngredients = data;
        this.wraps = this.allIngredients.filter((w) => w.type === 'WRAP');
        this.proteins = this.allIngredients.filter((p) => p.type === 'PROTEIN');
        this.veggies = this.allIngredients.filter((v) => v.type === 'VEGGIES');
        this.cheeses = this.allIngredients.filter((c) => c.type === 'CHEESE');
        this.sauces = this.allIngredients.filter((s) => s.type === 'SAUCE');
      });
  }

  updateIngredients(ingredient, event): void {
    if (event.target.checked) {
      this.model.ingredients.push(ingredient);
    } else {
      this.model.ingredients.splice(
        this.model.ingredients.findIndex((i) => i === ingredient),
        1
      );
    }
  }

  // the post() method is called instead of get(). This means that instead of fetching data from the API, you’re sending data to the API
  // Specifically, you’re sending a taco design, which is held in the 'model' variable, to the API endpoint at /design with a POST request
  // This means that you’ll need to write a method in DesignTacoController (back-end side) to handle that request and save the design
  onSubmit(): void {
    this.httpClient
      .post('http://localhost:8080/design', this.model, {
        headers: new HttpHeaders().set('Content-type', 'application/json'),
      })
      .subscribe((taco) => this.cart.addToCart(taco));

    this.router.navigate(['/cart']);
  }
}
