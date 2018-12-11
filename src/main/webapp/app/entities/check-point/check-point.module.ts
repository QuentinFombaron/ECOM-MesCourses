import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MesCoursesSharedModule } from 'app/shared';
import {
    CheckPointComponent,
    CheckPointDetailComponent,
    CheckPointUpdateComponent,
    CheckPointDeletePopupComponent,
    CheckPointDeleteDialogComponent,
    checkPointRoute,
    checkPointPopupRoute
} from './';

const ENTITY_STATES = [...checkPointRoute, ...checkPointPopupRoute];

@NgModule({
    imports: [MesCoursesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CheckPointComponent,
        CheckPointDetailComponent,
        CheckPointUpdateComponent,
        CheckPointDeleteDialogComponent,
        CheckPointDeletePopupComponent
    ],
    entryComponents: [CheckPointComponent, CheckPointUpdateComponent, CheckPointDeleteDialogComponent, CheckPointDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MesCoursesCheckPointModule {}
