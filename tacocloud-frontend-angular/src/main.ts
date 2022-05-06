import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

// If production is enabled, turn off Angular developer mode.
if (environment.production) {
  enableProdMode();
}

// Bootstraps the App module. The platformBrowserDynamic object is used to tell Angular which module is being loading (AppModule)
// Once the app is bootstrapped, Angular will look for the app-root element (in index.html) and replace it with the rendered component
platformBrowserDynamic()
  .bootstrapModule(AppModule)
  .catch((err) => console.error(err));
