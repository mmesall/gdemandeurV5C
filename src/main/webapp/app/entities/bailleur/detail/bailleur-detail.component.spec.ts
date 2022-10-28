import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BailleurDetailComponent } from './bailleur-detail.component';

describe('Bailleur Management Detail Component', () => {
  let comp: BailleurDetailComponent;
  let fixture: ComponentFixture<BailleurDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BailleurDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bailleur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BailleurDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BailleurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bailleur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bailleur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
