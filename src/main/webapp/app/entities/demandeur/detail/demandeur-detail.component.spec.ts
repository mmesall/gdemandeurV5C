import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemandeurDetailComponent } from './demandeur-detail.component';

describe('Demandeur Management Detail Component', () => {
  let comp: DemandeurDetailComponent;
  let fixture: ComponentFixture<DemandeurDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DemandeurDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ demandeur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DemandeurDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DemandeurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load demandeur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.demandeur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
