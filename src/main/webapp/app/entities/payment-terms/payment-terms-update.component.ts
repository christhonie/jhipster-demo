import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPaymentTerms, PaymentTerms } from 'app/shared/model/payment-terms.model';
import { PaymentTermsService } from './payment-terms.service';

@Component({
  selector: 'jhi-payment-terms-update',
  templateUrl: './payment-terms-update.component.html'
})
export class PaymentTermsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(20)]],
    days: [null, [Validators.required]]
  });

  constructor(protected paymentTermsService: PaymentTermsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paymentTerms }) => {
      this.updateForm(paymentTerms);
    });
  }

  updateForm(paymentTerms: IPaymentTerms) {
    this.editForm.patchValue({
      id: paymentTerms.id,
      name: paymentTerms.name,
      days: paymentTerms.days
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paymentTerms = this.createFromForm();
    if (paymentTerms.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentTermsService.update(paymentTerms));
    } else {
      this.subscribeToSaveResponse(this.paymentTermsService.create(paymentTerms));
    }
  }

  private createFromForm(): IPaymentTerms {
    const entity = {
      ...new PaymentTerms(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      days: this.editForm.get(['days']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentTerms>>) {
    result.subscribe((res: HttpResponse<IPaymentTerms>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
