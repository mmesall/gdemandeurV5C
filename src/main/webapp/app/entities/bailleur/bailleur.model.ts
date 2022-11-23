import { IAgent } from 'app/entities/agent/agent.model';
import { IPriseEnCharge } from 'app/entities/prise-en-charge/prise-en-charge.model';

export interface IBailleur {
  id?: number;
  nomProjet?: string | null;
  budgetPrevu?: number | null;
  budgetDepense?: number | null;
  budgetRestant?: number | null;
  nbrePC?: number | null;
  agent?: IAgent | null;
  priseEnCharges?: IPriseEnCharge[] | null;
}

export class Bailleur implements IBailleur {
  constructor(
    public id?: number,
    public nomProjet?: string | null,
    public budgetPrevu?: number | null,
    public budgetDepense?: number | null,
    public budgetRestant?: number | null,
    public nbrePC?: number | null,
    public agent?: IAgent | null,
    public priseEnCharges?: IPriseEnCharge[] | null
  ) {}
}

export function getBailleurIdentifier(bailleur: IBailleur): number | undefined {
  return bailleur.id;
}
