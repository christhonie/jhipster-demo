import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'country',
        loadChildren: './country/country.module#JavatrainingCountryModule'
      },
      {
        path: 'customer',
        loadChildren: './customer/customer.module#JavatrainingCustomerModule'
      },
      {
        path: 'payment-terms',
        loadChildren: './payment-terms/payment-terms.module#JavatrainingPaymentTermsModule'
      },
      {
        path: 'item',
        loadChildren: './item/item.module#JavatrainingItemModule'
      },
      {
        path: 'invoice',
        loadChildren: './invoice/invoice.module#JavatrainingInvoiceModule'
      },
      {
        path: 'invoice-line-item',
        loadChildren: './invoice-line-item/invoice-line-item.module#JavatrainingInvoiceLineItemModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JavatrainingEntityModule {}
