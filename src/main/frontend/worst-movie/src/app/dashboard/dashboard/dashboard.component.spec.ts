import { StudioService } from 'src/app/shared/services/studio.service';
import { MovieService } from './../../shared/services/movie.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed, getTestBed } from '@angular/core/testing';

import { DashboardComponent } from './dashboard.component';
import { of } from 'rxjs';

describe('DashboardComponent', () => {
  let movieService: MovieService;
  let studioService: StudioService;
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ DashboardComponent ],
      providers: [ MovieService, StudioService ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    movieService = fixture.debugElement.injector.get(MovieService);
    studioService = fixture.debugElement.injector.get(StudioService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load years with multiple winners', () => {
    spyOn(movieService, 'getYearWithMultipleWinners').and.returnValue(of([{ year: 1986, winnerCount: 2 }]));
    component.loadYearWithMultipleWinners();

    expect(component.yearWithMultipleWinners[0]).toEqual( { year:1986, winnerCount:2 } );
    expect(movieService.getYearWithMultipleWinners).toHaveBeenCalled();
  });

  it('should load top 3 winners studios', () => {
    const winnerStudios = [
      {name: 'Studio1', winCount: 3},
      {name: 'Studio2', winCount: 3},
      {name: 'Studio3', winCount: 3}
    ]
    spyOn(studioService, 'getTop3WinnerStudios').and.returnValue(of(winnerStudios));
    component.loadTop3WinnerStudios();

    expect(component.topWinnerStudios[0]).toEqual( { name: 'Studio1', winCount: 3 } );
    expect(component.topWinnerStudios[1]).toEqual( { name: 'Studio2', winCount: 3 } );
    expect(component.topWinnerStudios[2]).toEqual( { name: 'Studio3', winCount: 3 } );
    expect(studioService.getTop3WinnerStudios).toHaveBeenCalled();
  });

  it('should load producers with longest and shortest interval between wins', () => {
    const intervalMinMax = {
      max: [{producer: 'Producer1', interval: 5, previousWin: 2000, followingWin: 2005}],
      min: [{producer: 'Producer2', interval: 1, previousWin: 2000, followingWin: 2001}]
    };
    spyOn(movieService, 'getProducerWinIntervalMinMax').and.returnValue(of(intervalMinMax));
    component.loadProducerWinIntervalMinMax();

    expect(component.producerWinIntervalMinMax.max).toEqual(
      [{producer: 'Producer1', interval: 5, previousWin: 2000, followingWin: 2005}]
    );
    expect(component.producerWinIntervalMinMax.min).toEqual(
      [{producer: 'Producer2', interval: 1, previousWin: 2000, followingWin: 2001}]
    );
    expect(movieService.getProducerWinIntervalMinMax).toHaveBeenCalled();
  });

  it('should load winner years list', () => {
    const winnerYears = [ 1980, 1981, 1982 ]
    spyOn(movieService, 'getWinnerYearsList').and.returnValue(of(winnerYears));
    component.loadWinnerYearsList();

    expect(component.yearsList).toEqual( [ 1980, 1981, 1982 ] );
    expect(movieService.getWinnerYearsList).toHaveBeenCalled();
  });

  it('should upload a list of winning films by selected year', () => {
    const winnerMovies = [
      { id:1, year: 1990, title: 'Movie1', winner: true },
      { id:2, year: 1991, title: 'Movie2', winner: true },
      { id:3, year: 1992, title: 'Movie3', winner: true }
    ]
    spyOn(movieService, 'getWinnerMoviesByYear').withArgs(123).and.returnValue(of(winnerMovies));
    component.searchWinnerMoviesByYear();

    expect(component.movieList[0]).toEqual( { id:1, year: 1990, title: 'Movie1', winner: true } );
    expect(component.movieList[1]).toEqual( { id:2, year: 1991, title: 'Movie2', winner: true } );
    expect(component.movieList[2]).toEqual( { id:3, year: 1992, title: 'Movie3', winner: true } );
    expect(movieService.getWinnerMoviesByYear).toHaveBeenCalled();
  });

});
