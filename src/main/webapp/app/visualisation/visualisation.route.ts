import { Route } from '@angular/router';

import { VisualisationComponent } from './';
import { CourseResolve } from 'app/entities/course';

export const VISUALISATION_ROUTE: Route = {
    path: 'visualisation/:id',
    component: VisualisationComponent,
    resolve: {
        course: CourseResolve
    },
    data: {
        authorities: [],
        pageTitle: 'Visualisation'
    }
};
