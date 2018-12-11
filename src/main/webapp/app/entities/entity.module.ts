import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MesCoursesUserDocumentsModule } from './user-documents/user-documents.module';
import { MesCoursesCourseModule } from './course/course.module';
import { MesCoursesInscriptionModule } from './inscription/inscription.module';
import { MesCoursesCheckPointModule } from './check-point/check-point.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        MesCoursesUserDocumentsModule,
        MesCoursesCourseModule,
        MesCoursesInscriptionModule,
        MesCoursesCheckPointModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MesCoursesEntityModule {}
