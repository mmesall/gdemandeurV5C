import { IUser } from 'app/entities/user/user.model';
import { IDemande } from 'app/entities/demande/demande.model';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { TypeDemandeur } from 'app/entities/enumerations/type-demandeur.model';
import { NomRegion } from 'app/entities/enumerations/nom-region.model';
import { DiplomeRequis } from 'app/entities/enumerations/diplome-requis.model';
import { NiveauEtude } from 'app/entities/enumerations/niveau-etude.model';

export interface IDossier {
  id?: number;
  numDossier?: string | null;
  typeDemandeur?: TypeDemandeur | null;
  nom?: string | null;
  prenom?: string | null;
  adresse?: string | null;
  region?: NomRegion | null;
  telephone?: string | null;
  email?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  cvContentType?: string | null;
  cv?: string | null;
  lettreMotivation?: string | null;
  diplomeRequis?: DiplomeRequis | null;
  niveauEtude?: NiveauEtude | null;
  profession?: string | null;
  user?: IUser | null;
  demandes?: IDemande[] | null;
  eleve?: IEleve | null;
  etudiant?: IEtudiant | null;
  professionnel?: IProfessionnel | null;
  demandeur?: IDemandeur | null;
}

export class Dossier implements IDossier {
  constructor(
    public id?: number,
    public numDossier?: string | null,
    public typeDemandeur?: TypeDemandeur | null,
    public nom?: string | null,
    public prenom?: string | null,
    public adresse?: string | null,
    public region?: NomRegion | null,
    public telephone?: string | null,
    public email?: string | null,
    public photoContentType?: string | null,
    public photo?: string | null,
    public cvContentType?: string | null,
    public cv?: string | null,
    public lettreMotivation?: string | null,
    public diplomeRequis?: DiplomeRequis | null,
    public niveauEtude?: NiveauEtude | null,
    public profession?: string | null,
    public user?: IUser | null,
    public demandes?: IDemande[] | null,
    public eleve?: IEleve | null,
    public etudiant?: IEtudiant | null,
    public professionnel?: IProfessionnel | null,
    public demandeur?: IDemandeur | null
  ) {}
}

export function getDossierIdentifier(dossier: IDossier): number | undefined {
  return dossier.id;
}
