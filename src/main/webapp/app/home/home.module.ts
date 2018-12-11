import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';

import { MesCoursesSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { EventViewComponent } from 'app/home/event-view/event-view.component';

@NgModule({
    imports: [MesCoursesSharedModule, RouterModule.forChild([HOME_ROUTE]), LeafletModule, LeafletModule.forRoot()],
    declarations: [HomeComponent, EventViewComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MesCoursesHomeModule {}
