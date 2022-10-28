import { IDemande } from 'app/entities/demande/demande.model';
import { TypePiece } from 'app/entities/enumerations/type-piece.model';

export interface IPieceJointe {
  id?: number;
  typePiece?: TypePiece | null;
  nomPiece?: string | null;
  demande?: IDemande | null;
}

export class PieceJointe implements IPieceJointe {
  constructor(public id?: number, public typePiece?: TypePiece | null, public nomPiece?: string | null, public demande?: IDemande | null) {}
}

export function getPieceJointeIdentifier(pieceJointe: IPieceJointe): number | undefined {
  return pieceJointe.id;
}
