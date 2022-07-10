// Services are objects that abstract some common logic that you plan to reuse in multiple places.
// The intention of a service is to enable reuse of code. Any component can import them as necessary.
// A service might be a set of common methods that need to be shared,
// such as utilities to parse data formats or authentication logic that needs to be run in multiple places.
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class RestService {
  // injecting the HttpClient service
  constructor(private http: HttpClient) {}

  get(path: string): object {
    // The HttpClient service is called and returns an observable, which is a construct for handling asynchronous events (data from an API)
    return this.http.get('http://localhost:8080' + path);
  }
}

// The Angular CLI gives us a nice way to create a service that has the scaffolding we need to get started.
// To generate a service (--skipTests is optional): ng generate service /services/service-name --skipTests
// Because the CLI doesn’t automatically register the service with the App module,
// and we need to register HttpClient module with the application as well. Add these two imports in the src/app/app.module.ts file
// import { HttpClientModule } from '@angular/common/http';
// import { ServiceClassName } from './services/service-name.service';
// In the imports section, add 'HttpClientModule', and register the new ServiceClassName with the providers property
// to inform Angular that it should be made available for the module to use
// In the imports property, we also need to register the HttpClientModule
