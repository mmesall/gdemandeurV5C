import { IFiliere } from 'app/entities/filiere/filiere.model';
import { ISerie } from 'app/entities/serie/serie.model';
import { ISessionForm } from 'app/entities/session-form/session-form.model';
import { ILocalite } from 'app/entities/localite/localite.model';
import { TypeEtablissement } from 'app/entities/enumerations/type-etablissement.model';

export interface IEtablissement {
  id?: number;
  nomEtablissement?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  adresse?: string | null;
  email?: string | null;
  telephone?: number | null;
  typeEtablissement?: TypeEtablissement | null;
  filieres?: IFiliere[] | null;
  series?: ISerie[] | null;
  sessionForms?: ISessionForm[] | null;
  localite?: ILocalite | null;
}

export class Etablissement implements IEtablissement {
  constructor(
    public id?: number,
    public nomEtablissement?: string | null,
    public photoContentType?: string | null,
    public photo?: string | null,
    public adresse?: string | null,
    public email?: string | null,
    public telephone?: number | null,
    public typeEtablissement?: TypeEtablissement | null,
    public filieres?: IFiliere[] | null,
    public series?: ISerie[] | null,
    public sessionForms?: ISessionForm[] | null,
    public localite?: ILocalite | null
  ) {}
}

export function getEtablissementIdentifier(etablissement: IEtablissement): number | undefined {
  return etablissement.id;
}
