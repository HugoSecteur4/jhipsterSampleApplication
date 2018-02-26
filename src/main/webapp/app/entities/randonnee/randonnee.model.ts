import { BaseEntity } from './../../shared';

export class Randonnee implements BaseEntity {
    constructor(
        public id?: number,
        public lieuDeRDV?: string,
        public denivelePositif?: number,
        public duree?: number,
        public date?: any,
        public parcours?: BaseEntity[],
    ) {
    }
}
