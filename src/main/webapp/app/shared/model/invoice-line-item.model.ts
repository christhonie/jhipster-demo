import { IInvoice } from 'app/shared/model/invoice.model';
import { IItem } from 'app/shared/model/item.model';

export interface IInvoiceLineItem {
  id?: number;
  quantity?: number;
  description?: string;
  amount?: number;
  invoice?: IInvoice;
  item?: IItem;
}

export class InvoiceLineItem implements IInvoiceLineItem {
  constructor(
    public id?: number,
    public quantity?: number,
    public description?: string,
    public amount?: number,
    public invoice?: IInvoice,
    public item?: IItem
  ) {}
}
