import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { NomSerie } from 'app/entities/enumerations/nom-serie.model';
import { NiveauEtude } from 'app/entities/enumerations/niveau-etude.model';

export interface ISerie {
  id?: number;
  nomSerie?: NomSerie | null;
  niveauEtude?: NiveauEtude | null;
  programme?: string | null;
  autreSerie?: string | null;
  etablissement?: IEtablissement | null;
}

export class Serie implements ISerie {
  constructor(
    public id?: number,
    public nomSerie?: NomSerie | null,
    public niveauEtude?: NiveauEtude | null,
    public programme?: string | null,
    public autreSerie?: string | null,
    public etablissement?: IEtablissement | null
  ) {}
}

export function getSerieIdentifier(serie: ISerie): number | undefined {
  return serie.id;
}
