import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RunnerFormComponent } from './runner-form/runner-form.component';
import { BenevolesFormComponent } from './benevoles-form/benevoles-form.component';

import { MesCoursesSharedModule } from 'app/shared';
import { VISUALISATION_ROUTE, VisualisationComponent } from './';
import { CONFIRMATION_ROUTE } from 'app/visualisation/confirmation/confirmation.route';

@NgModule({
    imports: [MesCoursesSharedModule, RouterModule.forChild([VISUALISATION_ROUTE, CONFIRMATION_ROUTE])],
    declarations: [VisualisationComponent, RunnerFormComponent, BenevolesFormComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MesCoursesVisualisationModule {}
