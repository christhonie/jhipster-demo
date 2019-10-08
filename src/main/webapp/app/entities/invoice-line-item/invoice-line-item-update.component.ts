import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IInvoiceLineItem, InvoiceLineItem } from 'app/shared/model/invoice-line-item.model';
import { InvoiceLineItemService } from './invoice-line-item.service';
import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from 'app/entities/invoice';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item';

@Component({
  selector: 'jhi-invoice-line-item-update',
  templateUrl: './invoice-line-item-update.component.html'
})
export class InvoiceLineItemUpdateComponent implements OnInit {
  isSaving: boolean;

  invoices: IInvoice[];

  items: IItem[];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]],
    description: [null, [Validators.maxLength(100)]],
    amount: [null, [Validators.required]],
    invoice: [null, Validators.required],
    item: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected invoiceLineItemService: InvoiceLineItemService,
    protected invoiceService: InvoiceService,
    protected itemService: ItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ invoiceLineItem }) => {
      this.updateForm(invoiceLineItem);
    });
    this.invoiceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInvoice[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInvoice[]>) => response.body)
      )
      .subscribe((res: IInvoice[]) => (this.invoices = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.itemService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IItem[]>) => mayBeOk.ok),
        map((response: HttpResponse<IItem[]>) => response.body)
      )
      .subscribe((res: IItem[]) => (this.items = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(invoiceLineItem: IInvoiceLineItem) {
    this.editForm.patchValue({
      id: invoiceLineItem.id,
      quantity: invoiceLineItem.quantity,
      description: invoiceLineItem.description,
      amount: invoiceLineItem.amount,
      invoice: invoiceLineItem.invoice,
      item: invoiceLineItem.item
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const invoiceLineItem = this.createFromForm();
    if (invoiceLineItem.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceLineItemService.update(invoiceLineItem));
    } else {
      this.subscribeToSaveResponse(this.invoiceLineItemService.create(invoiceLineItem));
    }
  }

  private createFromForm(): IInvoiceLineItem {
    const entity = {
      ...new InvoiceLineItem(),
      id: this.editForm.get(['id']).value,
      quantity: this.editForm.get(['quantity']).value,
      description: this.editForm.get(['description']).value,
      amount: this.editForm.get(['amount']).value,
      invoice: this.editForm.get(['invoice']).value,
      item: this.editForm.get(['item']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceLineItem>>) {
    result.subscribe((res: HttpResponse<IInvoiceLineItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackInvoiceById(index: number, item: IInvoice) {
    return item.id;
  }

  trackItemById(index: number, item: IItem) {
    return item.id;
  }
}
