import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IInvoice, Invoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from './invoice.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';
import { IPaymentTerms } from 'app/shared/model/payment-terms.model';
import { PaymentTermsService } from 'app/entities/payment-terms';

@Component({
  selector: 'jhi-invoice-update',
  templateUrl: './invoice-update.component.html'
})
export class InvoiceUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomer[];

  paymentterms: IPaymentTerms[];

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.required, Validators.maxLength(10)]],
    date: [null, [Validators.required]],
    customer: [null, Validators.required],
    terms: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected invoiceService: InvoiceService,
    protected customerService: CustomerService,
    protected paymentTermsService: PaymentTermsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ invoice }) => {
      this.updateForm(invoice);
    });
    this.customerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomer[]>) => response.body)
      )
      .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.paymentTermsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPaymentTerms[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPaymentTerms[]>) => response.body)
      )
      .subscribe((res: IPaymentTerms[]) => (this.paymentterms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(invoice: IInvoice) {
    this.editForm.patchValue({
      id: invoice.id,
      number: invoice.number,
      date: invoice.date != null ? invoice.date.format(DATE_TIME_FORMAT) : null,
      customer: invoice.customer,
      terms: invoice.terms
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const invoice = this.createFromForm();
    if (invoice.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceService.update(invoice));
    } else {
      this.subscribeToSaveResponse(this.invoiceService.create(invoice));
    }
  }

  private createFromForm(): IInvoice {
    const entity = {
      ...new Invoice(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      date: this.editForm.get(['date']).value != null ? moment(this.editForm.get(['date']).value, DATE_TIME_FORMAT) : undefined,
      customer: this.editForm.get(['customer']).value,
      terms: this.editForm.get(['terms']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoice>>) {
    result.subscribe((res: HttpResponse<IInvoice>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCustomerById(index: number, item: ICustomer) {
    return item.id;
  }

  trackPaymentTermsById(index: number, item: IPaymentTerms) {
    return item.id;
  }
}
