import dayjs from 'dayjs/esm';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { IUser } from 'app/entities/user/user.model';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { Profil } from 'app/entities/enumerations/profil.model';

export interface IDemandeur {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  dateNaiss?: dayjs.Dayjs | null;
  lieuNaiss?: string | null;
  sexe?: Sexe | null;
  telephone?: number | null;
  email?: string | null;
  profil?: Profil | null;
  dossier?: IDossier | null;
  user?: IUser | null;
  eleve?: IEleve | null;
  etudiant?: IEtudiant | null;
  professionnel?: IProfessionnel | null;
}

export class Demandeur implements IDemandeur {
  constructor(
    public id?: number,
    public nom?: string | null,
    public prenom?: string | null,
    public dateNaiss?: dayjs.Dayjs | null,
    public lieuNaiss?: string | null,
    public sexe?: Sexe | null,
    public telephone?: number | null,
    public email?: string | null,
    public profil?: Profil | null,
    public dossier?: IDossier | null,
    public user?: IUser | null,
    public eleve?: IEleve | null,
    public etudiant?: IEtudiant | null,
    public professionnel?: IProfessionnel | null
  ) {}
}

export function getDemandeurIdentifier(demandeur: IDemandeur): number | undefined {
  return demandeur.id;
}
