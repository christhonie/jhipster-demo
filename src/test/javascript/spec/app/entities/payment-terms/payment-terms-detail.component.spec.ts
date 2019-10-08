/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JavatrainingTestModule } from '../../../test.module';
import { PaymentTermsDetailComponent } from 'app/entities/payment-terms/payment-terms-detail.component';
import { PaymentTerms } from 'app/shared/model/payment-terms.model';

describe('Component Tests', () => {
  describe('PaymentTerms Management Detail Component', () => {
    let comp: PaymentTermsDetailComponent;
    let fixture: ComponentFixture<PaymentTermsDetailComponent>;
    const route = ({ data: of({ paymentTerms: new PaymentTerms(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JavatrainingTestModule],
        declarations: [PaymentTermsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PaymentTermsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentTermsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paymentTerms).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
