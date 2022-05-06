import { NgModule } from '@angular/core';
// The RouterModule is used to activate the router and accepts the routes configuration when it’s initialized
import { Routes, RouterModule } from '@angular/router';
// Imports the routable components, so we can reference them properly in our routes configuration
import { HomeComponent } from './home/home.component';
import { RecentsComponent } from './recents/recents.component';
import { DesignComponent } from './design/design.component';
import { CartComponent } from './cart/cart.component';
import { LoginComponent } from './login/login.component';
import { SpecialsComponent } from './specials/specials.component';
import { LocationsComponent } from './locations/locations.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'recents',
    component: RecentsComponent,
  },
  {
    path: 'design',
    component: DesignComponent,
  },
  {
    path: 'cart',
    component: CartComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'specials',
    component: SpecialsComponent,
  },
  {
    path: 'locations',
    component: LocationsComponent,
  },
  {
    path: '**',
    redirectTo: 'home',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
