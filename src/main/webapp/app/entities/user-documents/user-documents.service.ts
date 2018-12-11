import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserDocuments } from 'app/shared/model/user-documents.model';

type EntityResponseType = HttpResponse<IUserDocuments>;
type EntityArrayResponseType = HttpResponse<IUserDocuments[]>;

@Injectable({ providedIn: 'root' })
export class UserDocumentsService {
    private resourceUrl = SERVER_API_URL + 'api/user-documents';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/user-documents';

    constructor(private http: HttpClient) {}

    create(userDocuments: IUserDocuments): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userDocuments);
        return this.http
            .post<IUserDocuments>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(userDocuments: IUserDocuments): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userDocuments);
        return this.http
            .put<IUserDocuments>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IUserDocuments>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUserDocuments[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUserDocuments[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(userDocuments: IUserDocuments): IUserDocuments {
        const copy: IUserDocuments = Object.assign({}, userDocuments, {
            dateDeNaissance:
                userDocuments.dateDeNaissance != null && userDocuments.dateDeNaissance.isValid()
                    ? userDocuments.dateDeNaissance.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateDeNaissance = res.body.dateDeNaissance != null ? moment(res.body.dateDeNaissance) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((userDocuments: IUserDocuments) => {
            userDocuments.dateDeNaissance = userDocuments.dateDeNaissance != null ? moment(userDocuments.dateDeNaissance) : null;
        });
        return res;
    }
}
