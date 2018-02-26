import { BaseEntity } from './../../shared';

export const enum Sexe {
    'MASCULIN',
    'FEMININ',
    'AUTRE'
}

export class Randonneur implements BaseEntity {
    constructor(
        public id?: number,
        public prenom?: string,
        public nom?: string,
        public sexe?: Sexe,
        public age?: number,
        public dateDeNaissance?: any,
        public marcheurs?: BaseEntity[],
        public envoyeurs?: BaseEntity[],
    ) {
    }
}
