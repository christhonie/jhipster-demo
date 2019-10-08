import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { IPaymentTerms } from 'app/shared/model/payment-terms.model';

export interface IInvoice {
  id?: number;
  number?: string;
  date?: Moment;
  customer?: ICustomer;
  terms?: IPaymentTerms;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public number?: string,
    public date?: Moment,
    public customer?: ICustomer,
    public terms?: IPaymentTerms
  ) {}
}
