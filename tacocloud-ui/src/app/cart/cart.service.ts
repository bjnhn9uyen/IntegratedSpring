import { Injectable } from '@angular/core';
import { CartItem } from './cart-item';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  items$: CartItem[] = [];

  constructor() {
    this.items$ = [];
  }

  addToCart(taco: any): void {
    this.items$.push(new CartItem(taco));
  }

  getItemsInCart(): any {
    return this.items$;
  }

  getCartTotal(): any {
    let total = 0;
    this.items$.forEach((item) => {
      total += item.lineTotal;
    });
    return total;
  }

  emptyCart(): void {
    this.items$ = [];
  }
}
