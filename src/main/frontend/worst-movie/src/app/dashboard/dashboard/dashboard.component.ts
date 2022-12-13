import { Movie } from '../../models/movie';
import { ProducerWinIntervalMinMax } from '../../models/producer-win-interval-min-max';
import { Component, OnInit } from '@angular/core';
import { WinnerStudio } from 'src/app/models/winners-studio';
import { StudioService } from 'src/app/services/studio.service';
import { YearWithMultipleWinners } from '../../models/year-with-multiple-winners';
import { MovieService } from '../../services/movie.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  year!: number;

  invalidSearch = false;

  yearWithMultipleWinners!: YearWithMultipleWinners[];
  topWinnerStudios!: WinnerStudio[];
  producerWinIntervalMinMax!: ProducerWinIntervalMinMax;
  yearsList!: number[];
  movieList!: Movie[]

  constructor( private movieService: MovieService,
               private studioService: StudioService
             ) { }

  ngOnInit(): void {
    this.loadYearWithMultipleWinners();
    this.loadTop3WinnerStudios();
    this.loadProducerWinIntervalMinMax();
    this.loadWinnerYearsList();
  }

  loadYearWithMultipleWinners() {
    this.movieService.getYearWithMultipleWinners().subscribe({
      next: (response) => {
        this.yearWithMultipleWinners = response;
      },
      error: (error) => {
        console.log('Error loading years with multiples winners');
      }
    })
  }

  loadTop3WinnerStudios() {
    this.studioService.getTop3WinnerStudios().subscribe({
      next: (response) => {
        this.topWinnerStudios = response;
      },
      error: (error) => {
        console.log('Error loading top 3 winners studios');
      }
    })
  }

  loadProducerWinIntervalMinMax() {
    this.movieService.getProducerWinIntervalMinMax().subscribe({
      next: (response) => {
        this.producerWinIntervalMinMax = response;
      },
      error: (error) => {
        console.log('Error loading max win interval for producers');
      }
    })
  }

  loadWinnerYearsList() {
    this.movieService.getWinnerYearsList().subscribe({
      next: (response) => {
        this.yearsList = response;
      },
      error: (error) => {
        console.log('Error loading winner years list');
      }
    })
  }

  searchWinnerMoviesByYear() {
    if (!this.year) {
      this.invalidSearch = true;
      return;
    }

    this.movieService.getWinnerMoviesByYear(this.year).subscribe({
      next: (response) => {
        this.movieList = response;
      },
      error: (error) => {
        console.log('Error loading winner movies list');
      }
    })
  }

  changeSelectValue(event: any) {
    this.invalidSearch = false;
    const value = event.target.value;
    this.year = value && value !== 'Shearch by year' ? event.target.value : null;
  }

}
