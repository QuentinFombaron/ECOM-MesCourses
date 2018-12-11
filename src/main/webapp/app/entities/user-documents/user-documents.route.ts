import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserDocuments } from 'app/shared/model/user-documents.model';
import { UserDocumentsService } from './user-documents.service';
import { UserDocumentsComponent } from './user-documents.component';
import { UserDocumentsDetailComponent } from './user-documents-detail.component';
import { UserDocumentsUpdateComponent } from './user-documents-update.component';
import { UserDocumentsDeletePopupComponent } from './user-documents-delete-dialog.component';
import { IUserDocuments } from 'app/shared/model/user-documents.model';

@Injectable({ providedIn: 'root' })
export class UserDocumentsResolve implements Resolve<IUserDocuments> {
    constructor(private service: UserDocumentsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((userDocuments: HttpResponse<UserDocuments>) => userDocuments.body));
        }
        return of(new UserDocuments());
    }
}

export const userDocumentsRoute: Routes = [
    {
        path: 'user-documents',
        component: UserDocumentsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDocuments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-documents/:id/view',
        component: UserDocumentsDetailComponent,
        resolve: {
            userDocuments: UserDocumentsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDocuments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-documents/new',
        component: UserDocumentsUpdateComponent,
        resolve: {
            userDocuments: UserDocumentsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDocuments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-documents/:id/edit',
        component: UserDocumentsUpdateComponent,
        resolve: {
            userDocuments: UserDocumentsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDocuments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userDocumentsPopupRoute: Routes = [
    {
        path: 'user-documents/:id/delete',
        component: UserDocumentsDeletePopupComponent,
        resolve: {
            userDocuments: UserDocumentsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDocuments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
