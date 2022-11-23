import dayjs from 'dayjs/esm';
import { IPriseEnCharge } from 'app/entities/prise-en-charge/prise-en-charge.model';
import { IConcours } from 'app/entities/concours/concours.model';
import { ISessionForm } from 'app/entities/session-form/session-form.model';
import { IDemande } from 'app/entities/demande/demande.model';
import { TypeFormation } from 'app/entities/enumerations/type-formation.model';
import { Admission } from 'app/entities/enumerations/admission.model';
import { DiplomeRequis } from 'app/entities/enumerations/diplome-requis.model';
import { Secteur } from 'app/entities/enumerations/secteur.model';
import { NomDiplome } from 'app/entities/enumerations/nom-diplome.model';

export interface IFormation {
  id?: number;
  nomFormation?: string | null;
  typeFormation?: TypeFormation | null;
  duree?: string | null;
  admission?: Admission | null;
  diplomeRequis?: DiplomeRequis | null;
  autreDiplome?: string | null;
  secteur?: Secteur | null;
  autreSecteur?: string | null;
  ficheFormationContentType?: string | null;
  ficheFormation?: string | null;
  programmes?: string | null;
  nomConcours?: string | null;
  dateOuverture?: dayjs.Dayjs | null;
  dateCloture?: dayjs.Dayjs | null;
  dateConcours?: dayjs.Dayjs | null;
  libellePC?: string | null;
  montantPriseEnCharge?: number | null;
  detailPC?: string | null;
  nomDiplome?: NomDiplome | null;
  nomDebouche?: string | null;
  priseEnCharges?: IPriseEnCharge[] | null;
  concours?: IConcours[] | null;
  sessionForms?: ISessionForm[] | null;
  demande?: IDemande | null;
}

export class Formation implements IFormation {
  constructor(
    public id?: number,
    public nomFormation?: string | null,
    public typeFormation?: TypeFormation | null,
    public duree?: string | null,
    public admission?: Admission | null,
    public diplomeRequis?: DiplomeRequis | null,
    public autreDiplome?: string | null,
    public secteur?: Secteur | null,
    public autreSecteur?: string | null,
    public ficheFormationContentType?: string | null,
    public ficheFormation?: string | null,
    public programmes?: string | null,
    public nomConcours?: string | null,
    public dateOuverture?: dayjs.Dayjs | null,
    public dateCloture?: dayjs.Dayjs | null,
    public dateConcours?: dayjs.Dayjs | null,
    public libellePC?: string | null,
    public montantPriseEnCharge?: number | null,
    public detailPC?: string | null,
    public nomDiplome?: NomDiplome | null,
    public nomDebouche?: string | null,
    public priseEnCharges?: IPriseEnCharge[] | null,
    public concours?: IConcours[] | null,
    public sessionForms?: ISessionForm[] | null,
    public demande?: IDemande | null
  ) {}
}

export function getFormationIdentifier(formation: IFormation): number | undefined {
  return formation.id;
}
