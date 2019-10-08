import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InvoiceLineItem } from 'app/shared/model/invoice-line-item.model';
import { InvoiceLineItemService } from './invoice-line-item.service';
import { InvoiceLineItemComponent } from './invoice-line-item.component';
import { InvoiceLineItemDetailComponent } from './invoice-line-item-detail.component';
import { InvoiceLineItemUpdateComponent } from './invoice-line-item-update.component';
import { InvoiceLineItemDeletePopupComponent } from './invoice-line-item-delete-dialog.component';
import { IInvoiceLineItem } from 'app/shared/model/invoice-line-item.model';

@Injectable({ providedIn: 'root' })
export class InvoiceLineItemResolve implements Resolve<IInvoiceLineItem> {
  constructor(private service: InvoiceLineItemService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInvoiceLineItem> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InvoiceLineItem>) => response.ok),
        map((invoiceLineItem: HttpResponse<InvoiceLineItem>) => invoiceLineItem.body)
      );
    }
    return of(new InvoiceLineItem());
  }
}

export const invoiceLineItemRoute: Routes = [
  {
    path: '',
    component: InvoiceLineItemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'javatrainingApp.invoiceLineItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InvoiceLineItemDetailComponent,
    resolve: {
      invoiceLineItem: InvoiceLineItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'javatrainingApp.invoiceLineItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InvoiceLineItemUpdateComponent,
    resolve: {
      invoiceLineItem: InvoiceLineItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'javatrainingApp.invoiceLineItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InvoiceLineItemUpdateComponent,
    resolve: {
      invoiceLineItem: InvoiceLineItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'javatrainingApp.invoiceLineItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const invoiceLineItemPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InvoiceLineItemDeletePopupComponent,
    resolve: {
      invoiceLineItem: InvoiceLineItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'javatrainingApp.invoiceLineItem.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
