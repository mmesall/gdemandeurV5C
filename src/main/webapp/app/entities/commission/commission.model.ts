import { IMembre } from 'app/entities/membre/membre.model';
import { IServices } from 'app/entities/services/services.model';

export interface ICommission {
  id?: number;
  nomCommission?: string | null;
  mission?: string | null;
  membres?: IMembre[] | null;
  services?: IServices | null;
}

export class Commission implements ICommission {
  constructor(
    public id?: number,
    public nomCommission?: string | null,
    public mission?: string | null,
    public membres?: IMembre[] | null,
    public services?: IServices | null
  ) {}
}

export function getCommissionIdentifier(commission: ICommission): number | undefined {
  return commission.id;
}
