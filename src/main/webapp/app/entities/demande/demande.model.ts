import { IFormation } from 'app/entities/formation/formation.model';
import { IPieceJointe } from 'app/entities/piece-jointe/piece-jointe.model';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { EtatDemande } from 'app/entities/enumerations/etat-demande.model';

export interface IDemande {
  id?: number;
  libelle?: string | null;
  niveauEtude?: string | null;
  etatDemande?: EtatDemande | null;
  formation?: IFormation | null;
  pieceJointes?: IPieceJointe[] | null;
  dossier?: IDossier | null;
}

export class Demande implements IDemande {
  constructor(
    public id?: number,
    public libelle?: string | null,
    public niveauEtude?: string | null,
    public etatDemande?: EtatDemande | null,
    public formation?: IFormation | null,
    public pieceJointes?: IPieceJointe[] | null,
    public dossier?: IDossier | null
  ) {}
}

export function getDemandeIdentifier(demande: IDemande): number | undefined {
  return demande.id;
}
