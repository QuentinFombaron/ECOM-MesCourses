import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MesCoursesSharedModule } from 'app/shared';
import {
    InscriptionComponent,
    InscriptionDetailComponent,
    InscriptionUpdateComponent,
    InscriptionDeletePopupComponent,
    InscriptionDeleteDialogComponent,
    inscriptionRoute,
    inscriptionPopupRoute
} from './';

const ENTITY_STATES = [...inscriptionRoute, ...inscriptionPopupRoute];

@NgModule({
    imports: [MesCoursesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InscriptionComponent,
        InscriptionDetailComponent,
        InscriptionUpdateComponent,
        InscriptionDeleteDialogComponent,
        InscriptionDeletePopupComponent
    ],
    entryComponents: [InscriptionComponent, InscriptionUpdateComponent, InscriptionDeleteDialogComponent, InscriptionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MesCoursesInscriptionModule {}
