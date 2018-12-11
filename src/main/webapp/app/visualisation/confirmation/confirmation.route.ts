import { Route } from '@angular/router';
import { CourseResolve } from 'app/entities/course';
import { ConfirmationComponent } from 'app/visualisation/confirmation/confirmation.component';

export const CONFIRMATION_ROUTE: Route = {
    path: 'confirmation/:id',
    component: ConfirmationComponent,
    resolve: {
        course: CourseResolve
    },
    data: {
        authorities: [],
        pageTitle: 'Confirmation'
    }
};
