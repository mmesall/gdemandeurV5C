import { IBailleur } from 'app/entities/bailleur/bailleur.model';
import { IFormation } from 'app/entities/formation/formation.model';

export interface IPriseEnCharge {
  id?: number;
  libelle?: string | null;
  montant?: number | null;
  nbrePC?: number | null;
  details?: string | null;
  bailleur?: IBailleur | null;
  formation?: IFormation | null;
}

export class PriseEnCharge implements IPriseEnCharge {
  constructor(
    public id?: number,
    public libelle?: string | null,
    public montant?: number | null,
    public nbrePC?: number | null,
    public details?: string | null,
    public bailleur?: IBailleur | null,
    public formation?: IFormation | null
  ) {}
}

export function getPriseEnChargeIdentifier(priseEnCharge: IPriseEnCharge): number | undefined {
  return priseEnCharge.id;
}
