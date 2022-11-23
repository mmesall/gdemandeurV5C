import { ICommission } from 'app/entities/commission/commission.model';
import { IAgent } from 'app/entities/agent/agent.model';

export interface IServices {
  id?: number;
  logoContentType?: string | null;
  logo?: string | null;
  nomService?: string | null;
  chefService?: string | null;
  description?: string | null;
  isPilotage?: boolean | null;
  commission?: ICommission | null;
  agents?: IAgent[] | null;
}

export class Services implements IServices {
  constructor(
    public id?: number,
    public logoContentType?: string | null,
    public logo?: string | null,
    public nomService?: string | null,
    public chefService?: string | null,
    public description?: string | null,
    public isPilotage?: boolean | null,
    public commission?: ICommission | null,
    public agents?: IAgent[] | null
  ) {
    this.isPilotage = this.isPilotage ?? false;
  }
}

export function getServicesIdentifier(services: IServices): number | undefined {
  return services.id;
}
