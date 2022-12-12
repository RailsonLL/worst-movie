import { Movie } from './../shared/models/movie';
import { MovieService } from './../shared/services/movie.service';
import { Component, OnInit } from '@angular/core';
import { PageResult } from '../shared/utils/page-result';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  year: any = null;
  winnerOptin: any = null;

  pageSize = 15;
  pageResult: PageResult<Movie> = new PageResult<Movie>();
  yearsList!: number[];
  winnerOptions = ["Yes", "No"];

  constructor( private movieService: MovieService ) { }

  ngOnInit(): void {
    this.loadYearsList();
    this.loadMoviePage();
  }

  loadMoviePage() {
    this.movieService.getMoviesByYearAndWinnerPage(this.year, this.winnerOptin, 0, 207).subscribe({
      next: (response) => {
        this.pageResult = response;
        this.pageResult.page = 1;
      },
      error: (error) => {
        console.log('Error loading list movies');
      }
    })
  }

  loadYearsList() {
    this.movieService.getYearsList().subscribe({
      next: (response) => {
        this.yearsList = response;
      },
      error: (error) => {
        console.log('Error loading years list');
      }
    })
  }

  searchWinnerMoviesByYear() {
    this.loadMoviePage();
  }

  changeSelectedYear(event: any) {
    const value = event.target.value;
    this.year = value && value !== 'All years' ? event.target.value : null;
  }

  changeWinnerOption(event: any) {
    const value: string = event.target.value;
    if (value && !value.includes('Yes/No')) {
      this.winnerOptin = value.includes('Yes') ? true : false;
    }
    else this.winnerOptin = null;

    this.loadMoviePage();
  }

}
