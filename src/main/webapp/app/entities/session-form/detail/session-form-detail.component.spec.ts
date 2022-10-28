import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SessionFormDetailComponent } from './session-form-detail.component';

describe('SessionForm Management Detail Component', () => {
  let comp: SessionFormDetailComponent;
  let fixture: ComponentFixture<SessionFormDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SessionFormDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ sessionForm: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SessionFormDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SessionFormDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sessionForm on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.sessionForm).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
