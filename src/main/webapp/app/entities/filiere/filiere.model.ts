import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { NomFiliere } from 'app/entities/enumerations/nom-filiere.model';
import { NiveauEtude } from 'app/entities/enumerations/niveau-etude.model';

export interface IFiliere {
  id?: number;
  nomFiliere?: NomFiliere | null;
  niveauEtude?: NiveauEtude | null;
  programme?: string | null;
  autreNiveauEtude?: string | null;
  autreFiliere?: string | null;
  etablissement?: IEtablissement | null;
}

export class Filiere implements IFiliere {
  constructor(
    public id?: number,
    public nomFiliere?: NomFiliere | null,
    public niveauEtude?: NiveauEtude | null,
    public programme?: string | null,
    public autreNiveauEtude?: string | null,
    public autreFiliere?: string | null,
    public etablissement?: IEtablissement | null
  ) {}
}

export function getFiliereIdentifier(filiere: IFiliere): number | undefined {
  return filiere.id;
}
