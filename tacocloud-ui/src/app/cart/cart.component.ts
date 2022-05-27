import { Component, Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CartService } from './cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
// if you forget to include the decorator, the class might not be injectable into your application
@Injectable()
export class CartComponent implements OnInit {
  model = {
    deliveryName: '',
    deliveryStreet: '',
    deliveryState: '',
    deliveryZip: '',
    ccNumber: '',
    ccExpiration: '',
    ccCVV: '',
    tacos: [],
  };

  constructor(private cart: CartService, private httpClient: HttpClient) {
    this.cart = cart;
  }

  ngOnInit(): void {}

  get cartItems(): any {
    return this.cart.getItemsInCart;
  }

  get cartTotal(): any {
    return this.cart.getCartTotal();
  }

  onSubmit(): void {
    // this.model.tacos = this.cart.getItemsInCart();
    this.cart.getItemsInCart().forEach((cartItem) => {
      this.model.tacos.push(cartItem.taco);
    });

    this.httpClient
      .post('http://localhost:8080/orders', this.model, {
        headers: new HttpHeaders()
          .set('Content-type', 'application/json')
          .set('Accept', 'application/json'),
      })
      .subscribe((r) => this.cart.emptyCart());

    // TODO: Do something after this...navigate to a thank you page or something
  }
}
