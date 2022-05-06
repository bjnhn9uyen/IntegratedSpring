import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RecentsComponent } from './recents/recents.component';
import { CloudTitleComponent } from './cloud-title/cloud-title.component';
import { DesignComponent } from './design/design.component';
import { GroupBoxComponent } from './group-box/group-box.component';
import { CartComponent } from './cart/cart.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { BigButtonComponent } from './big-button/big-button.component';
import { RestService } from './services/rest.service';
import { CartService } from './cart/cart.service';
import { RecentsService } from './recents/recents.service';
import { HomeComponent } from './home/home.component';
import { LittleButtonComponent } from './little-button/little-button.component';
import { LocationsComponent } from './locations/locations.component';
import { LoginComponent } from './login/login.component';
import { SpecialsComponent } from './specials/specials.component';
import { WrapsPipe } from './recents/wraps.pipe';
import { NonWrapsPipe } from './recents/non-wraps.pipe';

// The NgModule decorator takes 'AppModule' as an object with a few different properties
@NgModule({
  // The declarations property is to provide a list of any components and directives to make available to the ENTIRE application
  declarations: [
    AppComponent,
    RecentsComponent,
    CloudTitleComponent,
    DesignComponent,
    GroupBoxComponent,
    CartComponent,
    FooterComponent,
    HeaderComponent,
    BigButtonComponent,
    HomeComponent,
    LittleButtonComponent,
    LocationsComponent,
    LoginComponent,
    SpecialsComponent,
    WrapsPipe,
    NonWrapsPipe,
  ],
  // The imports property is an array of other modules upon which this module depends (such as third-party modules or ones you’ve created)
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule],
  // The providers property is empty by default. Any services that are created are to be listed here
  providers: [RestService, CartService, RecentsService],
  // The bootstrap property defines which components to bootstrap at runtime
  bootstrap: [AppComponent],
})
// Exports an empty class, which gets annotated with configuration from NgModule
export class AppModule {}
