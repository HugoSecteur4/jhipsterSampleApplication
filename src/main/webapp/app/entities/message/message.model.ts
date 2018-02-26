import { BaseEntity } from './../../shared';

export class Message implements BaseEntity {
    constructor(
        public id?: number,
        public idRandonnee?: number,
        public idRandonneur?: number,
        public longitude?: number,
        public latitude?: number,
        public dateHeure?: any,
        public sOS?: boolean,
        public freqCardiaque?: number,
        public positions?: BaseEntity[],
    ) {
        this.sOS = false;
    }
}
