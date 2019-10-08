/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JavatrainingTestModule } from '../../../test.module';
import { PaymentTermsUpdateComponent } from 'app/entities/payment-terms/payment-terms-update.component';
import { PaymentTermsService } from 'app/entities/payment-terms/payment-terms.service';
import { PaymentTerms } from 'app/shared/model/payment-terms.model';

describe('Component Tests', () => {
  describe('PaymentTerms Management Update Component', () => {
    let comp: PaymentTermsUpdateComponent;
    let fixture: ComponentFixture<PaymentTermsUpdateComponent>;
    let service: PaymentTermsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JavatrainingTestModule],
        declarations: [PaymentTermsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PaymentTermsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentTermsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentTermsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentTerms(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentTerms();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
