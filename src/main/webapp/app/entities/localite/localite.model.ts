import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { IAgent } from 'app/entities/agent/agent.model';
import { NomRegion } from 'app/entities/enumerations/nom-region.model';
import { DAKAR } from 'app/entities/enumerations/dakar.model';
import { DIOURBEL } from 'app/entities/enumerations/diourbel.model';
import { FATICK } from 'app/entities/enumerations/fatick.model';
import { KAFFRINE } from 'app/entities/enumerations/kaffrine.model';
import { KAOLACK } from 'app/entities/enumerations/kaolack.model';
import { KEDOUGOU } from 'app/entities/enumerations/kedougou.model';
import { KOLDA } from 'app/entities/enumerations/kolda.model';
import { LOUGA } from 'app/entities/enumerations/louga.model';
import { MATAM } from 'app/entities/enumerations/matam.model';
import { SAINTLOUIS } from 'app/entities/enumerations/saintlouis.model';
import { SEDHIOU } from 'app/entities/enumerations/sedhiou.model';
import { TAMBACOUNDA } from 'app/entities/enumerations/tambacounda.model';
import { THIES } from 'app/entities/enumerations/thies.model';
import { ZIGUINCHOR } from 'app/entities/enumerations/ziguinchor.model';

export interface ILocalite {
  id?: number;
  region?: NomRegion;
  autreRegion?: string | null;
  departementDakar?: DAKAR | null;
  departementDiourbel?: DIOURBEL | null;
  departementFatick?: FATICK | null;
  departementKaffrine?: KAFFRINE | null;
  departementKaolack?: KAOLACK | null;
  departementKedougou?: KEDOUGOU | null;
  departementKolda?: KOLDA | null;
  departementLouga?: LOUGA | null;
  departementMatam?: MATAM | null;
  departementSaint?: SAINTLOUIS | null;
  departementSedhiou?: SEDHIOU | null;
  departementTambacounda?: TAMBACOUNDA | null;
  departementThis?: THIES | null;
  departementZiguinchor?: ZIGUINCHOR | null;
  autredepartementDakar?: string | null;
  autredepartementDiourbel?: string | null;
  autredepartementFatick?: string | null;
  autredepartementKaffrine?: string | null;
  autredepartementKaolack?: string | null;
  autredepartementKedougou?: string | null;
  autredepartementKolda?: string | null;
  autredepartementLouga?: string | null;
  autredepartementMatam?: string | null;
  autredepartementSaint?: string | null;
  autredepartementSedhiou?: string | null;
  autredepartementTambacounda?: string | null;
  autredepartementThis?: string | null;
  autredepartementZiguinchor?: string | null;
  commune?: string;
  nomQuartier?: string | null;
  etablissements?: IEtablissement[] | null;
  eleves?: IEleve[] | null;
  etudiants?: IEtudiant[] | null;
  professionnels?: IProfessionnel[] | null;
  agents?: IAgent[] | null;
}

export class Localite implements ILocalite {
  constructor(
    public id?: number,
    public region?: NomRegion,
    public autreRegion?: string | null,
    public departementDakar?: DAKAR | null,
    public departementDiourbel?: DIOURBEL | null,
    public departementFatick?: FATICK | null,
    public departementKaffrine?: KAFFRINE | null,
    public departementKaolack?: KAOLACK | null,
    public departementKedougou?: KEDOUGOU | null,
    public departementKolda?: KOLDA | null,
    public departementLouga?: LOUGA | null,
    public departementMatam?: MATAM | null,
    public departementSaint?: SAINTLOUIS | null,
    public departementSedhiou?: SEDHIOU | null,
    public departementTambacounda?: TAMBACOUNDA | null,
    public departementThis?: THIES | null,
    public departementZiguinchor?: ZIGUINCHOR | null,
    public autredepartementDakar?: string | null,
    public autredepartementDiourbel?: string | null,
    public autredepartementFatick?: string | null,
    public autredepartementKaffrine?: string | null,
    public autredepartementKaolack?: string | null,
    public autredepartementKedougou?: string | null,
    public autredepartementKolda?: string | null,
    public autredepartementLouga?: string | null,
    public autredepartementMatam?: string | null,
    public autredepartementSaint?: string | null,
    public autredepartementSedhiou?: string | null,
    public autredepartementTambacounda?: string | null,
    public autredepartementThis?: string | null,
    public autredepartementZiguinchor?: string | null,
    public commune?: string,
    public nomQuartier?: string | null,
    public etablissements?: IEtablissement[] | null,
    public eleves?: IEleve[] | null,
    public etudiants?: IEtudiant[] | null,
    public professionnels?: IProfessionnel[] | null,
    public agents?: IAgent[] | null
  ) {}
}

export function getLocaliteIdentifier(localite: ILocalite): number | undefined {
  return localite.id;
}
