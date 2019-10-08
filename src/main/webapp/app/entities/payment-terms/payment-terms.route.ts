import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PaymentTerms } from 'app/shared/model/payment-terms.model';
import { PaymentTermsService } from './payment-terms.service';
import { PaymentTermsComponent } from './payment-terms.component';
import { PaymentTermsDetailComponent } from './payment-terms-detail.component';
import { PaymentTermsUpdateComponent } from './payment-terms-update.component';
import { PaymentTermsDeletePopupComponent } from './payment-terms-delete-dialog.component';
import { IPaymentTerms } from 'app/shared/model/payment-terms.model';

@Injectable({ providedIn: 'root' })
export class PaymentTermsResolve implements Resolve<IPaymentTerms> {
  constructor(private service: PaymentTermsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPaymentTerms> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PaymentTerms>) => response.ok),
        map((paymentTerms: HttpResponse<PaymentTerms>) => paymentTerms.body)
      );
    }
    return of(new PaymentTerms());
  }
}

export const paymentTermsRoute: Routes = [
  {
    path: '',
    component: PaymentTermsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'javatrainingApp.paymentTerms.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PaymentTermsDetailComponent,
    resolve: {
      paymentTerms: PaymentTermsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'javatrainingApp.paymentTerms.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PaymentTermsUpdateComponent,
    resolve: {
      paymentTerms: PaymentTermsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'javatrainingApp.paymentTerms.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PaymentTermsUpdateComponent,
    resolve: {
      paymentTerms: PaymentTermsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'javatrainingApp.paymentTerms.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const paymentTermsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PaymentTermsDeletePopupComponent,
    resolve: {
      paymentTerms: PaymentTermsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'javatrainingApp.paymentTerms.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
