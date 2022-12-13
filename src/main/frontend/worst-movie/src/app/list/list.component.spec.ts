import { MovieService } from '../services/movie.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListComponent } from './list.component';
import { of } from 'rxjs';

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

  it('should load a page with a list of films by year and winner selected', () => {
    const moviesPage = {
      page: 1,
      totalElements: 99,
      content: [{ id:1, year: 1990, title: 'Movie1', winner: true }]
    }

    spyOn(movieService, 'getMoviesByYearAndWinnerPage').and.returnValue(of(moviesPage));
    component.year = 1990;
    component.winnerOptin = true;
    component.loadMoviePage();

    expect(component.pageResult.page).toEqual(1);
    expect(component.pageResult.totalElements).toEqual(99);
    expect(component.pageResult.content[0]).toEqual( { id:1, year: 1990, title: 'Movie1', winner: true } );
    expect(movieService.getMoviesByYearAndWinnerPage).toHaveBeenCalled();
  });
});
