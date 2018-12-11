import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MesCoursesSharedModule } from 'app/shared';
import { MesCoursesAdminModule } from 'app/admin/admin.module';
import {
    UserDocumentsComponent,
    UserDocumentsDetailComponent,
    UserDocumentsUpdateComponent,
    UserDocumentsDeletePopupComponent,
    UserDocumentsDeleteDialogComponent,
    userDocumentsRoute,
    userDocumentsPopupRoute
} from './';

const ENTITY_STATES = [...userDocumentsRoute, ...userDocumentsPopupRoute];

@NgModule({
    imports: [MesCoursesSharedModule, MesCoursesAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserDocumentsComponent,
        UserDocumentsDetailComponent,
        UserDocumentsUpdateComponent,
        UserDocumentsDeleteDialogComponent,
        UserDocumentsDeletePopupComponent
    ],
    entryComponents: [
        UserDocumentsComponent,
        UserDocumentsUpdateComponent,
        UserDocumentsDeleteDialogComponent,
        UserDocumentsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MesCoursesUserDocumentsModule {}
