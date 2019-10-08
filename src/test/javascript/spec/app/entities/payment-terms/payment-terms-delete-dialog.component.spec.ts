/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JavatrainingTestModule } from '../../../test.module';
import { PaymentTermsDeleteDialogComponent } from 'app/entities/payment-terms/payment-terms-delete-dialog.component';
import { PaymentTermsService } from 'app/entities/payment-terms/payment-terms.service';

describe('Component Tests', () => {
  describe('PaymentTerms Management Delete Component', () => {
    let comp: PaymentTermsDeleteDialogComponent;
    let fixture: ComponentFixture<PaymentTermsDeleteDialogComponent>;
    let service: PaymentTermsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JavatrainingTestModule],
        declarations: [PaymentTermsDeleteDialogComponent]
      })
        .overrideTemplate(PaymentTermsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentTermsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentTermsService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
