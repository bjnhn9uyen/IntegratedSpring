import { Injectable } from '@angular/core';
import { RestService } from '../services/rest.service';

@Injectable({
  providedIn: 'root',
})
export class RecentsService {
  constructor(private apiService: RestService) {}

  getRecentTacos(): object {
    return this.apiService.get('/design/recent');
  }
}
