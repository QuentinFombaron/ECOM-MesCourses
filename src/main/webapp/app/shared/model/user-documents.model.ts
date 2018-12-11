import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export const enum Sexe {
    HOMME = 'HOMME',
    FEMME = 'FEMME'
}

export interface IUserDocuments {
    id?: number;
    dateDeNaissance?: Moment;
    sexe?: Sexe;
    certificatMedicalContentType?: string;
    certificatMedical?: any;
    licenseFederaleContentType?: string;
    licenseFederale?: any;
    documentComplementaireContentType?: string;
    documentComplementaire?: any;
    user?: IUser;
}

export class UserDocuments implements IUserDocuments {
    constructor(
        public id?: number,
        public dateDeNaissance?: Moment,
        public sexe?: Sexe,
        public certificatMedicalContentType?: string,
        public certificatMedical?: any,
        public licenseFederaleContentType?: string,
        public licenseFederale?: any,
        public documentComplementaireContentType?: string,
        public documentComplementaire?: any,
        public user?: IUser
    ) {}
}
