export interface IItem {
  id?: number;
  name?: string;
  price?: number;
}

export class Item implements IItem {
  constructor(public id?: number, public name?: string, public price?: number) {}
}
