import dayjs from 'dayjs/esm';
import { IFormation } from 'app/entities/formation/formation.model';

export interface IConcours {
  id?: number;
  nomConcours?: string | null;
  dateOuverture?: dayjs.Dayjs | null;
  dateCloture?: dayjs.Dayjs | null;
  dateConcours?: dayjs.Dayjs | null;
  formation?: IFormation | null;
}

export class Concours implements IConcours {
  constructor(
    public id?: number,
    public nomConcours?: string | null,
    public dateOuverture?: dayjs.Dayjs | null,
    public dateCloture?: dayjs.Dayjs | null,
    public dateConcours?: dayjs.Dayjs | null,
    public formation?: IFormation | null
  ) {}
}

export function getConcoursIdentifier(concours: IConcours): number | undefined {
  return concours.id;
}
