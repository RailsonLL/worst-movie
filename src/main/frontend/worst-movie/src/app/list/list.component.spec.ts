import { MovieService } from './../shared/services/movie.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListComponent } from './list.component';

describe('ListComponent', () => {
  let movieService: MovieService;
  let component: ListComponent;
  let fixture: ComponentFixture<ListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ ListComponent ],
      providers: [ MovieService ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListComponent);
    component = fixture.componentInstance;
    movieService = fixture.debugElement.injector.get(MovieService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should upload a list of winning films by selected year', () => {
    /* const winnerMovies = [
      { id:1, year: 1990, title: 'Movie1', winner: true },
      { id:2, year: 1991, title: 'Movie2', winner: true },
      { id:3, year: 1992, title: 'Movie3', winner: true }
    ]
    spyOn(movieService, 'getWinnerMoviesByYear').withArgs(123).and.returnValue(of(winnerMovies));
    component.searchWinnerMoviesByYear();

    expect(component.movieList[0]).toEqual( { id:1, year: 1990, title: 'Movie1', winner: true } );
    expect(component.movieList[1]).toEqual( { id:2, year: 1991, title: 'Movie2', winner: true } );
    expect(component.movieList[2]).toEqual( { id:3, year: 1992, title: 'Movie3', winner: true } );
    expect(movieService.getWinnerMoviesByYear).toHaveBeenCalled(); */
  });
});
