import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JavatrainingSharedModule } from 'app/shared';
import {
  InvoiceLineItemComponent,
  InvoiceLineItemDetailComponent,
  InvoiceLineItemUpdateComponent,
  InvoiceLineItemDeletePopupComponent,
  InvoiceLineItemDeleteDialogComponent,
  invoiceLineItemRoute,
  invoiceLineItemPopupRoute
} from './';

const ENTITY_STATES = [...invoiceLineItemRoute, ...invoiceLineItemPopupRoute];

@NgModule({
  imports: [JavatrainingSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InvoiceLineItemComponent,
    InvoiceLineItemDetailComponent,
    InvoiceLineItemUpdateComponent,
    InvoiceLineItemDeleteDialogComponent,
    InvoiceLineItemDeletePopupComponent
  ],
  entryComponents: [
    InvoiceLineItemComponent,
    InvoiceLineItemUpdateComponent,
    InvoiceLineItemDeleteDialogComponent,
    InvoiceLineItemDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JavatrainingInvoiceLineItemModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
