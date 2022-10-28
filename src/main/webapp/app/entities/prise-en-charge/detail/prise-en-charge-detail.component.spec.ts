import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PriseEnChargeDetailComponent } from './prise-en-charge-detail.component';

describe('PriseEnCharge Management Detail Component', () => {
  let comp: PriseEnChargeDetailComponent;
  let fixture: ComponentFixture<PriseEnChargeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PriseEnChargeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ priseEnCharge: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PriseEnChargeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PriseEnChargeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load priseEnCharge on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.priseEnCharge).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
