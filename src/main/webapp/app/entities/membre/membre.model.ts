import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IAgent } from 'app/entities/agent/agent.model';
import { ICommission } from 'app/entities/commission/commission.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';

export interface IMembre {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  dateNaiss?: dayjs.Dayjs | null;
  lieuNaiss?: string | null;
  sexe?: Sexe | null;
  telephone?: number | null;
  adressePhysique?: string | null;
  email?: string | null;
  cni?: number | null;
  matricule?: string | null;
  user?: IUser | null;
  agent?: IAgent | null;
  commission?: ICommission | null;
}

export class Membre implements IMembre {
  constructor(
    public id?: number,
    public nom?: string | null,
    public prenom?: string | null,
    public dateNaiss?: dayjs.Dayjs | null,
    public lieuNaiss?: string | null,
    public sexe?: Sexe | null,
    public telephone?: number | null,
    public adressePhysique?: string | null,
    public email?: string | null,
    public cni?: number | null,
    public matricule?: string | null,
    public user?: IUser | null,
    public agent?: IAgent | null,
    public commission?: ICommission | null
  ) {}
}

export function getMembreIdentifier(membre: IMembre): number | undefined {
  return membre.id;
}
