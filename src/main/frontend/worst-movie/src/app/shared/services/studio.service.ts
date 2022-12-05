import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { WinnerStudio } from '../models/winners-studio';

@Injectable({
  providedIn: 'root'
})
export class StudioService {

  private readonly api = `${environment.api}/api/studios`;

  constructor( private http: HttpClient ) { }

  public getTop3WinnerStudios(): Observable<WinnerStudio[]> {
    return this.http.get<WinnerStudio[]>(`${this.api}/studios-with-win-count`);
  }
}
