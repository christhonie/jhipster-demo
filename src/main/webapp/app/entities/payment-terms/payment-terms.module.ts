import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JavatrainingSharedModule } from 'app/shared';
import {
  PaymentTermsComponent,
  PaymentTermsDetailComponent,
  PaymentTermsUpdateComponent,
  PaymentTermsDeletePopupComponent,
  PaymentTermsDeleteDialogComponent,
  paymentTermsRoute,
  paymentTermsPopupRoute
} from './';

const ENTITY_STATES = [...paymentTermsRoute, ...paymentTermsPopupRoute];

@NgModule({
  imports: [JavatrainingSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PaymentTermsComponent,
    PaymentTermsDetailComponent,
    PaymentTermsUpdateComponent,
    PaymentTermsDeleteDialogComponent,
    PaymentTermsDeletePopupComponent
  ],
  entryComponents: [
    PaymentTermsComponent,
    PaymentTermsUpdateComponent,
    PaymentTermsDeleteDialogComponent,
    PaymentTermsDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JavatrainingPaymentTermsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
