import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IBailleur } from 'app/entities/bailleur/bailleur.model';
import { IMembre } from 'app/entities/membre/membre.model';
import { IServices } from 'app/entities/services/services.model';
import { ILocalite } from 'app/entities/localite/localite.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';

export interface IAgent {
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
  bailleur?: IBailleur | null;
  membre?: IMembre | null;
  services?: IServices | null;
  localite?: ILocalite | null;
}

export class Agent implements IAgent {
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
    public bailleur?: IBailleur | null,
    public membre?: IMembre | null,
    public services?: IServices | null,
    public localite?: ILocalite | null
  ) {}
}

export function getAgentIdentifier(agent: IAgent): number | undefined {
  return agent.id;
}
