export interface IPaymentTerms {
  id?: number;
  name?: string;
  days?: number;
}

export class PaymentTerms implements IPaymentTerms {
  constructor(public id?: number, public name?: string, public days?: number) {}
}
