import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentTerms } from 'app/shared/model/payment-terms.model';

@Component({
  selector: 'jhi-payment-terms-detail',
  templateUrl: './payment-terms-detail.component.html'
})
export class PaymentTermsDetailComponent implements OnInit {
  paymentTerms: IPaymentTerms;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paymentTerms }) => {
      this.paymentTerms = paymentTerms;
    });
  }

  previousState() {
    window.history.back();
  }
}
