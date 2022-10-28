import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { ILocalite } from 'app/entities/localite/localite.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';

export interface IEtudiant {
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
  user?: IUser | null;
  dossier?: IDossier | null;
  demandeur?: IDemandeur | null;
  localite?: ILocalite | null;
}

export class Etudiant implements IEtudiant {
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
    public user?: IUser | null,
    public dossier?: IDossier | null,
    public demandeur?: IDemandeur | null,
    public localite?: ILocalite | null
  ) {}
}

export function getEtudiantIdentifier(etudiant: IEtudiant): number | undefined {
  return etudiant.id;
}
