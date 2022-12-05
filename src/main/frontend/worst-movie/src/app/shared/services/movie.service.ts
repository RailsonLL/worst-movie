import { Movie } from './../models/movie';
import { ProducerWinIntervalMinMax } from '../models/producer-win-interval-min-max';
import { environment } from './../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { YearWithMultipleWinners } from '../models/year-with-multiple-winners';
import { PageResult } from '../utils/page-result';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private readonly api = `${environment.api}/api/movie`;

  constructor( private http: HttpClient ) { }

  public getYearWithMultipleWinners(): Observable<YearWithMultipleWinners[]> {
    return this.http.get<YearWithMultipleWinners[]>(`${this.api}/years-with-multiple-winners`);
  }

  public getProducerWinIntervalMinMax(): Observable<ProducerWinIntervalMinMax> {
    return this.http.get<ProducerWinIntervalMinMax>(`${this.api}/max-min-win-interval-for-producers`);
  }

  public getYearsList(): Observable<number[]> {
    return this.http.get<number[]>(`${this.api}/years-list`);
  }

  public getWinnerYearsList(): Observable<number[]> {
    return this.http.get<number[]>(`${this.api}/winner-years-list`);
  }

  public getWinnerMoviesByYear(year: number): Observable<Movie[]> {
    return this.http.get<Movie[]>(`${this.api}/movies-by-winner-and-year?winner=true&year=${year}`);
  }

  /* public getMoviesByYearAndWinnerPage(year: any, winner: any, page: number, size: number): Observable<PageResult<Movie>> {
    return this.http.get<PageResult<Movie>>(`${this.api}/movies-page-by-winner-year?page=${page}&size=${size}&winner=${winner}&year=${year}`);
  } */

  public getMoviesByYearAndWinnerPage(year: any, winner: any, page: number, size: number): Observable<any> {
    return this.http.get(`${this.api}/movies-page-by-winner-year?page=${page}&size=${size}&winner=${winner}&year=${year}`);
  }

}
