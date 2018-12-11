import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export const enum Sport {
    COURSE_A_PIED = 'COURSE_A_PIED',
    MARATHON = 'MARATHON',
    RANDONNEE = 'RANDONNEE',
    COURSE_A_VELO = 'COURSE_A_VELO',
    TRIATHLON = 'TRIATHLON'
}

export interface ICourse {
    id?: number;
    organisateur?: number;
    titre?: string;
    description?: string;
    sport?: Sport;
    date?: Moment;
    heure?: string;
    longitude?: number;
    latitude?: number;
    lieu?: string;
    prix?: number;
    image1ContentType?: string;
    image1?: any;
    image2ContentType?: string;
    image2?: any;
    image3ContentType?: string;
    image3?: any;
    image4ContentType?: string;
    image4?: any;
    image5ContentType?: string;
    image5?: any;
    participants?: IUser[];
}

export class Course implements ICourse {
    constructor(
        public id?: number,
        public organisateur?: number,
        public titre?: string,
        public description?: string,
        public sport?: Sport,
        public date?: Moment,
        public heure?: string,
        public longitude?: number,
        public latitude?: number,
        public lieu?: string,
        public prix?: number,
        public image1ContentType?: string,
        public image1?: any,
        public image2ContentType?: string,
        public image2?: any,
        public image3ContentType?: string,
        public image3?: any,
        public image4ContentType?: string,
        public image4?: any,
        public image5ContentType?: string,
        public image5?: any,
        public participants?: IUser[]
    ) {}
}
