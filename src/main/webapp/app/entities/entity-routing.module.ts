import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'localite',
        data: { pageTitle: 'Localites' },
        loadChildren: () => import('./localite/localite.module').then(m => m.LocaliteModule),
      },
      {
        path: 'demande',
        data: { pageTitle: 'Demandes' },
        loadChildren: () => import('./demande/demande.module').then(m => m.DemandeModule),
      },
      {
        path: 'dossier',
        data: { pageTitle: 'Dossiers' },
        loadChildren: () => import('./dossier/dossier.module').then(m => m.DossierModule),
      },
      {
        path: 'piece-jointe',
        data: { pageTitle: 'PieceJointes' },
        loadChildren: () => import('./piece-jointe/piece-jointe.module').then(m => m.PieceJointeModule),
      },
      {
        path: 'services',
        data: { pageTitle: 'Services' },
        loadChildren: () => import('./services/services.module').then(m => m.ServicesModule),
      },
      {
        path: 'commission',
        data: { pageTitle: 'Commissions' },
        loadChildren: () => import('./commission/commission.module').then(m => m.CommissionModule),
      },
      {
        path: 'membre',
        data: { pageTitle: 'Membres' },
        loadChildren: () => import('./membre/membre.module').then(m => m.MembreModule),
      },
      {
        path: 'agent',
        data: { pageTitle: 'Agents' },
        loadChildren: () => import('./agent/agent.module').then(m => m.AgentModule),
      },
      {
        path: 'bailleur',
        data: { pageTitle: 'Bailleurs' },
        loadChildren: () => import('./bailleur/bailleur.module').then(m => m.BailleurModule),
      },
      {
        path: 'demandeur',
        data: { pageTitle: 'Demandeurs' },
        loadChildren: () => import('./demandeur/demandeur.module').then(m => m.DemandeurModule),
      },
      {
        path: 'eleve',
        data: { pageTitle: 'Eleves' },
        loadChildren: () => import('./eleve/eleve.module').then(m => m.EleveModule),
      },
      {
        path: 'etudiant',
        data: { pageTitle: 'Etudiants' },
        loadChildren: () => import('./etudiant/etudiant.module').then(m => m.EtudiantModule),
      },
      {
        path: 'professionnel',
        data: { pageTitle: 'Professionnels' },
        loadChildren: () => import('./professionnel/professionnel.module').then(m => m.ProfessionnelModule),
      },
      {
        path: 'concours',
        data: { pageTitle: 'Concours' },
        loadChildren: () => import('./concours/concours.module').then(m => m.ConcoursModule),
      },
      {
        path: 'prise-en-charge',
        data: { pageTitle: 'PriseEnCharges' },
        loadChildren: () => import('./prise-en-charge/prise-en-charge.module').then(m => m.PriseEnChargeModule),
      },
      {
        path: 'session-form',
        data: { pageTitle: 'SessionForms' },
        loadChildren: () => import('./session-form/session-form.module').then(m => m.SessionFormModule),
      },
      {
        path: 'formation',
        data: { pageTitle: 'Formations' },
        loadChildren: () => import('./formation/formation.module').then(m => m.FormationModule),
      },
      {
        path: 'etablissement',
        data: { pageTitle: 'Etablissements' },
        loadChildren: () => import('./etablissement/etablissement.module').then(m => m.EtablissementModule),
      },
      {
        path: 'filiere',
        data: { pageTitle: 'Filieres' },
        loadChildren: () => import('./filiere/filiere.module').then(m => m.FiliereModule),
      },
      {
        path: 'serie',
        data: { pageTitle: 'Series' },
        loadChildren: () => import('./serie/serie.module').then(m => m.SerieModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
