import dayjs from 'dayjs/esm';
import { IFormation } from 'app/entities/formation/formation.model';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';

export interface ISessionForm {
  id?: number;
  nomSession?: string | null;
  annee?: string | null;
  dateDebutSess?: dayjs.Dayjs | null;
  dateFinSess?: dayjs.Dayjs | null;
  formation?: IFormation | null;
  etablissement?: IEtablissement | null;
}

export class SessionForm implements ISessionForm {
  constructor(
    public id?: number,
    public nomSession?: string | null,
    public annee?: string | null,
    public dateDebutSess?: dayjs.Dayjs | null,
    public dateFinSess?: dayjs.Dayjs | null,
    public formation?: IFormation | null,
    public etablissement?: IEtablissement | null
  ) {}
}

export function getSessionFormIdentifier(sessionForm: ISessionForm): number | undefined {
  return sessionForm.id;
}
