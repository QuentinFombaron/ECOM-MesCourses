export const enum Metier {
    MEDECIN = 'MEDECIN',
    KINESITHERAPEUTE = 'KINESITHERAPEUTE',
    PODOLOGUE = 'PODOLOGUE',
    CONTROLEUR = 'CONTROLEUR'
}

export interface IInscription {
    id?: number;
    role?: Metier;
    user?: number;
    course?: number;
}

export class Inscription implements IInscription {
    constructor(public id?: number, public role?: Metier, public user?: number, public course?: number) {}
}
