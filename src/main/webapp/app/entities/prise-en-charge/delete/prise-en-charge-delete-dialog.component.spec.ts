jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PriseEnChargeService } from '../service/prise-en-charge.service';

import { PriseEnChargeDeleteDialogComponent } from './prise-en-charge-delete-dialog.component';

describe('PriseEnCharge Management Delete Component', () => {
  let comp: PriseEnChargeDeleteDialogComponent;
  let fixture: ComponentFixture<PriseEnChargeDeleteDialogComponent>;
  let service: PriseEnChargeService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PriseEnChargeDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(PriseEnChargeDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PriseEnChargeDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PriseEnChargeService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
